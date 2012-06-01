package at.fhv.multisync.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class PreferenceHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbench workbench = PlatformUI.getWorkbench();
		PreferenceDialog dialog = new PreferenceDialog(workbench.getActiveWorkbenchWindow().getShell(), workbench.getPreferenceManager());
		dialog.open();
		return null;
	}

}
