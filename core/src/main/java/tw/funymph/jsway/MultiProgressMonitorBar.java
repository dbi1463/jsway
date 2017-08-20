/* MultiProgressMonitorBar.java created on 2012/9/9
 *
 * Copyright (C) 2012. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoundedRangeModel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A progress monitor bar that shows different progresses. There are two modes to display
 * the progresses: cyclically or on-update. In the cyclically mode, the monitor bar will
 * start up a timer and display one progress per specified period (default is 1 second).
 * In the on-update mode (the default mode), the monitor bar displays the latest-updated
 * progress.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
public class MultiProgressMonitorBar extends JProgressBar implements ChangeListener, ActionListener {

	private static final long serialVersionUID = -5782544706544363592L;

	private static final int DEFAULT_DELAY_TIME = 1000;

	private static final boolean DEFAULT_CYCLE_DISPLAY = false;

	private int displayIndex;
	private boolean cyclicallyDisplay;

	private Timer timer;
	private LinkedList<BoundedRangeModel> progresses;

	/**
	 * Construct an on-update mode <code>MultiProgressMonitorBar</code> instance.
	 */
	public MultiProgressMonitorBar() {
		this(DEFAULT_CYCLE_DISPLAY);
	}

	/**
	 * Construct a <code>MultiProgressMonitorBar</code> instance with the specified mode.
	 */
	public MultiProgressMonitorBar(boolean cyclicallyDisplay) {
		progresses = new LinkedList<BoundedRangeModel>();
		setCyclicallyDisplay(cyclicallyDisplay);
		setStringPainted(true);
	}

	/**
	 * Set the display mode: cyclically (true) or on-update (false). If the display mode
	 * is cyclically, use the default period (1 second) to start up the timer.
	 * 
	 * @param cyclicallyDisplay true enable cyclically display
	 */
	public void setCyclicallyDisplay(boolean cyclicallyDisplay) {
		setCyclicallyDisplay(cyclicallyDisplay, DEFAULT_DELAY_TIME);
	}

	/**
	 * Set the display mode: cyclically (true) or on-update (false). If the display mode
	 * is cyclically, use the specified period to start up the timer.
	 * 
	 * @param cyclically true enable cyclically display
	 * @param period the period in millisecond to start up the timer
	 */
	public void setCyclicallyDisplay(boolean cyclically, int period) {
		if(cyclicallyDisplay != cyclically) {
			cyclicallyDisplay = cyclically;
			setCyclicalPeriod(period);
		}
	}

	/**
	 * Set the cyclically display period. Note that if the display mode is on-update,
	 * calling this method has no effects.
	 * 
	 * @param period the period in millisecond to start up the timer 
	 */
	public void setCyclicalPeriod(int period) {
		cancelTimer();
		if(cyclicallyDisplay) {
			timer = new Timer(period, this);
			timer.start();
		}
	}

	/**
	 * Add the progress to be monitored
	 * 
	 * @param progress the progress to be monitored
	 */
	public void addMonitorProgress(BoundedRangeModel progress) {
		if(progress != null && !progresses.contains(progress)) {
			progress.addChangeListener(this);
			progresses.add(progress);
		}
	}

	/**
	 * Remove the progress
	 * 
	 * @param progress the progress to be removed
	 */
	public void removeMonitorProgress(BoundedRangeModel progress) {
		if(progress != null && progresses.contains(progress)) {
			progress.removeChangeListener(this);
			progresses.remove(progress);
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		updateProgressModel((BoundedRangeModel)e.getSource());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(progresses.isEmpty()) {
			return;
		}
		displayIndex++;
		BoundedRangeModel progress =  progresses.get((displayIndex % progresses.size()));
		updateProgressModel(progress);
	}

	/**
	 * Cancel the running timer if it exists.
	 */
	private void cancelTimer() {
		if(timer != null && timer.isRunning()) {
			timer.stop();
			timer = null;
		}
	}

	/**
	 * Use the external model to update the internal model
	 * 
	 * @param progress the external model
	 */
	private void updateProgressModel(BoundedRangeModel progress) {
		setModel(progress);
		if(progress instanceof ProgressModel) {
			setString(((ProgressModel)progress).getProgressInfo());
		}
	}
}
