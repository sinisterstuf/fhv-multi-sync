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

public class JobEditor extends EditorPart {
	public static final String ID = "MultiSync.editor.JobEditor";

	public JobEditor() {
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

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
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}
