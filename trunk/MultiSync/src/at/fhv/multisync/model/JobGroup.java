package at.fhv.multisync.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * This class represents a group of jobs.
 * 
 * @author Michael Sieber
 */
public class JobGroup implements Serializable {
	private static final long serialVersionUID = -3930715948623517171L;

	private final List<Job> _jobs;

	private String _name;

	/**
	 * Default constructor.
	 */
	public JobGroup() {
		_jobs = new ArrayList<Job>();
	}

	/**
	 * Add a job to the group.
	 * 
	 * @param job
	 *            The job to add
	 */
	public void addJob(Job job) {
		if (!_jobs.contains(job)) {
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
}
