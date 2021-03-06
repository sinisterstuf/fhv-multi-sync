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
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

import at.fhv.multisync.bl.helper.PluginHelper;
import at.fhv.multisync.extension.FileSystemProvider;
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
	private CTabFolder _masterTabFolder;

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
		parent.setLayout(new GridLayout(3, false));

		Label lblMaster = new Label(parent, SWT.NONE);
		lblMaster.setText("Master");

		Label lblSlaves = new Label(parent, SWT.NONE);
		lblSlaves.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
				false, 1, 1));
		lblSlaves.setText("Slaves");

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false,
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
				// get the file system provider
				StructuredSelection selected = (StructuredSelection) _slaveCombo
						.getSelection();
				FileSystemProvider provider = (FileSystemProvider) selected
						.getFirstElement();

				initializeTab(_slaveDirTabFolder, provider, null);
				setDirty(true);
			}
		});

		_masterTabFolder = new CTabFolder(parent, SWT.BORDER);
		_masterTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				true, 1, 1));
		_masterTabFolder.setSelectionBackground(Display.getCurrent()
				.getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		CTabItem masterTab = new CTabItem(_masterTabFolder, SWT.NONE);
		masterTab.setText("Master");

		_masterDirTree = new TreeViewer(_masterTabFolder, SWT.BORDER);
		Tree tree = _masterDirTree.getTree();
		masterTab.setControl(tree);

		// load data
		String masterDir = (_job.getMaster() != null) ? _job.getMaster()
				: "C://";
		showFileSystem(_masterDirTree,
				PluginHelper.getSuitableProvider(masterDir), _job.getMaster());

		// set as selected
		if (_masterDirTree != null && _job.getMaster() != null
				&& !_job.getMaster().isEmpty()) {
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

		CTabItem tbtmPreferences = new CTabItem(_masterTabFolder, SWT.NONE);
		tbtmPreferences.setText("Preferences");

		// preferences or settings
		ScrolledComposite prefscroll = new ScrolledComposite(_masterTabFolder,
				SWT.V_SCROLL);
		Composite prefcomp = new Composite(prefscroll, SWT.NONE);
		GridLayout layoutPref = new GridLayout();
		layoutPref.numColumns = 2;

		prefcomp.setLayout(layoutPref);

		List<SettingKeyValue> preference = getPreferenceList();

		for (final SettingKeyValue pref : preference) {
			if (pref.getProperty().getClass() == Boolean.class) {
				final Button button = new Button(prefcomp, SWT.CHECK);
				button.setText(pref.getPropertyDescription());
				button.setSelection((Boolean) pref.getProperty());
				GridData gridData = new GridData();
				gridData.horizontalSpan = 2;
				button.setLayoutData(gridData);
				button.addMouseListener(new MouseListener() {

					@Override
					public void mouseUp(MouseEvent e) {
						pref.setProperty(button.getSelection());
						setDirty(true);
					}

					@Override
					public void mouseDown(MouseEvent e) {
					}

					@Override
					public void mouseDoubleClick(MouseEvent e) {
					}
				});
			}
			if (pref.getProperty().getClass() == String.class) {

				Label lbl = new Label(prefcomp, SWT.None);
				final Text txt = new Text(prefcomp, SWT.CHECK);

				lbl.setText(pref.getPropertyDescription() + ":");
				txt.setText((String) pref.getProperty());
				txt.setToolTipText(pref.getPropertyDescription());
				txt.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if (txt.getText()
								.compareTo((String) pref.getProperty()) != 0)
							pref.setProperty(txt.getText());
					}

					@Override
					public void focusGained(FocusEvent e) {
					}
				});
			}

			if (pref.getProperty().getClass() == Long.class
					|| pref.getProperty().getClass() == Long.class) {

				Label lbl = new Label(prefcomp, SWT.None);
				final Spinner spin = new Spinner(prefcomp, SWT.CHECK);

				lbl.setText(pref.getPropertyDescription() + ":");
				spin.setDigits(0);
				spin.setMinimum(0);
				spin.setIncrement(100);
				spin.setSelection(((Long) pref.getProperty()).intValue());
				spin.setToolTipText(pref.getPropertyDescription());
				spin.addFocusListener(new FocusListener() {

					@Override
					public void focusLost(FocusEvent e) {
						if (spin.getSelection() == (Long) pref.getProperty())
							pref.setProperty(spin.getSelection());
					}

					@Override
					public void focusGained(FocusEvent e) {
					}
				});
			}
		}

		prefscroll.setContent(prefcomp);
		prefscroll.setExpandHorizontal(true);
		prefscroll.setExpandVertical(true);
		prefscroll.setMinSize(prefcomp.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		tbtmPreferences.setControl(prefscroll);

		_slaveDirTabFolder = new CTabFolder(parent, SWT.BORDER);
		_slaveDirTabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, true, 2, 1));
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

	interface SettingKeyValue {
		String getPropertyDescription();

		Object getProperty();

		void setProperty(Object o);
	}

	/**
	 * Returns a list of preference items to be displayed by the editor.
	 * 
	 * @return List of values
	 * 
	 *         The following preference items are missing setters: TODO:
	 *         implement Boolean StdLog TODO: implement String LogFile TODO:
	 *         implement Boolean SyncTimeOfTarget Actually I just realised that
	 *         everything after _overwriteTarget has no setter O_o
	 */
	private List<SettingKeyValue> getPreferenceList() {
		List<SettingKeyValue> preference = new ArrayList<SettingKeyValue>();

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setName((String) o);
			}

			@Override
			public Object getProperty() {
				return _job.getName();
			};

			@Override
			public String getPropertyDescription() {
				return "Name";
			};
		});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setSimulteOnly((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isSimulateOnly();
			};

			@Override
			public String getPropertyDescription() {
				return "Simulate only; do not modify target";
			};
		});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setIgnoreWarnings((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isIgnoreWarnings();
			};

			@Override
			public String getPropertyDescription() {
				return "Ignore warnings; do not pause";
			};
		});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setNoRecurse((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isNoRecurse();
			};

			@Override
			public String getPropertyDescription() {
				return "Do not recurse into subdirectories";
			};
		});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setNoNameMatch((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isNoNameMatch();
			};

			@Override
			public String getPropertyDescription() {
				return "Do not use filename for file-matching";
			};
		});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setNoTimeMatch((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isNoTimeMatch();
			};

			@Override
			public String getPropertyDescription() {
				return "Do not use last-modified time for file-matching";
			};
		});

		// TIME TOLERANCE <<< Y U NO HAZ SETTER?!
		// preference.add(new SettingKeyValue(){
		// public void setProperty(Object o){_job.setTimeTolerance((Long)o);}
		// //method missing
		// public Object getProperty(){return _job.getTimeTolerance();};
		// public String getPropertyDescription(){return ""; };});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setNoCrcMatch((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isNoCrcMatch();
			};

			@Override
			public String getPropertyDescription() {
				return "Do not use CRC-32 checksum for file-matching";
			};
		});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setRenameTarget((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isRenameTarget();
			};

			@Override
			public String getPropertyDescription() {
				return "Rename matched target files";
			};
		});

		preference.add(new SettingKeyValue() {
			@Override
			public void setProperty(Object o) {
				_job.setOverwriteTarget((Boolean) o);
			}

			@Override
			public Object getProperty() {
				return _job.isOverwriteTarget();
			};

			@Override
			public String getPropertyDescription() {
				return "Overwrite existing target files";
			};
		});

		// preference.add(new SettingKeyValue(){
		// //public void setProperty(Object o){_job.setStdLog((Boolean)o);}
		// //method missing
		// public Object getProperty(){return _job.isStdLog();};
		// public String getPropertyDescription(){return "Use std logging";
		// };});
		//
		// preference.add(new SettingKeyValue(){
		// //public void setProperty(Object o){_job.set((String)o);} //method
		// missing
		// public Object getProperty(){return _job.getLogFile();};
		// public String getPropertyDescription(){return "Log File"; };});

		// preference.add(new SettingKeyValue(){
		// public void setProperty(Object o){_job.set((Boolean)o);} //method
		// missing
		// public Object getProperty(){return _job.isSyncTimeOfTarget();};
		// public String getPropertyDescription(){return ""; };});

		// preference.add(new SettingKeyValue(){
		// public void setProperty(Object o){_job.setDeleteTarget((Boolean)o);}
		// //method missing
		// public Object getProperty(){return _job.isDeleteTarget();};
		// public String getPropertyDescription(){return ""; };});

		return preference;
	}

	/**
	 * Load all slaves in the job to the given tab folder
	 * 
	 * @param folder
	 *            The tab folder to load the slaves in
	 */
	private void loadSlaves(CTabFolder folder) {
		for (String s : _job.getSlaves()) {
			TreeViewer item = initializeTab(folder,
					PluginHelper.getSuitableProvider(s), s);

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
	 * @param url
	 *            The root url to load
	 */
	private void showFileSystem(TreeViewer tree, FileSystemProvider provider,
			String url) {
		if (provider != null) {
			tree.setContentProvider(new FileContentProvider());
			tree.setLabelProvider(new FileLabelProvider());
			tree.setInput(provider.getRoot(url));
		}
	}

	/**
	 * Initialize the slave tab folder
	 * 
	 * @param folder
	 *            The tab folder to initialize
	 * @param url
	 *            The root url
	 * @return The created treeviewer in the tab
	 */
	private TreeViewer initializeTab(CTabFolder folder,
			FileSystemProvider provider, String url) {
		CTabItem item = new CTabItem(folder, SWT.NONE);
		item.setText("Slave");
		item.setShowClose(true);

		// create the tree
		TreeViewer treeViewer = new TreeViewer(item.getParent(), SWT.NONE);
		item.setControl(treeViewer.getControl());

		if (provider != null) {
			// show the files
			showFileSystem(treeViewer, provider, url);
			folder.setFocus();
		}

		return treeViewer;
	}

	@Override
	public void setFocus() {
		_masterTabFolder.setFocus();
		_slaveDirTabFolder.setFocus();
	}
}
