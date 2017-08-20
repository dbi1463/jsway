/* RecentHistoryList.java created on 2011/9/23
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
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A default implementation of {@link RecentHistory} that uses
 * {@link java.util.LinkedList LinkedList} as the container.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class LinkedRecentHistory<E> implements RecentHistory<E> {

	private int maxEventCapacity;

	private LinkedList<E> events;

	/**
	 * Construct a <code>RecentHistoryList</code> instance by specifying
	 * the maximum event capacity.
	 * 
	 * @param maxCapacity the maximum event capacity.
	 */
	public LinkedRecentHistory(int maxCapacity) {
		events = new LinkedList<E>();
		setMaximumRecentEventCapacity(maxCapacity);
	}

	@Override
	public int size() {
		return events.size();
	}

	@Override
	public boolean isEmpty() {
		return events.isEmpty();
	}

	@Override
	public boolean contains(Object event) {
		return events.contains(event);
	}

	@Override
	public Iterator<E> iterator() {
		return events.iterator();
	}

	@Override
	public Object[] toArray() {
		return events.toArray();
	}

	@Override
	public <T> T[] toArray(T[] array) {
		return events.toArray(array);
	}

	@Override
	public boolean add(E event) {
		boolean result = events.add(event);
		normalize();
		return result;
	}

	@Override
	public boolean remove(Object event) {
		return events.remove(event);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return events.containsAll(collection);
	}

	@Override
	public boolean addAll(Collection<? extends E> collection) {
		boolean result = events.addAll(collection);
		normalize();
		return result;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		return events.removeAll(collection);
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		boolean result = events.retainAll(collection);
		normalize();
		return result;
	}

	@Override
	public void clear() {
		events.clear();
	}

	@Override
	public void setMaximumRecentEventCapacity(int capacity) {
		maxEventCapacity = capacity;
		normalize();
	}

	@Override
	public int getMaximumRecentEventCapacity() {
		return maxEventCapacity;
	}

	@Override
	public void normalize() {
		while(events.size() > maxEventCapacity) {
			events.removeFirst();
		}
	}

	@Override
	public E get(int index) {
		return events.get(index);
	}
}
