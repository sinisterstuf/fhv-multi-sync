package at.fhv.multisync.model;

import java.util.List;

import at.fhv.multisync.model.helper.SerializationHelper;

/**
 * 
 * The JobModel class responsible for loading all jobs from the file system.
 * 
 * @author Michael Sieber
 */
public class JobModel {
	private static JobModel INSTANCE;
	private final List<JobGroup> _groups;

	/**
	 * Default constructor.
	 */
	private JobModel() {
		_groups = SerializationHelper.deserialize();
	}

	/**
	 * Get the singleton instance of this object
	 * 
	 * @return The singleton JobModel instance
	 */
	public static JobModel getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new JobModel();
		}

		return INSTANCE;
	}

	/**
	 * @return The groups
	 */
	public List<JobGroup> getGroups() {
		return _groups;
	}
}
