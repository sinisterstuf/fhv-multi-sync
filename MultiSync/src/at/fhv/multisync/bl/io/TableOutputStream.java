package at.fhv.multisync.bl.io;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 
 * The TableOutputStream class which writes the console ouput to a table
 * 
 * @author Michael Sieber
 */
public class TableOutputStream extends PrintWriter {

	/**
	 * Create a new TableOutputStream
	 * 
	 * @param out The output stream to write to. This will not be used
	 */
	public TableOutputStream(OutputStream out) {
		super(out);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void print(String s) {
		// TODO implement
	}
}
