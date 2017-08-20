/* MemoryUsageMonitor.java created on 2011/9/21
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

import static java.lang.Runtime.getRuntime;
import static java.lang.System.gc;

import java.util.LinkedList;
import java.util.List;

import tw.funymph.jsway.property.BooleanProperty;
import tw.funymph.jsway.property.NumberProperty;

/**
 * A timing memory usage monitor.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class MemoryUsageMonitor extends TimingMonitor implements MemoryUsageNotifier, AutoMemoryRecycler {

	private static final double DEFAULT_RECYCLE_BOUNDARY = 0.75;

	private static final String AUTO_RECYCLE_PROPERTY_NAME = "Auto Recycle";
	private static final String RECYCLE_BOUNDARY_PROPERTY_NAME = "Recycle Boundary";

	public static final int DEFAULT_MONITOR_PERIOD = 1000;

	private static MemoryUsageMonitor instance;

	private BooleanProperty autoRecycle;
	private NumberProperty<Double> recycleBoundary;

	private MemoryUsageEvent lastEvent;
	private List<MemoryUsageListener> listeners;

	/**
	 * Get the system single instance of <code>MemoryUsageMonitor</code>.
	 * 
	 * @return the system single instance
	 */
	public static MemoryUsageMonitor getInstance() {
		if(instance == null) {
			instance = new MemoryUsageMonitor();
		}
		return instance;
	}

	/**
	 * Construct a <code>MemoryUsageMonitor</code> instance with the default
	 * monitor period ({@link #DEFAULT_MONITOR_PERIOD}) and disable the
	 * auto-recycling.
	 */
	public MemoryUsageMonitor() {
		this(DEFAULT_MONITOR_PERIOD, false);
	}

	/**
	 * Construct a <code>MemoryUsageMonitor</code> instance by specifying
	 * the monitor period and whether enable auto-recycling.
	 * 
	 * @param period the period to monitor the memory usage
	 * @param recycle enable or disable the auto recycling
	 */
	public MemoryUsageMonitor(int period, boolean recycle) {
		listeners = new LinkedList<MemoryUsageListener>();
		autoRecycle = new BooleanProperty(AUTO_RECYCLE_PROPERTY_NAME, recycle);
		recycleBoundary = new NumberProperty<Double>(RECYCLE_BOUNDARY_PROPERTY_NAME, DEFAULT_RECYCLE_BOUNDARY, 0.0, 1.0, 0.01);
		calculateMemoryUsage();
		setMonitorPeriod(period);
		startMonitor();
	}

	/**
	 * Get the property that is used to enable/disable auto recycle.
	 * 
	 * @return the property that is used to enable/disable auto recycle
	 */
	public BooleanProperty getAutoRecycleProperty() {
		return autoRecycle;
	}

	/**
	 * Get the property that is used to decide when to invoke garbage collection.
	 * 
	 * @return the property that is used to decide when to invoke garbage collection
	 */
	public NumberProperty<Double> getRecycleBoundaryProperty() {
		return recycleBoundary;
	}

	@Override
	public void addMemoryUsageUpdateListener(MemoryUsageListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeMemoryUsageUpdateListener(MemoryUsageListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void notifyMemoryUsageUpdateListeners() {
		for(MemoryUsageListener listener : listeners) {
			listener.memoryUsageUpdated(lastEvent);
		}
	}

	@Override
	public MemoryUsageEvent getLastNotifiedEvent() {
		return lastEvent;
	}

	@Override
	protected void monitor() {
		calculateMemoryUsage();
		notifyMemoryUsageUpdateListeners();
		if(autoRecycle.getBooleanValue() && lastEvent.getMemoryUsageRatio() > getRecycleBoundary()) {
			gc();
		}
	}

	@Override
	public boolean isAutoRecycle() {
		return autoRecycle.getBooleanValue();
	}

	@Override
	public void setAutoRecycle(boolean recycle) {
		autoRecycle.setBooleanValue(recycle);
	}

	@Override
	public void setRecycleBoundary(double boundary) {
		recycleBoundary.setValue(boundary);
	}

	@Override
	public double getRecycleBoundary() {
		return recycleBoundary.getNumber().doubleValue();
	}

	/**
	 * Calculate the memory usage.
	 */
	private void calculateMemoryUsage() {
		long totalMemory = getRuntime().totalMemory();
		long freeMemory = getRuntime().freeMemory();
		long usedMemory = totalMemory - freeMemory;
		double ratio = (double)usedMemory / (double)totalMemory;
		lastEvent = new MemoryUsageEvent(System.currentTimeMillis(), freeMemory, usedMemory, totalMemory, ratio);
	}
}
