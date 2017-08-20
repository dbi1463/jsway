/* DefaultMemoryUsageMonitorBarModel.java created on 2011/9/20
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

import static tw.funymph.jsway.memory.MemoryUsageMonitor.getInstance;
import static tw.funymph.jsway.memory.MemoryUtilities.UNIT_GB;
import static tw.funymph.jsway.memory.MemoryUtilities.UNIT_KB;
import static tw.funymph.jsway.memory.MemoryUtilities.UNIT_MB;
import static tw.funymph.jsway.memory.MemoryUtilities.getGBSize;
import static tw.funymph.jsway.memory.MemoryUtilities.getKBSize;
import static tw.funymph.jsway.memory.MemoryUtilities.getMBSize;

import java.text.NumberFormat;

/**
 * A default implementation of {@link MemoryUsageMonitorBarModel}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class DefaultMemoryUsageMonitorBarModel extends MemoryUsageMonitorBarModel {

	private static final long serialVersionUID = 6144341662519378338L;

	public static final int DISPLAY_PERCENTAGE_ONLY = 0;
	public static final int DISPLAY_IN_KILLOBYTES = 1;
	public static final int DISPLAY_IN_MEGABYTES = 2;
	public static final int DISPLAY_IN_GIGABYTES = 3;
	public static final int AUTO_DETECTION = 4;
	public static final int DEFAULT_DISPLAY_FORMAT = DISPLAY_IN_MEGABYTES;

	private static final String USAGE_FORMATE = "%1s %2s / %3s %4s";

	private static final int DEFAULT_FACTION_DIGITS = 1;

	private String formattedUsage;
	private NumberFormat numberFormatter;
	private MemoryUsageNotifier eventSource;
	private MemoryUsageTextFormatter textFormatter;

	private double memoryUsageRatio;

	/**
	 * Construct a <code>DefaultMemoryUsageMonitorBar</code> using the default
	 * display format: {@link #DISPLAY_IN_MEGABYTES} and the system single
	 * instance of {@link MemoryUsageMonitor}.
	 */
	public DefaultMemoryUsageMonitorBarModel() {
		this(getInstance());
	}

	/**
	 * Construct a <code>DefaultMemoryUsageMonitorBar</code> using the default
	 * display format: {@link #DISPLAY_IN_MEGABYTES} and the specific memory
	 * usage source.
	 * 
	 * @param source the memory usage source
	 */
	public DefaultMemoryUsageMonitorBarModel(MemoryUsageNotifier source) {
		this(DEFAULT_DISPLAY_FORMAT, source);
	}

	/**
	 * Construct a <code>DefaultMemoryUsageMonitorBar</code> by specifying the display
	 * format and the memory usage source. The available display formats:<br />
	 * <ol>
	 * <li>{@link #DISPLAY_PERCENTAGE_ONLY}: display the memory usage percentage only</li>
	 * <li>{@link #DISPLAY_IN_KILLOBYTES}: display the memory usage in how many kB</li>
	 * <li>{@link #DISPLAY_IN_MEGABYTES}: display the memory usage in how many MB</li>
	 * <li>{@link #DISPLAY_IN_GIGABYTES}: display the memory usage in how many GB</li>
	 * <li>{@link #AUTO_DETECTION}: the unit is detected automatically</li>
	 * </ol>
	 * 
	 * @param format the display format
	 * @param source the memory usage source
	 */
	public DefaultMemoryUsageMonitorBarModel(int format, MemoryUsageNotifier source) {
		setDisplayFormat(format);
		setMemoryUsageNotifier(source);
		setMaximumFractionDigits(DEFAULT_FACTION_DIGITS);
	}

	/**
	 * Set the display format. The available display formats are<br />
	 * <ol>
	 * <li>{@link #DISPLAY_PERCENTAGE_ONLY}: display the memory usage percentage only</li>
	 * <li>{@link #DISPLAY_IN_KILLOBYTES}: display the memory usage in how many kB</li>
	 * <li>{@link #DISPLAY_IN_MEGABYTES}: display the memory usage in how many MB</li>
	 * <li>{@link #DISPLAY_IN_GIGABYTES}: display the memory usage in how many GB</li>
	 * <li>{@link #AUTO_DETECTION}: the unit is detected automatically</li>
	 * </ol>
	 * 
	 * @param format the new display format
	 */
	public void setDisplayFormat(int format) {
		switch(format) {
		case DISPLAY_PERCENTAGE_ONLY:
			textFormatter = new DefaultMemoryUsageFormatter();
			break;
		case DISPLAY_IN_KILLOBYTES:
			textFormatter = new MemoryUsageWithKilloBytesUnit();
			break;
		case DISPLAY_IN_GIGABYTES:
			textFormatter = new MemoryUsageWithGigaBytesUnit();
			break;
		case DISPLAY_IN_MEGABYTES:
			textFormatter = new MemoryUsageWithMegaBytesUnit();
			break;
		};
	}

	/**
	 * Set the maximum number of digits allowed in the fraction portion of a number. 
	 * 
	 * @param digits the maximum number of digits allowed in the fraction portion of a number
	 */
	public void setMaximumFractionDigits(int digits) {
		if(numberFormatter == null) {
			numberFormatter = NumberFormat.getInstance();
		}
		numberFormatter.setMaximumFractionDigits(digits);
	}

	/**
	 * Set the memory usage notifier that will update the memory usage periodically.
	 * If the the current source and the given source is not the same, the method will
	 * remove it from the current source listeners, and register it into the give source.
	 * 
	 * @param source the source to get the memory usage
	 */
	public void setMemoryUsageNotifier(MemoryUsageNotifier source) {
		if(eventSource != source) {
			if(eventSource != null) {
				eventSource.removeMemoryUsageUpdateListener(this);
			}
			if((eventSource = source) != null) {
				eventSource.addMemoryUsageUpdateListener(this);
			}
		}		
	}

	@Override
	public void memoryUsageUpdated(MemoryUsageEvent event) {
		memoryUsageRatio = event.getMemoryUsageRatio();
		formattedUsage = textFormatter.format(event);
		setValue((int)(memoryUsageRatio * MAXIMUM_MEMORY_USAGE));
	}

	@Override
	public void setMemoryUsageTextFormatter(MemoryUsageTextFormatter formatter) {
		textFormatter = formatter;
	}

	@Override
	public String getFormattedMemoryUsageText() {
		return formattedUsage;
	}

	@Override
	public double getMemoryUsageRatio() {
		return memoryUsageRatio;
	}

	/**
	 * The default memory usage formatter that return null to let
	 * {@link javax.swing.JProgressBar JProgressBar} decide what to display.
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.0
	 */
	private class DefaultMemoryUsageFormatter implements MemoryUsageTextFormatter {

		@Override
		public String format(MemoryUsageEvent event) {
			return null;
		}
	}

	/**
	 * The formatter is used when the display format is specified as
	 * {@link DefaultMemoryUsageMonitorBarModel#DISPLAY_IN_KILLOBYTES DISPLAY_IN_KILLOBYTES}.
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.0
	 */
	private class MemoryUsageWithKilloBytesUnit implements MemoryUsageTextFormatter {

		@Override
		public String format(MemoryUsageEvent event) {
			String used = numberFormatter.format(getKBSize(event.getUsedMemorySize()));
			String total = numberFormatter.format(getKBSize(event.getTotalMemorySize()));
			return String.format(USAGE_FORMATE, used, UNIT_KB, total, UNIT_KB);
		}
	}

	/**
	 * The formatter is used when the display format is specified as
	 * {@link DefaultMemoryUsageMonitorBarModel#DISPLAY_IN_MEGABYTES DISPLAY_IN_MEGABYTES}.
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.0
	 */
	private class MemoryUsageWithMegaBytesUnit implements MemoryUsageTextFormatter  {

		@Override
		public String format(MemoryUsageEvent event) {
			String used = numberFormatter.format(getMBSize(event.getUsedMemorySize()));
			String total = numberFormatter.format(getMBSize(event.getTotalMemorySize()));
			return String.format(USAGE_FORMATE, used, UNIT_MB, total, UNIT_MB);
		}
	}

	/**
	 * The formatter is used when the display format is specified as
	 * {@link DefaultMemoryUsageMonitorBarModel#DISPLAY_IN_GIGABYTES}.
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.0
	 */
	private class MemoryUsageWithGigaBytesUnit implements MemoryUsageTextFormatter {

		@Override
		public String format(MemoryUsageEvent event) {
			String used = numberFormatter.format(getGBSize(event.getUsedMemorySize()));
			String total = numberFormatter.format(getGBSize(event.getTotalMemorySize()));
			return String.format(USAGE_FORMATE, used, UNIT_GB, total, UNIT_GB);
		}
	}
}
