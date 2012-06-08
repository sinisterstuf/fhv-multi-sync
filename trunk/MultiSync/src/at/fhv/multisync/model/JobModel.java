package at.fhv.multisync.model;

import java.util.ArrayList;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PlatformUI;

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

	/**
	 * Default constructor.
	 */
	private JobModel() {

		_groups = new ArrayList<JobGroup>();
		// FIXME for testing only
		JobGroup group = new JobGroup("MyGroup");
		Job j1 = new Job("Job 1");
		j1.addSlave("C:\\tmp");
		j1.setMaster("C:\\master");
		group.addJob(j1);
		_groups.add(group);

		// add a 'WorkBenchListener called when exiting the application
		PlatformUI.getWorkbench().addWorkbenchListener(
				new IWorkbenchListener() {

					@Override
					public boolean preShutdown(IWorkbench workbench,
							boolean forced) {
						save();
						return true;
					}

					@Override
					public void postShutdown(IWorkbench workbench) {
						// TODO Auto-generated method stub

					}
				});

		// load();

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
	 * Add a group.
	 * 
	 * @param group
	 *            The group to add.
	 */
	public void addGroup(JobGroup group) {
		_groups.add(group);
	}

	/**
	 * Remove a group
	 * 
	 * @param group
	 *            The group to remove
	 */
	public void removeGroup(JobGroup group) {
		_groups.remove(group);
	}

	/**
	 * Save the jobs
	 */
	public void save() {
		if (_groups != null) {
			SerializationHelper.serialize(_groups);
		}
	}

	/**
	 * Load the jobs
	 */
	public void load() {
		try {
			_groups = SerializationHelper.deserialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
