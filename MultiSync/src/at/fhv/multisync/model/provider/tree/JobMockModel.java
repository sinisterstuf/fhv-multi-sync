package at.fhv.multisync.model.provider.tree;

import java.util.ArrayList;
import java.util.List;

import at.fhv.multisync.model.JobGroup;
import at.fhv.multisync.model.helper.SerializationHelper;

/**
 * 
 * The JobMockModel class provides the job model
 * 
 * @author Michael Sieber
 */
public class JobMockModel {
	private static ArrayList<JobGroup> JOBS;

	/**
	 * Load the jobs
	 * 
	 * @return The list of jobs
	 */
	public static List<JobGroup> loadJobs() {
		if (JOBS == null) {
			JOBS = SerializationHelper.deserialize();
		}

		return JOBS;
	}

	/**
	 * Save the jobs
	 */
	public static void saveJobs() {
		if (JOBS != null) {
			SerializationHelper.serialize(JOBS);
		}
	}
}
