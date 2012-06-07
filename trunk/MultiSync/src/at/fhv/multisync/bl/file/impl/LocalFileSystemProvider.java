package at.fhv.multisync.bl.file.impl;

import java.io.File;

import at.fhv.multisync.bl.file.FileSystemProvider;

/**
 * 
 * File system provider for local file system.
 * 
 * @author Michael Sieber
 */
public class LocalFileSystemProvider implements FileSystemProvider {
	private final String _name = "Local File System";

	@Override
	public File[] getRoot() {
		return File.listRoots();
	}

	@Override
	public String toString() {
		return _name;
	}
}
