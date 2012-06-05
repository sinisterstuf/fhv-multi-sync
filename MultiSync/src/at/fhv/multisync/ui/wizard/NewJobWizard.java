package at.fhv.multisync.ui.wizard;

import org.eclipse.jface.wizard.Wizard;

/**
 * 
 * The NewJobWizard class which represents the wizard for creating new jobs.
 * 
 * @author Michael Sieber
 */
public class NewJobWizard extends Wizard {
	private NewJobWizardPage _jobPage;

	/**
	 * Default constructor.
	 */
	public NewJobWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addPages() {
		_jobPage = new NewJobWizardPage();
		addPage(_jobPage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performFinish() {
		return true;
	}
}
