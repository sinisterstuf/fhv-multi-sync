package at.fhv.multisync.model.provider.file;

import java.io.File;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import at.fhv.multisync.Activator;

/**
 * 
 * The FileLabelProvider for providing the labels for files.
 * 
 * @author Michael Sieber
 */
public class FileLabelProvider extends LabelProvider {
	private final Image FOLDER = Activator.getImageDescriptor(
			"icons/folder.png").createImage();
	private final Image FILE = Activator.getImageDescriptor(
			"icons/document.png").createImage();

	@Override
	public String getText(Object element) {
		return ((File) element).getName();
	}

	@Override
	public Image getImage(Object element) {
		if (((File) element).isDirectory()) {
			return FOLDER;
		} else {
			return FILE;
		}
	}
}
