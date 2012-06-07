package at.fhv.multisync.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import at.fhv.multisync.model.Job;
import at.fhv.multisync.model.JobGroup;
import at.fhv.multisync.model.JobModel;
import at.fhv.multisync.ui.views.JobExplorer;

/**
 * 
 * Handler for removing jobs and job groups.
 * 
 * @author Michael Sieber
 */
public class HandlerRemoveJob extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();

		Object selected = null;
		// check if the selection is a tree selection
		if (selection instanceof TreeSelection) {
			selected = ((TreeSelection) selection).getFirstElement();
		}

		// check if a job group is selected
		if (selected != null && selected instanceof JobGroup) {
			JobModel.getInstance().removeGroup((JobGroup) selected);
			// check if a job is selected
		} else if (selected != null && selected instanceof Job) {
			Job job = (Job) selected;
			if (job.getParent() != null) {
				job.getParent().removeJob(job);
				job = null;
			}
		}

		JobExplorer.refreshTree();

		return null;
	}
}
