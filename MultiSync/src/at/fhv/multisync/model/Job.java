package at.fhv.multisync.model;

import java.io.Serializable;
import java.util.List;

import at.fhv.multisync.bl.Sync;

/**
 * 
 * This class represents a single sync job.
 * 
 * @author Michael Sieber
 */
public class Job implements Serializable {
	private static final long serialVersionUID = -5521745757247879979L;

	private String _master;
	private List<String> _slave;

	/**
	 * Default constructor.
	 */
	public Job() {

	}

	/**
	 * Save this job by serializing it to the file system.
	 */
	public void save() {
		// TODO implement
	}

	/**
	 * Load the job
	 */
	public void load() {
		// TODO implement
	}

	/**
	 * Run this job.
	 */
	public void run() {
		Sync sync = new Sync();
		for (String slave : _slave) {
			sync.sync(_master, slave);
		}
	}
}
