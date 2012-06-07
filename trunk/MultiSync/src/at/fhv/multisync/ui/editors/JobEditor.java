package at.fhv.multisync.ui.editors;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabFolder2Adapter;
import org.eclipse.swt.custom.CTabFolderEvent;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import at.fhv.multisync.bl.file.FileSystemProvider;
import at.fhv.multisync.bl.file.impl.LocalFileSystemProvider;
import at.fhv.multisync.bl.helper.PluginHelper;
import at.fhv.multisync.model.Job;
import at.fhv.multisync.model.provider.file.FileContentProvider;
import at.fhv.multisync.model.provider.file.FileLabelProvider;

public class JobEditor extends EditorPart {
	public static final String ID = "MultiSync.editor.JobEditor";
	private Job _job;
	private boolean _dirty;
	private TreeViewer _masterDirTree;
	private CTabFolder _slaveDirTabFolder;
	private ComboViewer _slaveCombo;

	/**
	 * Default constructor.
	 */
	public JobEditor() {
		_dirty = false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// save the master directory
		TreeSelection masterSelection = (TreeSelection) _masterDirTree
				.getSelection();
		File selectedMasterFile = (File) masterSelection.getFirstElement();
		_job.setMaster(selectedMasterFile.getAbsolutePath());

		// save the slaves
		List<String> slaves = new ArrayList<String>();
		for (CTabItem tab : _slaveDirTabFolder.getItems()) {
			Tree t = (Tree) tab.getControl();

			if (t.getSelectionCount() > 0) {
				TreeItem item = t.getSelection()[0];
				File file = (File) item.getData();
				slaves.add(file.getAbsolutePath());
			}
		}
		_job.SetSlaves(slaves);

		setDirty(false);
	}

	@Override
	public void doSaveAs() {
		// nothing
	}

	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		if (!(input instanceof JobEditorInput)) {
			throw new IllegalArgumentException();
		}

		// set the parts site
		setSite(site);

		// set the input for the editor
		setInput(input);
		_job = ((JobEditorInput) input).getJob();

		// set the title
		setPartName(_job.getName());
	}

	@Override
	public boolean isDirty() {
		return _dirty;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	/**
	 * Change the dirty state and fire event.
	 * 
	 * @param dirty
	 *            The new dirty state
	 */
	private void setDirty(boolean dirty) {
		_dirty = dirty;
		firePropertyChange(PROP_DIRTY);
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(3, true));

		Label lblMaster = new Label(parent, SWT.NONE);
		lblMaster.setText("Master");

		Label lblSlaves = new Label(parent, SWT.NONE);
		lblSlaves.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		lblSlaves.setText("Slaves");

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		composite.setLayout(new GridLayout(2, false));

		_slaveCombo = new ComboViewer(composite, SWT.NONE);
		_slaveCombo.getControl().setLayoutData(
				new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		addFileSystemProvider(_slaveCombo);

		Button btnAddSlave = new Button(composite, SWT.CENTER);
		btnAddSlave.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		btnAddSlave.setText("Add slave");

		btnAddSlave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				initializeTab(_slaveDirTabFolder);
				setDirty(true);
			}
		});

		_masterDirTree = new TreeViewer(parent, SWT.BORDER);
		Tree tree = _masterDirTree.getTree();
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1);
		gd_tree.widthHint = 118;
		tree.setLayoutData(gd_tree);

		// load data
		showFileSystem(_masterDirTree, new LocalFileSystemProvider());

		// set as selected
		if (_masterDirTree != null && !_job.getMaster().isEmpty()) {
			File master = new File(_job.getMaster());
			StructuredSelection sel = new StructuredSelection(master);
			_masterDirTree.setSelection(sel, true);
		}

		_masterDirTree
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						setDirty(true);
					}
				});

		getSite().setSelectionProvider(_masterDirTree);

		_slaveDirTabFolder = new CTabFolder(parent, SWT.BORDER);
		_slaveDirTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 2, 1));
		_slaveDirTabFolder.setSelectionBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		_slaveDirTabFolder.addCTabFolder2Listener(new CTabFolder2Adapter() {
			@Override
			public void close(CTabFolderEvent event) {
				setDirty(true);
			}
		});

		loadSlaves(_slaveDirTabFolder);
	}

	/**
	 * Load all slaves in the job to the given tab folder
	 * 
	 * @param folder
	 *            The tab folder to load the slaves in
	 */
	private void loadSlaves(CTabFolder folder) {
		for (String s : _job.getSlaves()) {
			TreeViewer item = initializeTab(folder);

			File slave = new File(s);
			StructuredSelection sel = new StructuredSelection(slave);
			item.setSelection(sel, true);
		}
	}

	/**
	 * Add all available file system provider to a combobox
	 * 
	 * @param combo
	 *            The combobox to which all the providers will be added
	 */
	private void addFileSystemProvider(ComboViewer combo) {
		// create the input list
		// FileSystemProvider[] provider = { new LocalFileSystemProvider() };
		FileSystemProvider[] provider = PluginHelper.getFileSystemProviders();

		combo.setContentProvider(new ArrayContentProvider());
		combo.setLabelProvider(new LabelProvider());
		combo.setInput(provider);
		combo.getCombo().select(0);
	}

	/**
	 * Show the file system in the given tree
	 * 
	 * @param tree
	 *            The tree in which the files should be shown
	 * @param provider
	 *            The provider of the file system
	 */
	private void showFileSystem(TreeViewer tree, FileSystemProvider provider) {
		tree.setContentProvider(new FileContentProvider());
		tree.setLabelProvider(new FileLabelProvider());
		tree.setInput(provider.getRoot());
	}

	/**
	 * Initialize the slave tab folder
	 * 
	 * @param folder
	 *            The tab folder to initialize
	 * @return The created treeviewer in the tab
	 */
	private TreeViewer initializeTab(CTabFolder folder) {
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("Slave");
		item.setShowClose(true);

		// create the tree
		TreeViewer treeViewer = new TreeViewer(item.getParent(), SWT.NONE);
		item.setControl(treeViewer.getControl());

		// get the file system provider
		StructuredSelection selected = (StructuredSelection) _slaveCombo
				.getSelection();
		FileSystemProvider provider = (FileSystemProvider) selected
				.getFirstElement();

		if (provider != null) {
			// show the files
			showFileSystem(treeViewer, provider);
			folder.setFocus();
		}

		return treeViewer;
	}

	@Override
	public void setFocus() {
		_slaveDirTabFolder.setFocus();
	}
}
