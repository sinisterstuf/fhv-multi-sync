package at.fhv.multisync.bl.helper;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import at.fhv.multisync.bl.file.FileSystemProvider;

/**
 * 
 * Static helper class for plugin interaction.
 * 
 * @author Michael Sieber
 */
public class PluginHelper {

	/**
	 * Private constructor to avoid instances of static helper class
	 */
	private PluginHelper() {
		// nothing
	}

	/**
	 * Get a list of all available file system providers
	 * 
	 * @return A list of all available file system providers
	 */
	public static FileSystemProvider[] getFileSystemProviders() {
		List<FileSystemProvider> ret = new ArrayList<FileSystemProvider>();

		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(FileSystemProvider.ID);

		try {
			for (IConfigurationElement element : config) {
				Object obj = element.createExecutableExtension("class");
				if (obj instanceof FileSystemProvider) {
					ret.add((FileSystemProvider) obj);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret.toArray(new FileSystemProvider[ret.size()]);
	}
}
