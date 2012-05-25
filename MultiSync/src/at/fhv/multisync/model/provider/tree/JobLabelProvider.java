package at.fhv.multisync.model.provider.tree;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import at.fhv.multisync.model.Job;
import at.fhv.multisync.model.JobGroup;

/**
 * 
 * The JobLabelProvider class which provides the labels for the tree.
 * 
 * @author Michael Sieber
 */
public class JobLabelProvider extends LabelProvider {
	@Override
	public String getText(Object element) {
		if (element instanceof JobGroup) {
			return ((JobGroup) element).getName();
		}
		return ((Job) element).getName();
	}

	@Override
	public Image getImage(Object element) {
		// TODO add image
		return super.getImage(element);
	}
}
