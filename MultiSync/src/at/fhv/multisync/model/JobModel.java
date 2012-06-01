package at.fhv.multisync.model;

import java.util.ArrayList;

import at.fhv.multisync.model.helper.SerializationHelper;

/**
 * 
 * The JobModel class responsible for loading all jobs from the file system.
 * 
 * @author Michael Sieber
 */
public class JobModel {
	private static JobModel INSTANCE;
	private ArrayList<JobGroup> _groups;

	{
		// FIXME for testing only
		JobGroup group = new JobGroup("MyGroup");
		Job j1 = new Job("Job 1");
		j1.addSlave("C:\\tmp");
		j1.setMaster("C:\\master");
		group.addJob(j1);
		_groups = new ArrayList<JobGroup>();
		_groups.add(group);
	}

	/**
	 * Default constructor.
	 */
	private JobModel() {
		// _groups = SerializationHelper.deserialize();
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
	public ArrayList<JobGroup> getGroups() {
		return _groups;
	}

	/**
	 * Save the jobs
	 */
	public void save() {
		if (_groups != null) {
			SerializationHelper.serialize(_groups);
		}
	}
}
