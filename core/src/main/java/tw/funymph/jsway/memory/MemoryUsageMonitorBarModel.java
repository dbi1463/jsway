/* MemoryUsageMonitorBarModel.java created on 2011/9/20
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

import javax.swing.DefaultBoundedRangeModel;

/**
 * An abstract class extends {@link javax.swing.DefaultBoundedRangeModel DefaultBoundedRangeModel}
 * as the model of the {@link javax.swing.JProgressBar JProgressBar} that is also the base class of
 * {@link MemoryUsageMonitorBar}. This class also defines additional methods for its implementors.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public abstract class MemoryUsageMonitorBarModel extends DefaultBoundedRangeModel implements MemoryUsageListener {

	private static final long serialVersionUID = 3723854087615261620L;

	public static final int MINIMUM_MEMORY_USAGE = 0;
	public static final int MAXIMUM_MEMORY_USAGE = 100;

	private static final int DEFAULT_VALUE = 0;
	private static final int DEFAULT_EXTENT = 0;

	/**
	 * Construct a <code>MemoryUsageMonitorBarModel</code> with the default values
	 * for {@link javax.swing.DefaultBoundedRangeModel DefaultBoundedRangeModel}.
	 */
	protected MemoryUsageMonitorBarModel() {
		super(DEFAULT_VALUE, DEFAULT_EXTENT, MINIMUM_MEMORY_USAGE, MAXIMUM_MEMORY_USAGE);
	}

	/**
	 * Set the memory usage text formatter that can offered a formatted text to
	 * showing the memory usage.
	 * 
	 * @param formatter the memory usage text formatter
	 */
	public abstract void setMemoryUsageTextFormatter(MemoryUsageTextFormatter formatter);

	/**
	 * Get the memory usage ratio
	 * 
	 * @return the memory usage
	 */
	public abstract double getMemoryUsageRatio();

	/**
	 * Get the formatted text for the memory usage
	 * 
	 * @return the formatted text
	 */
	public abstract String getFormattedMemoryUsageText();
}
