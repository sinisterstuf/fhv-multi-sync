package at.fhv.multisync.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;

import at.fhv.multisync.model.Job;
import at.fhv.multisync.model.JobGroup;
import at.fhv.multisync.ui.dialog.StringInputDialog;
import at.fhv.multisync.ui.views.JobExplorer;

/**
 * 
 * The HandlerJobAdd class which handles job add commands.
 * 
 * @author Michael Sieber
 */
public class HandlerJobAdd extends AbstractHandler {

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
			StringInputDialog d = new StringInputDialog(PlatformUI
					.getWorkbench().getActiveWorkbenchWindow().getShell(),
					"Add Job", "Job name:");

			if (d.open() == Window.OK) {
				((JobGroup) selected).addJob(new Job(d.getInput()));
				JobExplorer.refreshTree();
			}
			// if no group is selected warn the user
		} else {
			MessageDialog.openWarning(HandlerUtil.getActiveShell(event),
					"Warning", "You have to select a job group first.");
		}

		return null;
	}
}
