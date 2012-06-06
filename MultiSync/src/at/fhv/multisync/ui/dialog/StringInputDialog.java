package at.fhv.multisync.ui.dialog;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * 
 * The StringInputDialog class which represents a dialog with an text input
 * field for strings.
 * 
 * @author Michael Sieber
 */
public class StringInputDialog extends TitleAreaDialog {
	private Text _inputText;
	private String _input;
	private final String _textLabel;
	private final String _title;

	/**
	 * Create a new StringInputDialog
	 * 
	 * @param parentShell
	 *            The parent shell
	 */
	public StringInputDialog(Shell parentShell, String title, String textLabel) {
		super(parentShell);
		_title = title;
		_textLabel = textLabel;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void create() {
		super.create();
		setTitle(_title);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		// layout.horizontalAlignment = GridData.FILL;
		parent.setLayout(layout);

		// The text fields will grow with the size of the dialog
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;

		Label label1 = new Label(parent, SWT.NONE);
		label1.setText(_textLabel);

		_inputText = new Text(parent, SWT.BORDER);
		_inputText.setLayoutData(gridData);

		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.horizontalSpan = 3;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.CENTER;

		parent.setLayoutData(gridData);
		// Create Add button
		// Own method as we need to overview the SelectionAdapter
		createOkButton(parent, OK, "Add", true);
		// Add a SelectionListener

		// Create Cancel button
		Button cancelButton = createButton(parent, CANCEL, "Cancel", false);
		// Add a SelectionListener
		cancelButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				cancelPressed();
			}
		});
	}

	/**
	 * We need to have the textFields into Strings because the UI gets disposed
	 * and the Text Fields are not accessible any more.
	 */
	private void saveInput() {
		_input = _inputText.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void okPressed() {
		saveInput();
		super.okPressed();
	}

	/**
	 * Create the ok button with validation
	 * 
	 * @param parent
	 * @param id
	 * @param label
	 * @param defaultButton
	 * @return
	 */
	protected Button createOkButton(Composite parent, int id, String label,
			boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns++;
		Button button = new Button(parent, SWT.PUSH);
		button.setText(label);
		button.setFont(JFaceResources.getDialogFont());
		button.setData(new Integer(id));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				if (isValidInput()) {
					okPressed();
				}
			}
		});
		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
		}
		setButtonLayoutData(button);
		return button;
	}

	/**
	 * Validate the input
	 * 
	 * @return True if the input is valid
	 */
	private boolean isValidInput() {
		boolean valid = true;
		if (_inputText.getText().length() == 0) {
			setErrorMessage("Please enter value.");
			valid = false;
		}
		return valid;
	}

	/**
	 * Get the input from the box.
	 * 
	 * @return The input
	 */
	public String getInput() {
		return _input;
	}

}
