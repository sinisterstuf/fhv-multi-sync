package at.fhv.multisync.model.provider.tree;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import at.fhv.multisync.model.JobGroup;
import at.fhv.multisync.model.JobModel;

public class JobContentProvider implements ITreeContentProvider {
	private JobModel _model;

	@Override
	public void dispose() {
		// nothing
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		_model = (JobModel) newInput;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return _model.getGroups().toArray();
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof JobGroup) {
			return ((JobGroup) parentElement).getJobs().toArray();
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		return element instanceof JobGroup
				&& ((JobGroup) element).getJobs().size() > 0;
	}
}
