package at.fhv.multisync.ui.views;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;

import at.fhv.multisync.model.JobModel;
import at.fhv.multisync.model.provider.tree.JobContentProvider;
import at.fhv.multisync.model.provider.tree.JobLabelProvider;
import at.fhv.multisync.ui.handlers.HandlerJobEdit;

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

		// make the selection available to the whole application
		getSite().setSelectionProvider(_jobTree);

		// add listener for opening jobs
		_jobTree.addDoubleClickListener(new IDoubleClickListener() {

			@Override
			public void doubleClick(DoubleClickEvent event) {
				IHandlerService hs = (IHandlerService) getSite().getService(
						IHandlerService.class);

				try {
					hs.executeCommand(HandlerJobEdit.ID, null);
				} catch (Exception e) {
					MessageDialog.openError(getSite().getShell(), "Error",
							"Error opening job.");
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void setFocus() {
		_jobTree.getControl().setFocus();
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
