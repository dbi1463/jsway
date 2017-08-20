/* SimpleFolderFilter.java created on 2011/10/13
 *
 * Copyright (C) 2011. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * A simple FileFilter that accept folders only (files are filtered out).
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class SimpleFolderFilter extends FileFilter {

	/**
	 * The default description of the filter that is constructed by the
	 * default constructor.
	 */
	public static final String DEFAULT_DESCRIPTION = "Select a folder.";

	private String fileDescription;

	/**
	 * Construct a <code>SimpleFolderFilter</code> instance with the default
	 * description {@link #DEFAULT_DESCRIPTION} ({@value #DEFAULT_DESCRIPTION}).
	 */
	public SimpleFolderFilter() {
		this(DEFAULT_DESCRIPTION);
	}

	/**
	 * Construct a <code>SimpleFolderFilter</code> instance with the
	 * specified description.
	 * 
	 * @param description the description about the filter
	 */
	public SimpleFolderFilter(String description) {
		fileDescription = description;
	}

	@Override
	public boolean accept(File file) {
		return file.isDirectory();
	}

	@Override
	public String getDescription() {
		return fileDescription;
	}
}
