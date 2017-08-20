/* DefaultMemoryUsageEventHistory.java created on 2011/9/23
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

import java.util.LinkedList;

import tw.funymph.jsway.utils.LinkedRecentHistory;
import tw.funymph.jsway.utils.RecentHistory;

/**
 * A default {@link RecentMemoryUsageEventHistory} implementation.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class DefaultMemoryUsageEventHistory implements RecentMemoryUsageHistory {

	public static final int DEFAULT_EVENT_CAPCAITY = 20;

	private LinkedRecentHistory<MemoryUsageEvent> events;
	private LinkedList<RecentMemoryUsageHistoryListener> listeners;

	/**
	 * Construct a <code>DefaultMemoryUsageEventHistory</code> with the
	 * default capacity.
	 */
	public DefaultMemoryUsageEventHistory() {
		this(DEFAULT_EVENT_CAPCAITY);
	}

	/**
	 * Construct a <code>DefaultMemoryUsageEventHistory</code> by specifying
	 * the default capacity.
	 */
	public DefaultMemoryUsageEventHistory(int capacity) {
		listeners = new LinkedList<RecentMemoryUsageHistoryListener>();
		events = new LinkedRecentHistory<MemoryUsageEvent>(capacity);
	}

	@Override
	public void memoryUsageUpdated(MemoryUsageEvent event) {
		events.add(event);
		notifyRecentHistoryChangeListeners();
	}

	@Override
	public RecentHistory<MemoryUsageEvent> getEvents() {
		return events;
	}

	@Override
	public void addRecentHistoryChangeListener(RecentMemoryUsageHistoryListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeRecentHistoryChangeListener(RecentMemoryUsageHistoryListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void notifyRecentHistoryChangeListeners() {
		for(RecentMemoryUsageHistoryListener listener : listeners) {
			listener.historyChanged(this);
		}
	}
}
