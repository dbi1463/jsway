/* MemoryUtilities.java created on 2011/9/23
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
package tw.funymph.jsway.memory;

/**
 * A helper utility class for the memory usage package.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class MemoryUtilities {

	public static final String UNIT_KB = "kB";
	public static final String UNIT_MB = "MB";
	public static final String UNIT_GB = "GB";
	public static final String UNIT_TB = "TB";

	public static final double KILLOBYTES = 1024.0;
	public static final double MEGABYTES = 1048576.0;
	public static final double GIGABYTES = 1073741824.0;
	public static final double TERABYTES = 1099511627776.0;

	/**
	 * Disable creation
	 */
	private MemoryUtilities() { }

	/**
	 * Get the memory size in kB.
	 * 
	 * @param size the memory size in bytes.
	 * @return the memory size in kB.
	 */
	public static double getKBSize(long size) {
		return (double)size / KILLOBYTES;
	}

	/**
	 * Get the memory size in MB.
	 * 
	 * @param size the memory size in bytes.
	 * @return the memory size in MB.
	 */
	public static double getMBSize(long size) {
		return (double)size / MEGABYTES;
	}

	/**
	 * Get the memory size in GB.
	 * 
	 * @param size the memory size in bytes.
	 * @return the memory size in GB.
	 */
	public static double getGBSize(long size) {
		return (double)size / GIGABYTES;
	}

	/**
	 * Get the memory size in TB.
	 * 
	 * @param size the memory size in bytes.
	 * @return the memory size in TB.
	 */
	public static double getTBSize(long size) {
		return (double)size / TERABYTES;
	}

	/**
	 * Change the event source for the specified listener.
	 *  
	 * @param oldHistory the old history
	 * @param newHistory the new history
	 * @param listener the listener that will be notified by the new event source
	 */
	public static void changeRecnetMemoryUsageHistory(RecentMemoryUsageHistory oldHistory, RecentMemoryUsageHistory newHistory, RecentMemoryUsageHistoryListener listener) {
		if(oldHistory != null && oldHistory != newHistory) {
			oldHistory.removeRecentHistoryChangeListener(listener);
		}
		if(newHistory != null) {
			newHistory.addRecentHistoryChangeListener(listener);
		}
	}
}
