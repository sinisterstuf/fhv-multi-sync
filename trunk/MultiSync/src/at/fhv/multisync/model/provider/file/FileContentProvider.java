package at.fhv.multisync.model.provider.file;

import java.io.File;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * Content provider for files.
 * 
 * @author Michael Sieber
 */
public class FileContentProvider implements ITreeContentProvider {
	private File _directory;

	/**
	 * Create a new FileContentProvider
	 * 
	 * @param directory
	 *            The root directory of the content
	 */
	public FileContentProvider(File directory) {
		_directory = directory;
	}

	@Override
	public void dispose() {
		// nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		_directory = (File) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return _directory.listFiles();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		File tmp = (File) parentElement;
		return tmp.listFiles();
	}

	@Override
	public Object getParent(Object element) {
		return ((File) element).getParent();
	}

	@Override
	public boolean hasChildren(Object element) {
		return ((File) element).isDirectory();
	}
}
