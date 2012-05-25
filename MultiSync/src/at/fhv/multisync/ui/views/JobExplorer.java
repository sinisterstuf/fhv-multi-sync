package at.fhv.multisync.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class JobExplorer extends ViewPart {
	private Tree _jobTree;

	public JobExplorer() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		_jobTree = new Tree(parent, SWT.BORDER);
		// TODO Auto-generated method stub

	}

	@Override
	public void setFocus() {
		_jobTree.setFocus();
	}
}
