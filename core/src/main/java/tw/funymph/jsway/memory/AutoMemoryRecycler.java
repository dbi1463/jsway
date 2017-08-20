/* AutoMemoryRecycler.java created on 2011/9/21
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
 * A class can implement the <code>AutoMemoryRecycler</code> to invoke
 * garbage collection automatically when a specific memory usage is reached.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface AutoMemoryRecycler {

	/**
	 * Get whether the auto garbage collection is on
	 * 
	 * @return true if the garbage collection is invoked automatically
	 */
	boolean isAutoRecycle();

	/**
	 * Enable or disable the auto garbage collection
	 * 
	 * @param autoRecycle enable or disable
	 */
	void setAutoRecycle(boolean autoRecycle);

	/**
	 * Set the memory usage boundary to invoke garbage collection
	 * 
	 * @param boundary the memory usage boundary
	 */
	void setRecycleBoundary(double boundary);

	/**
	 * Get the memory usage boundary that is used to invoke garbage
	 * collection.
	 * 
	 * @return the memory usage boundary
	 */
	double getRecycleBoundary();
}
