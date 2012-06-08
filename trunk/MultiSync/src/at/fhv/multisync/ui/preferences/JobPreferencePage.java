package at.fhv.multisync.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
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
		
		// boolean settings
		addField(new BooleanFieldEditor(PreferenceConstants.J_simulate,	"Simulate only; do not modify target", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_ignoreWarnings,	"ignore warnings; do not pause", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_noRecurse,	"do not recurse into subdirectories", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_noNameMatch,	" do not use filename for file-matching", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_noTimeMatch,	"do not use last-modified time for file-matching", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_noCrcMatch,	"do not use CRC-32 checksum for file-matching", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_renameTarget,	"rename matched target files", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_overwriteTarget,	"overwrite existing target files", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_stdLog,	"use standard logging", getFieldEditorParent()));	
		addField(new BooleanFieldEditor(PreferenceConstants.J_syncTimeOfTarget,	"synchronize time of matched target files", getFieldEditorParent()));	
		addField(new BooleanFieldEditor(PreferenceConstants.J_deleteTarget,	"delete unmatched target files/directories", getFieldEditorParent()));	
		addField(new BooleanFieldEditor(PreferenceConstants.J_filterRelativePathname,	"filter relative pathname", getFieldEditorParent()));	
		addField(new BooleanFieldEditor(PreferenceConstants.J_regexFilterEnabled,	"use REGEX instead of GLOB filename filters", getFieldEditorParent()));
		addField(new BooleanFieldEditor(PreferenceConstants.J_filterLowerCase,	"filter lowercase", getFieldEditorParent()));
		
		String ending = ".:";
		// string settings with  with names matching specified GLOB/REGEX expression
		addField(new StringFieldEditor(PreferenceConstants.J_include,	"include source and target files/directories"+ending, getFieldEditorParent()));	
		addField(new StringFieldEditor(PreferenceConstants.J_exclude,	"exclude source and target files/directories"+ending, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.J_includeSource,	"include source files/directories"+ending, getFieldEditorParent()));	
		addField(new StringFieldEditor(PreferenceConstants.J_excludeSource,	"exclude source files/directories"+ending, getFieldEditorParent()));	
		addField(new StringFieldEditor(PreferenceConstants.J_includeTarget,	"include target files/directories"+ending, getFieldEditorParent()));
		addField(new StringFieldEditor(PreferenceConstants.J_excludeTarget,	"exclude target files/directories"+ending, getFieldEditorParent()));	
		
		// long settings
		addField(new StringFieldEditor(PreferenceConstants.J_timeTolerance,	"time-tolerance (in milliseconds)"+ending, getFieldEditorParent()));
	}	
}
