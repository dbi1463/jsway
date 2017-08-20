/* TimingMonitor.java created on 2011/9/21
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * An abstract class that provides the timer-related functionality
 * to facilitate the monitor implementation.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public abstract class TimingMonitor implements ActionListener {

	protected Timer timer;

	/**
	 * Set the monitor period.
	 * 
	 * @param period the monitor period
	 */
	public void setMonitorPeriod(int period) {
		if(timer != null) {
			timer.stop();
		}
		timer = new Timer(period, this);
	}

	/**
	 * Get the monitor period.
	 * 
	 * @return the monitor period
	 */
	public int getMonitorPeriod() {
		return (timer != null)? timer.getDelay() : 0;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		monitor();
	}

	/**
	 * Start the timing monitor.
	 */
	public void startMonitor() {
		timer.start();
	}

	/**
	 * Stop the timing monitor.
	 */
	public void stopMonitor() {
		timer.stop();
	}

	/**
	 * Monitor the specific resource. 
	 */
	protected abstract void monitor();
}
