/* AddibleLeveledColorsListener.java created on 2011/9/23
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
package tw.funymph.jsway.color;

import tw.funymph.jsway.property.ColorProperty;

/**
 * A class can implement this interface to receive and handle the
 * color added/removed events from {@link AddibleLeveledColors}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface AddibleLeveledColorsListener {

	/**
	 * Invoked when a color is added into the {@link AddibleLeveledColors}.
	 * 
	 * @param ratio the ratio of the added color
	 * @param color the added color
	 */
	void colorAdded(double ratio, ColorProperty color);

	/**
	 * Invoked when a color is removed from the {@link AddibleLeveledColors}.
	 * 
	 * @param ratio the ratio of the removed color
	 * @param color the removed color
	 */
	void colorRemoved(double ratio, ColorProperty color);
}
