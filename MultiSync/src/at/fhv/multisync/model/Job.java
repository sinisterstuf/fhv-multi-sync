package at.fhv.multisync.model;

import java.io.Serializable;
import java.util.List;

import at.fhv.multisync.bl.Sync;

/**
 * 
 * This class represents a single sync job.
 * 
 * @author Michael Sieber
 * 
 * 
 *         26.05.2012 mwe2644 added all parameters for sync
 * 
 */
public class Job implements Serializable {
	private static final long serialVersionUID = -5521745757247879979L;

	private final String _name;

	/* process source directory/file */
	private String _master;
	/* process target directory/file */
	private List<String> _slaves;

	/*
	 * SYNC - PARAMETERS
	 * 
	 * For help please visit Class Sync_original: private static void
	 * printUsage()
	 */

	/* simulate only; do not modify target */
	private boolean _simulateOnly;
	/* ignore warnings; do not pause */
	private boolean _ignoreWarnings;
	/* do not recurse into subdirectories */
	private boolean _noRecurse;
	/* do not use filename for file-matching */
	private boolean _noNameMatch;
	/* do not use last-modified time for file-matching */
	private boolean _noTimeMatch;
	/* do not use CRC-32 checksum for file-matching */
	private boolean _noCrcMatch;
	/* rename matched target files? */
	private boolean _renameTarget;
	/* overwrite existing target files? */
	private boolean _overwriteTarget;
	/* use std logging */
	private boolean _stdLog;
	private String _logFile;
	/* time-tolerance (in milliseconds) */
	private long _timeTolerance;
	private boolean _syncTimeOfTarget;
	private boolean _deleteTarget;
	private boolean _filterRelativePathname;
	private boolean _filterLowerCase;
	private boolean _regexFilterEnabled;
	private String _include;
	private String _exclude;
	private String _includeSource;
	private String _excludeSource;
	private String _includeTarget;
	private String _excludeTarget;

	/**
	 * Create a new Job
	 * 
	 * @param name
	 *            The name of the job
	 */
	public Job(String name) {
		_name = name;
	}

	/**
	 * Run this job.
	 */
	public void run() {
		Sync.syncJob(this);
	}

	public String getName() {
		return _name;
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

	public boolean isNoNameMatch() {
		return this._noNameMatch;
	}

	public boolean isNoTimeMatch() {
		return this._noTimeMatch;
	}

	public boolean isNoCrcMatch() {
		return this._noCrcMatch;
	}

	public boolean isRenameTarget() {
		return this._renameTarget;
	}

	public boolean isOverwriteTarget() {
		return this._overwriteTarget;
	}

	public boolean isStdLog() {
		return this._stdLog;
	}

	public String getLogFile() {
		return this._logFile;
	}

	public long getTimeTolerance() {
		return this._timeTolerance;
	}

	public boolean isSyncTimeOfTarget() {
		return this._syncTimeOfTarget;
	}

	public boolean isDeleteTarget() {
		return this._deleteTarget;
	}

	public boolean isFilterRelativePathname() {
		return this._filterRelativePathname;
	}

	public boolean isFilterLowerCase() {
		return this._filterLowerCase;
	}

	public boolean isRegexFilterEnabled() {
		return this._regexFilterEnabled;
	}

	public String getInclude() {
		return this._include;
	}

	public String getExclude() {
		return this._exclude;
	}

	public String getIncludeSource() {
		return this._includeSource;
	}

	public String getExcludeSource() {
		return this._excludeSource;
	}

	public String getIncludeTarget() {
		return this._includeTarget;
	}

	public String getExcludeTarget() {
		return this._excludeTarget;
	}
}
