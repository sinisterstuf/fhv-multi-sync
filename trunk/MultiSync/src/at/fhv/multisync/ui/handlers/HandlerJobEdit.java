package at.fhv.multisync.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import at.fhv.multisync.model.Job;
import at.fhv.multisync.ui.editors.JobEditor;
import at.fhv.multisync.ui.editors.JobEditorInput;

public class HandlerJobEdit extends AbstractHandler {
	public final static String ID = "at.multisync.job.edit";

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getActiveWorkbenchWindow(event)
				.getActivePage().getSelection();

		Object selected = null;
		// check if the selection is a tree selection
		if (selection instanceof TreeSelection) {
			selected = ((TreeSelection) selection).getFirstElement();
		}

		// check if a job is selected
		if (selected != null && selected instanceof Job) {
			// get the selected job
			Job job = (Job) selected;
			JobEditorInput input = new JobEditorInput(job);

			try {
				// open the editor with the selected job
				HandlerUtil.getActiveWorkbenchWindow(event).getActivePage()
						.openEditor(input, JobEditor.ID);
			} catch (Exception e) {
				MessageDialog.openError(HandlerUtil.getActiveShell(event),
						"Error", "Error opening job.");
				e.printStackTrace();
			}
		}
		return null;
	}
}
