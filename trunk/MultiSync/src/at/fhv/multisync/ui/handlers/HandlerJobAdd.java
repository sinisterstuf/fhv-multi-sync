package at.fhv.multisync.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.handlers.HandlerUtil;

import at.fhv.multisync.ui.wizard.NewJobWizard;

public class HandlerJobAdd extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Instantiates and initializes the wizard
		NewJobWizard wizard = new NewJobWizard();

		// Instantiates the wizard container with the wizard and opens it
		WizardDialog dialog = new WizardDialog(
				HandlerUtil.getActiveShell(event), wizard);
		dialog.open();

		return null;
	}
}
