package at.fhv.multisync.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import at.fhv.multisync.model.helper.SerializationHelper;

/**
 * 
 * This class represents a group of jobs.
 * 
 * @author Michael Sieber
 */
public class JobGroup implements Serializable {
	private static final long serialVersionUID = -3930715948623517171L;

	private final ArrayList<Job> _jobs;
	private String _name;

	/**
	 * Default constructor.
	 * 
	 * @param name
	 *            The group name
	 */
	public JobGroup(String name) {
		_name = name;
		_jobs = new ArrayList<Job>();
	}

	/**
	 * Save this jobGroup by serializing it to the file system.
	 */
	public void save() {
		SerializationHelper.serialize(this);
	}

	/**
	 * Add a job to the group.
	 * 
	 * @param job
	 *            The job to add
	 */
	public void addJob(Job job) {
		if (!_jobs.contains(job)) {
			job.setParent(this);
			_jobs.add(job);
		}
	}

	/**
	 * Remove a job from the group.
	 * 
	 * @param job
	 *            The job to remove
	 */
	public void removeJob(Job job) {
		if (_jobs.contains(job)) {
			_jobs.remove(job);
		}
	}

	/**
	 * Run all jobs in this group.
	 */
	public void runAll() {
		for (Job curr : _jobs) {
			curr.run();
		}
	}

	/**
	 * Get the name of the group
	 * 
	 * @return The name of the group
	 */
	public String getName() {
		return _name;
	}

	/**
	 * Set the group name
	 * 
	 * @param name
	 *            The name of the group
	 */
	public void setName(String name) {
		_name = name;
	}

	/**
	 * Get the jobs of this group
	 * 
	 * @return The jobs
	 */
	public List<Job> getJobs() {
		return _jobs;
	}

}
