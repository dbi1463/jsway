/* RecentHistory.java created on 2011/9/23
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
package tw.funymph.jsway.utils;

import java.util.Collection;

/**
 * A class can implement the <code>RecentHistory</code> to offer a container
 * that the size of the container is always less than or equal to the maximum
 * capacity.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface RecentHistory<E> extends Collection<E> {

	/**
	 * Set the maximum recent event capacity. Note that if the new capacity
	 * is less than the old capacity, a normalization should be executed.
	 * 
	 * @param capacity the new capacity
	 */
	void setMaximumRecentEventCapacity(int capacity);

	/**
	 * Get the maximum recent event capacity.
	 * 
	 * @return the maximum recent event capacity.
	 */
	int getMaximumRecentEventCapacity();

	/**
	 * Normalize the history so that the amount of the events in the history
	 * is less than or equal to the maximum recent event capacity.
	 */
	void normalize();

	/**
	 * Get the event on the specified index
	 * 
	 * @param index the specified index
	 * @return the event on the specified index
	 */
	E get(int index);
}
