package at.fhv.multisync.model.provider.file;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

/**
 * 
 * The FileLabelProvider for providing the labels for files.
 * 
 * @author Michael Sieber
 */
public class FileLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		return ((File) element).getName();
	}

	@Override
	public Image getImage(Object element) {
		// TODO add image
		return super.getImage(element);
	}
}
