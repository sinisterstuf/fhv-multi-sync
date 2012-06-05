package at.fhv.multisync.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import at.fhv.multisync.Activator;

public class JobPreferencePage
	extends FieldEditorPreferencePage
	implements IWorkbenchPreferencePage {
	
	public JobPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Preferences for the current job");
	}

	public void init(IWorkbench workbench) {}

	protected void createFieldEditors() {
		
		addField(new BooleanFieldEditor(
				PreferenceConstants.J_simulate,
				"Simulate only; do not modify target",
				getFieldEditorParent()));
		
	}
	
	
	
}
