/* RecentMemoryUsageHistoryListener.java created on 2011/9/23
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

/** A class can implement this interface to receive and handle the
 * events fired from a {@link RecentMemoryUsageHistory}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface RecentMemoryUsageHistoryListener {

	/**
	 * Invoke when the content of the specific {@link RecentMemoryUsageHistory}
	 * is changed.
	 * 
	 * @param history the event source
	 */
	void historyChanged(RecentMemoryUsageHistory history);
}
