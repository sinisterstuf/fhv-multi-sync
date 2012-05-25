package at.fhv.multisync.ui.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.part.ViewPart;

public class LogView extends ViewPart {
	private Table _logTable;

	public LogView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		_logTable = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		_logTable.setHeaderVisible(true);
		_logTable.setLinesVisible(true);

		TableColumn tblclmnFile = new TableColumn(_logTable, SWT.NONE);
		tblclmnFile.setWidth(433);
		tblclmnFile.setText("File");

		TableColumn tblclmnDirection = new TableColumn(_logTable, SWT.NONE);
		tblclmnDirection.setWidth(139);
		tblclmnDirection.setText("Direction");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
}