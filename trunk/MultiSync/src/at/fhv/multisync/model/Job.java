package at.fhv.multisync.model;

import java.io.Serializable;
import java.util.ArrayList;
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

	private String _name;

	/* process source directory/file */
	private String _master;
	/* process target directory/file */
	private List<String> _slaves = new ArrayList<String>();
	private JobGroup _parent;

	/*
	 * SYNC - PARAMETERS
	 * 
	 * For help please visit Class Sync_original: private static void
	 * printUsage()
	 */

	/* simulate only; do not modify target */
	private boolean _simulateOnly = false;
	/* ignore warnings; do not pause */
	private boolean _ignoreWarnings = false;
	/* do not recurse into subdirectories */
	private boolean _noRecurse = false;
	/* do not use filename for file-matching */
	private boolean _noNameMatch = true;
	/* do not use last-modified time for file-matching */
	private boolean _noTimeMatch = true;
	/* time-tolerance (in milliseconds) */
	private final long _timeTolerance = 0L;
	/* do not use CRC-32 checksum for file-matching */
	private boolean _noCrcMatch = true;
	/* rename matched target files? */
	private boolean _renameTarget = false;
	/* overwrite existing target files? */
	private boolean _overwriteTarget = true;
	/* use std logging */
	private final boolean _stdLog = true;
	/* */
	private String _logFile;
	/* synchronize time of matched target files */
	private final boolean _syncTimeOfTarget = true;
	/* delete unmatched target files/directories */
	private final boolean _deleteTarget = true;
	/* */
	private final boolean _filterRelativePathname = false;
	/* */
	private final boolean _filterLowerCase = false;
	/* use REGEX instead of GLOB filename filters */
	private final boolean _regexFilterEnabled = false;
	/*
	 * include source and target files/directories with names matching specified
	 * GLOB/REGEX expression
	 */
	private final String _include = "";
	/*
	 * exclude source and target files/directories with names matching specified
	 * GLOB/REGEX expression
	 */
	private final String _exclude = "";
	/*
	 * include source files/directories with names matching specified GLOB/REGEX
	 * expression
	 */
	private final String _includeSource = "";
	/*
	 * exclude source files/directories with names matching specified GLOB/REGEX
	 * expression
	 */
	private final String _excludeSource = "";
	/*
	 * include target files/directories with names matching specified GLOB/REGEX
	 * expression
	 */
	private final String _includeTarget = "";
	/*
	 * exclude target files/directories with names matching specified GLOB/REGEX
	 * expression
	 */
	private final String _excludeTarget = "";

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

	public void setName(String name) {
		this._name = name;
	}

	public String getName() {
		return _name;
	}

	public void setMaster(String source) {
		this._master = source;
	}

	public String getMaster() {
		return this._master;
	}

	public void SetSlaves(List<String> slaves) {
		this._slaves = slaves;
	}

	public List<String> getSlaves() {
		return this._slaves;
	}

	public void addSlave(String slave) {
		this._slaves.add(slave);
	}

	public void remSlave(String slave) {
		this._slaves.remove(slave);
	}

	/**
	 * @return The parent
	 */
	public JobGroup getParent() {
		return _parent;
	}

	/**
	 * @param parent
	 *            The parent to set
	 */
	public void setParent(JobGroup parent) {
		_parent = parent;
	}

	public void setSimulteOnly(boolean simulateOnly) {
		this._simulateOnly = simulateOnly;
	}

	public boolean isSimulateOnly() {
		return this._simulateOnly;
	}

	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this._ignoreWarnings = ignoreWarnings;
	}

	public boolean isIgnoreWarnings() {
		return this._ignoreWarnings;
	}

	public void setNoRecurse(boolean noRecurse) {
		this._noRecurse = noRecurse;
	}

	public boolean isNoRecurse() {
		return this._noRecurse;
	}

	public void setNoNameMatch(boolean noNameMatch) {
		this._noNameMatch = noNameMatch;
	}

	public boolean isNoNameMatch() {
		return this._noNameMatch;
	}

	public void setNoTimeMatch(boolean noTimeMatch) {
		this._noTimeMatch = noTimeMatch;
	}

	public boolean isNoTimeMatch() {
		return this._noTimeMatch;
	}

	public void setNoCrcMatch(boolean noCrcMatch) {
		this._noCrcMatch = noCrcMatch;
	}

	public boolean isNoCrcMatch() {
		return this._noCrcMatch;
	}

	public void setRenameTarget(boolean renameTarget) {
		this._renameTarget = renameTarget;
	}

	public boolean isRenameTarget() {
		return this._renameTarget;
	}

	public void setOverwriteTarget(boolean overwriteTarget) {
		this._overwriteTarget = overwriteTarget;
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

	/*
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_master == null) ? 0 : _master.hashCode());
		result = prime * result + ((_name == null) ? 0 : _name.hashCode());
		result = prime * result + ((_slaves == null) ? 0 : _slaves.hashCode());
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
		Job other = (Job) obj;
		if (_master == null) {
			if (other._master != null)
				return false;
		} else if (!_master.equals(other._master))
			return false;
		if (_name == null) {
			if (other._name != null)
				return false;
		} else if (!_name.equals(other._name))
			return false;
		if (_slaves == null) {
			if (other._slaves != null)
				return false;
		} else if (!_slaves.equals(other._slaves))
			return false;
		return true;
	}

}
