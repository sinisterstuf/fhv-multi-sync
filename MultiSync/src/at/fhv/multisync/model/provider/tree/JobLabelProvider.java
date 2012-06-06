package at.fhv.multisync.model.provider.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import at.fhv.multisync.Activator;
import at.fhv.multisync.model.Job;
import at.fhv.multisync.model.JobGroup;

/**
 * 
 * The JobLabelProvider class which provides the labels for the tree.
 * 
 * @author Michael Sieber
 */
public class JobLabelProvider extends LabelProvider {
	private final Image GROUP = Activator
			.getImageDescriptor("icons/folder.png").createImage();
	private final Image JOB = Activator
			.getImageDescriptor("icons/document.png").createImage();

	@Override
	public String getText(Object element) {
		if (element instanceof JobGroup) {
			return ((JobGroup) element).getName();
		}
		return ((Job) element).getName();
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof JobGroup) {
			return GROUP;
		} else {
			return JOB;
		}
	}
}
