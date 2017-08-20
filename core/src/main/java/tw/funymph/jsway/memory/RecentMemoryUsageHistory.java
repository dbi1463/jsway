/* RecentMemoryUsageHistory.java created on 2011/9/23
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

import tw.funymph.jsway.utils.RecentHistory;

/**
 * A class can implement this interface to offer a recent memory
 * usage event history.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface RecentMemoryUsageHistory extends MemoryUsageListener {

	/**
	 * Get the recent memory usage event history.
	 * 
	 * @return the recent memory usage event history
	 */
	RecentHistory<MemoryUsageEvent> getEvents();

	/**
	 * Add the recent history change listener.
	 * 
	 * @param listener the listener
	 */
	void addRecentHistoryChangeListener(RecentMemoryUsageHistoryListener listener);

	/**
	 * Remove the recent history change listener.
	 * 
	 * @param listener the listener
	 */
	void removeRecentHistoryChangeListener(RecentMemoryUsageHistoryListener listener);

	/**
	 * Notify all listeners when the recent events are changed.
	 */
	void notifyRecentHistoryChangeListeners();
}
