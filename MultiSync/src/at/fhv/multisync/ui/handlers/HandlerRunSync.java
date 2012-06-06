package at.fhv.multisync.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import at.fhv.multisync.model.Job;
import at.fhv.multisync.model.JobGroup;

/**
 * 
 * The HandlerRunSync class which runs the selected group or job.
 * 
 * @author Michael Sieber
 */
public class HandlerRunSync extends AbstractHandler {

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
			((JobGroup) selected).runAll();
			// check if a job is selected
		} else if (selected != null && selected instanceof Job) {
			((Job) selected).run();
		}

		return null;
	}

}
