package at.fhv.multisync.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.PlatformUI;

import at.fhv.multisync.model.JobGroup;
import at.fhv.multisync.model.JobModel;
import at.fhv.multisync.ui.dialog.StringInputDialog;
import at.fhv.multisync.ui.views.JobExplorer;

/**
 * 
 * The HandlerJobAdd class which handles job add commands.
 * 
 * @author Michael Sieber
 */
public class HandlerJobGroupAdd extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		StringInputDialog d = new StringInputDialog(PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getShell(), "Add Job Group",
				"Group name:");

		if (d.open() == Window.OK) {
			String name = d.getInput();
			JobModel.getInstance().addGroup(new JobGroup(name));
			JobExplorer.refreshTree();
		}

		return null;
	}
}
