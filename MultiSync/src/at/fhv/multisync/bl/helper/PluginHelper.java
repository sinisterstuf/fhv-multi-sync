package at.fhv.multisync.bl.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import at.fhv.multisync.extension.FileSystemProvider;

/**
 * 
 * Static helper class for plugin interaction.
 * 
 * @author Michael Sieber
 */
public class PluginHelper {
	private static final Pattern _protocolPattern;
	private static final String _matchingProtocol = "[a-zA-Z]+://";

	static {
		_protocolPattern = Pattern.compile(_matchingProtocol);
	}

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

	/**
	 * Get a suitable provider for the given path
	 * 
	 * @param pathname The path to check
	 * @return The provider or null if no suitable provider could be found
	 */
	public static FileSystemProvider getSuitableProvider(String pathname) {
		FileSystemProvider[] providers = getFileSystemProviders();
		String protocol = getProtocol(pathname);

		for (FileSystemProvider provider : providers) {
			if (provider.canHandle(protocol)) {
				return provider;
			}
		}

		return null;
	}

	/**
	 * Get the protocol of a path name
	 * 
	 * @param pathname The pathname from which the protocol should be extracted
	 * @return The protocol or the pathname if no protocol was found
	 */
	private static String getProtocol(String pathname) {
		if (pathname != null) {
			pathname = pathname.replaceAll("\\\\", "//");
			Matcher matcher = _protocolPattern.matcher(pathname);

			if (matcher.find()) {
				return matcher.group();
			}
		}
		return pathname;
	}
}
