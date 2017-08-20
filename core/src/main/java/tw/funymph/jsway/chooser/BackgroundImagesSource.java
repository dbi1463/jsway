/* RowBackgroundSource.java created on 2013/5/5
 *
 * Copyright (C) 2013. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.chooser;

import java.awt.Paint;

/**
 * The class can implement this interface to offer the background
 * images painted in the {@link ImageChooser}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.2
 */
public interface BackgroundImagesSource {

	/**
	 * Get the background image for each row in the {@link ImageChooser}.
	 * 
	 * @return the background image
	 */
	Paint getRowBackgroundImage();

	/**
	 * Get the background image used in the action bar in the {@link ImageChooser}.
	 *   
	 * @return the background image
	 */
	Paint getActionBarBackgroundImage();

	/**
	 * Get the background image used in the navigation bar in the {@link ImageChooser}.
	 *   
	 * @return the background image
	 */
	Paint getNavigationBarBackgroundImage();
}
