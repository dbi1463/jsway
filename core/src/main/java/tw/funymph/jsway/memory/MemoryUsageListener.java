/* MemoryUsageListener.java created on 2011/9/21
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
 * memory usage update events.
 *  
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface MemoryUsageListener {

	/**
	 * Invoke when the register memory usage monitor found that the
	 * memory usage is updated.
	 * 
	 * @param event the memory usage monitor event
	 */
	void memoryUsageUpdated(MemoryUsageEvent event);
}
