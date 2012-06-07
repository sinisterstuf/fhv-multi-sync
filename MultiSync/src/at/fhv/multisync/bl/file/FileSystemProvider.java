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

	/**
	 * This method is used to determine if a protocol can be handled by the
	 * plugin
	 * 
	 * @param protocol
	 *            The protocol to check e.g. ftp:// (FTP Server) or C:// (Local
	 *            Files)
	 * @return True if the plugin can handle the protocol
	 */
	public boolean canHandle(String protocol);
}
