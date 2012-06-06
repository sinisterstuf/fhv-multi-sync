package at.fhv.multisync.ui.editors;

import org.eclipse.core.runtime.IProgressMonitor;
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

import at.fhv.multisync.model.Job;

public class JobEditor extends EditorPart {
	public static final String ID = "MultiSync.editor.JobEditor";
	private Job _job;
	private boolean _dirty;

	/**
	 * Default constructor.
	 */
	public JobEditor() {
		_dirty = false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

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
		firePropertyChange(PROP_DIRTY);
		_dirty = dirty;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, true));

		Tree _masterDirTree = new Tree(parent, SWT.BORDER);
		GridData gd__masterDirTree = new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1);
		gd__masterDirTree.widthHint = 274;
		_masterDirTree.setLayoutData(gd__masterDirTree);

		TabFolder _slaveDirTab = new TabFolder(parent, SWT.NONE);
		GridData gd__slaveDirTab = new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1);
		gd__slaveDirTab.heightHint = 292;
		_slaveDirTab.setLayoutData(gd__slaveDirTab);
	}

	@Override
	public void setFocus() {
		// nothing
	}
}
