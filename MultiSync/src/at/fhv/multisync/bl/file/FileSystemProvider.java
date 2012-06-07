package at.fhv.multisync.bl.file;

import java.io.File;

/**
 * 
 * Interface for all file system providers.
 * 
 * @author Michael Sieber
 */
public interface FileSystemProvider {
	public static final String ID = "at.fhv.multisync.FileSystemProvider";

	/**
	 * Get all root folder of the file system
	 * 
	 * @return An array of all root folders
	 */
	public File[] getRoot();
}
