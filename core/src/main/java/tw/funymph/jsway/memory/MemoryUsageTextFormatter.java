/* MemoryUsageTextFormatter.java created on 2011/9/20
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

/**
 * A class can implement the this interface to generate suitable
 * text for showing memory usage.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface MemoryUsageTextFormatter {

	/**
	 * Gets the text to show the memory usage.
	 * 
	 * @param event the memory usage event
	 */
	String format(MemoryUsageEvent event);
}
