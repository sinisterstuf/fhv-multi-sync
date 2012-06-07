package at.fhv.multisync.model.provider.file;

import java.io.File;
import java.io.FileFilter;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * 
 * Content provider for files.
 * 
 * @author Michael Sieber
 */
public class FileContentProvider implements ITreeContentProvider {
	private File[] _root;

	@Override
	public void dispose() {
		// nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		_root = (File[]) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return _root;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		File tmp = (File) parentElement;
		return tmp.listFiles(new DirectoryFileFilter());
	}

	@Override
	public Object getParent(Object element) {
		return ((File) element).getParentFile();
	}

	@Override
	public boolean hasChildren(Object element) {
		return ((File) element).isDirectory();
	}

	/**
	 * 
	 * FileFilter for showing only directories which are not hidden
	 * 
	 * @author Michael Sieber
	 */
	private class DirectoryFileFilter implements FileFilter {

		@Override
		public boolean accept(File pathname) {
			// only show directories which are not hidden
			return !pathname.isHidden() && pathname.isDirectory();
		}

	}
}
