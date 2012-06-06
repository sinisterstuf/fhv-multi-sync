package at.fhv.multisync.ui.editors;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import at.fhv.multisync.model.Job;

/**
 * 
 * The JobEditorInput class represents the input model for the JobEditor.
 * 
 * @author Michael Sieber
 */
public class JobEditorInput implements IEditorInput {
	private final Job _job;

	/**
	 * Create a new JobEditorInput
	 * 
	 * @param job
	 *            The containing job
	 */
	public JobEditorInput(Job job) {
		_job = job;
	}

	/**
	 * Get the stored job
	 * 
	 * @return The job
	 */
	public Job getJob() {
		return _job;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public String getName() {
		return _job.getName();
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return _job.getName();
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_job == null) ? 0 : _job.hashCode());
		return result;
	}

	/*
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobEditorInput other = (JobEditorInput) obj;
		if (_job == null) {
			if (other._job != null)
				return false;
		} else if (!_job.equals(other._job))
			return false;
		return true;
	}

}
