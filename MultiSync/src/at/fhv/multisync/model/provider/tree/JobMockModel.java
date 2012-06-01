package at.fhv.multisync.model.provider.tree;

import java.util.ArrayList;
import java.util.List;

import at.fhv.multisync.model.Job;
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

	static {
		// FIXME for testing only
		JobGroup group = new JobGroup();
		Job j1 = new Job("Job 1");
		j1.addSlave("C:\\tmp");
		j1.setMaster("C:\\master");
		group.addJob(j1);
		JOBS = new ArrayList<JobGroup>();
		JOBS.add(group);
	}

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
