package at.fhv.multisync.ui.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * 
 * The NewJobWizardPage class which represents a single wizard page for adding
 * new jobs.
 * 
 * @author Michael Sieber
 */
public class NewJobWizardPage extends WizardPage {

	/**
	 * Default constructor.
	 */
	protected NewJobWizardPage() {
		super("New Job");
		setTitle("New Job");
		setDescription("Add a new sync job");
	}

	@Override
	public void createControl(Composite parent) {
		// TODO add ui stuff here
		Label l = new Label(parent, SWT.NONE);
		l.setText("Test");
		setControl(l);
	}
}
