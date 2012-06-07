package at.fhv.multisync.ui.editors;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import at.fhv.multisync.bl.file.FileSystemProvider;
import at.fhv.multisync.bl.file.impl.LocalFileSystemProvider;
import at.fhv.multisync.model.Job;
import at.fhv.multisync.model.provider.file.FileContentProvider;
import at.fhv.multisync.model.provider.file.FileLabelProvider;

public class JobEditor extends EditorPart {
	public static final String ID = "MultiSync.editor.JobEditor";
	private Job _job;
	private boolean _dirty;
	private TreeViewer _masterDirTree;

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
		// TODO implement
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
		parent.setLayout(new GridLayout(2, true));

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

		TabFolder slaveDirTab = new TabFolder(parent, SWT.NONE);
		GridData gdSlaveDirTab = new GridData(SWT.FILL, SWT.FILL, true, true,
				1, 1);
		gdSlaveDirTab.heightHint = 320;
		slaveDirTab.setLayoutData(gdSlaveDirTab);
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

	@Override
	public void setFocus() {
		// nothing
	}
}
