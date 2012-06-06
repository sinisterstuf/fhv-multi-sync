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
	private static TreeViewer _jobTree;
	public static final String ID = "MultiSync.view.JobExplorer";

	/**
	 * Default constructor.
	 */
	public JobExplorer() {
		// nothing
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		_jobTree = new TreeViewer(parent, SWT.BORDER);
		_jobTree.setContentProvider(new JobContentProvider());
		_jobTree.setLabelProvider(new JobLabelProvider());
		_jobTree.setInput(JobModel.getInstance());
		getSite().setSelectionProvider(_jobTree);
	}

	@Override
	public void setFocus() {
		// nothing
	}

	/**
	 * Refresh the tree
	 */
	public static void refreshTree() {
		if (_jobTree != null) {
			_jobTree.refresh();
		}
	}
}
