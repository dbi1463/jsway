/* FileProperty.java created on 2012/5/2
 *
 * Copyright (C) 2012. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.property;

import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.io.File;

import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A class that extends {@link AbstractEditableProperty} to manages
 * a file or a directory.
 * 
 * @author Pin-Ying Tu
 * @version 1.1
 * @since 1.0
 */
public class FileProperty extends AbstractEditableProperty {

	public static final String KEY_EXTENSION = "FileProperty.extension";
	public static final String KEY_DIRECTORY = "FileProperty.directory";

	private File value;

	private String fileExtension;
	private boolean isDirectory;

	public FileProperty(String name) {
		this(name, null);
	}

	/**
	 * @param name
	 * @param file
	 */
	public FileProperty(String name, File file) {
		this(name, file, null);
	}

	/**
	 * @param name
	 * @param file
	 */
	public FileProperty(String name, File file, String extension) {
		super(name);
		setFile(file);
		isDirectory = false;
		fileExtension = extension;
	}

	public boolean isDirectory() {
		return isDirectory;
	}

	public String getExtension() {
		return fileExtension;
	}

	public File getFile() {
		return value;
	}

	public void setFile(File file) {
		requireNonNull(file, NULL_VALUE_EXCEPTION);
		if(!ObjectUtilities.equals(file, value)) {
			File oldFile = value;
			value = file;
			notifyEditablePropertyListeners(KEY_VALUE, value, oldFile);
		}
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		setFile((File)value);
	}
}
