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

	/* process source directory/file */
	private String _master;
	/* process target directory/file */
	private List<String> _slaves;
	/* simulate only; do not modify target */
	private boolean _simulateOnly;
	/* ignore warnings; do not pause */
	private boolean _ignoreWarnings;
	/* do not recurse into subdirectories */
	private boolean _noRecurse;
	/* do not use filename for file-matching */
	private boolean _matchName;
	/* do not use last-modified time for file-matching */
	private boolean _matchTime;
	/* do not use CRC-32 checksum for file-matching */
	private boolean _matchCrc;
	/* rename matched target files? */
	private boolean _renameTarget;

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

	public boolean isNoRecurse() {
		return this._noRecurse;
	}

	public boolean isMatchName() {
		return this._matchName;
	}

	public boolean isMatchTime() {
		return this._matchTime;
	}

	public boolean isMatchCrc() {
		return this._matchCrc;
	}

	public boolean isRenameTarget() {
		return this._renameTarget;
	}
}
