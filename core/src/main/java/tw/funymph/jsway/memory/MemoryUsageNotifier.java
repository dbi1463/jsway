/* MemoryUsageNotifier.java created on 2011/9/21
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
 * A class can implement this interface to fire memory usage change events
 * to the registered memory usage update listeners.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface MemoryUsageNotifier {

	/**
	 * Add the memory usage listener.
	 * 
	 * @param listener the memory usage listener
	 */
	void addMemoryUsageUpdateListener(MemoryUsageListener listener);

	/**
	 * Remove the memory usage update listener.
	 * 
	 * @param listener the memory usage update listener.
	 */
	void removeMemoryUsageUpdateListener(MemoryUsageListener listener);

	/**
	 * Notify all registered memory usage update listeners.
	 */
	void notifyMemoryUsageUpdateListeners();

	/**
	 * Get the last notified memory usage event.
	 * 
	 * @return the last notified memory usage event.
	 */
	MemoryUsageEvent getLastNotifiedEvent();
}
