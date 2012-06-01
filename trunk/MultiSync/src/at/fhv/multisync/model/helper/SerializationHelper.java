package at.fhv.multisync.model.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

/**
 * 
 * Static helper class for doing object serialization and deserialisation.
 * 
 * @author Michael Sieber
 */
public class SerializationHelper {
	private static String DEFAULT_LOCATION;

	static {
		try {
			URL url = new URL(Platform.getInstallLocation().getURL()
					+ Platform.getProduct().getName());
			DEFAULT_LOCATION = url.toString();
		} catch (MalformedURLException e) {
			MessageDialog.openError(PlatformUI.getWorkbench().getDisplay()
					.getActiveShell(),
					"Error loading default location for storing jobs.",
					e.getMessage());
		}
	}

	/**
	 * Avoid instances of static helper class
	 */
	private SerializationHelper() {
		// nothing
	}

	/**
	 * Serialize an object to the default location
	 * 
	 * @param object The object to serialize
	 */
	public static void serialize(Serializable object) {
		if (DEFAULT_LOCATION != null && !DEFAULT_LOCATION.isEmpty()) {
			FileOutputStream fOut = null;
			ObjectOutputStream objOut = null;
			try {
				// create the stream
				fOut = new FileOutputStream(new File(DEFAULT_LOCATION));
				objOut = new ObjectOutputStream(fOut);

				// write
				objOut.writeObject(object);
			} catch (Exception e) {

				// show the error
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay()
						.getActiveShell(), "Error serializing object.",
						e.getMessage());
			} finally {

				// close object output
				if (objOut != null) {
					try {
						objOut.close();
					} catch (Exception e) {
						// ignore
					}
				}

				// close file output
				if (fOut != null) {
					try {
						fOut.close();
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}
	}

	/**
	 * Deserialize all objects from the location defined in DEFAULT_LOCATION
	 * 
	 * @return A list of all serialized object in the folder.
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> deserialize() {
		FileInputStream fIn = null;
		ObjectInputStream objIn = null;
		ArrayList<T> out = new ArrayList<T>();

		if (DEFAULT_LOCATION != null && !DEFAULT_LOCATION.isEmpty()) {
			try {
				// create the file object
				File location = new File(DEFAULT_LOCATION);

				// process directory
				if (location.isDirectory()) {
					for (File file : location.listFiles()) {
						// create the stream
						fIn = new FileInputStream(file);
						objIn = new ObjectInputStream(fIn);

						T tmp = (T) objIn.readObject();
						out.add(tmp);
					}
					// process single file
				} else {
					// create the stream
					fIn = new FileInputStream(location);
					objIn = new ObjectInputStream(fIn);

					T tmp = (T) objIn.readObject();
					out.add(tmp);
				}
			} catch (Exception e) {

				// show the error
				MessageDialog.openError(PlatformUI.getWorkbench().getDisplay()
						.getActiveShell(), "Error deserializing object.",
						e.getMessage());
			} finally {

				// close object output
				if (objIn != null) {
					try {
						objIn.close();
					} catch (Exception e) {
						// ignore
					}
				}

				// close file output
				if (fIn != null) {
					try {
						fIn.close();
					} catch (Exception e) {
						// ignore
					}
				}
			}
		}

		return out;
	}
}
