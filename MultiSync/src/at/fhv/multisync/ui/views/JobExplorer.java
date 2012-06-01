package at.fhv.multisync.ui.views;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import at.fhv.multisync.model.JobModel;
import at.fhv.multisync.model.provider.tree.JobContentProvider;
import at.fhv.multisync.model.provider.tree.JobLabelProvider;

public class JobExplorer extends ViewPart {
	private TreeViewer _jobTree;
	public static final String ID = "MultiSync.view.JobExplorer";

	public JobExplorer() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		_jobTree = new TreeViewer(parent, SWT.BORDER);
		_jobTree.setContentProvider(new JobContentProvider());
		_jobTree.setLabelProvider(new JobLabelProvider());
		_jobTree.setInput(JobModel.getInstance());
	}

	@Override
	public void setFocus() {
		// nothing
	}
}
