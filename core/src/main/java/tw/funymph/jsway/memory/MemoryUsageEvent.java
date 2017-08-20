/* MemoryUsageEvent.java created on 2011/9/23
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
 * An event that is fired by a <code>MemoryUsageNotifier</code>
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class MemoryUsageEvent {

	private long freeMemory;
	private long usedMemory;
	private long totalMemory;
	private long timeStamp;
	private double usedRatio;

	/**
	 * Construct a <code>MemoryUsageEvent<code> instance with the required free memory size,
	 * used memory size, total memory size, and memory usage ratio at the time stamp.
	 * 
	 * @param time the time stamp when the event occurs
	 * @param free the free memory size at the time stamp
	 * @param used the used memory size at the time stamp
	 * @param total the total memory size at the time stamp
	 * @param ratio the memory usage ratio at the time stamp
	 */
	public MemoryUsageEvent(long time, long free, long used, long total, double ratio) {
		timeStamp = time;
		freeMemory = free;
		usedMemory = used;
		totalMemory = total;
		usedRatio = ratio;
	}

	/**
	 * Get the time stamp of the event.
	 * 
	 * @return the time stamp
	 */
	public long getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Get the free memory size.
	 * 
	 * @return the free memory size at the specific time
	 */
	public long getFreeMemorySize() {
		return freeMemory;
	}

	/**
	 * Get the used memory size.
	 * 
	 * @return the used memory size at the specific time
	 */
	public long getUsedMemorySize() {
		return usedMemory;
	}

	/**
	 * Get the total memory size.
	 * 
	 * @return the total memory size at the specific time
	 */
	public long getTotalMemorySize() {
		return totalMemory;
	}

	/**
	 * Get the memory usage ratio.
	 * 
	 * @return the memory usage ratio at the specific time
	 */
	public double getMemoryUsageRatio() {
		return usedRatio;
	}
}
