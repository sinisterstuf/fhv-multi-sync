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
	private List<String> _slaves;
	private boolean _simulateOnly;
	private boolean _ignoreWarnings;

	/**
	 * Default constructor.
	 */
	public Job() {

	}

	/**
	 * Run this job.
	 */
	public void run() {
		Sync.syncJob(this);
	}

	public List<String> getSlaves() {
		return this._slaves;
	}

	public String getMaster() {
		return this._master;
	}

	public boolean isSimulateOnly() {
		return this._simulateOnly;
	}

	public boolean isIgnoreWarnings() {
		return this._ignoreWarnings;
	}
}
