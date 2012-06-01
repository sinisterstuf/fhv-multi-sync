package at.fhv.multisync.ui.views;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.part.ViewPart;

public class JobExplorer extends ViewPart {
	private Tree _jobTree;
	public static final String ID = "MultiSync.view.JobExplorer";

	public JobExplorer() {
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledComposite scrolledComposite =
				new ScrolledComposite(parent, SWT.BORDER | SWT.H_SCROLL
						| SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		_jobTree = new Tree(scrolledComposite, SWT.BORDER);
		scrolledComposite.setContent(_jobTree);
		scrolledComposite.setMinSize(_jobTree.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
		
		// This is new code
		// First we create a menu Manager
		//MenuManager menuManager = new MenuManager();
		//Menu menu = menuManager.createContextMenu(viewer.getTable());
		
		// Set the MenuManager
		//viewer.getTable().setMenu(menu);
		//getSite().registerContextMenu(menuManager, viewer);
		
		// Make the selection available
		//getSite().setSelectionProvider(viewer);
	}

	@Override
	public void setFocus() {
		_jobTree.setFocus();
	}
}
