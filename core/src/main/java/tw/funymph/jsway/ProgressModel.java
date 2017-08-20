/* ProgressModel.java created on 2012/9/9
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

import javax.swing.BoundedRangeModel;

/**
 * An interface that extends the <code>BoundedRangeModel</code> to offer more functionalities.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface ProgressModel extends BoundedRangeModel {

	/**
	 * Get the information about the current progress.
	 * 
	 * @return the information
	 */
	String getProgressInfo();
}
