/* ColorUtilities.java created on 2011/9/15
 *
 * Copyright (C) 2011.  Pin-Ying Tu all rights reserved.
 *
 * This file is a part of JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.color;

import static java.lang.Integer.parseInt;

import java.awt.Color;

import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.property.EditablePropertyListener;
import tw.funymph.jsway.utils.NumberUtilities;

/**
 * A set of useful color related methods. 
 *
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class ColorUtilities {
	
	private static final String HTML_COLOR_PREFIX = "#";

	public static final int HTML_COLOR_CHANNEL_STRING_LENGTH = 2;
	public static final int MAXIMUM_COLOR_CHANNEL_VALUE = 255;

	private static final int RED_START_INDEX = 1;
	private static final int RED_END_INDEX = 3;
	private static final int GREEN_START_INDEX = 3;
	private static final int GREEN_END_INDEX = 5;
	private static final int BLUE_START_INDEX = 5;
	private static final int RADIX = 16;

	/**
	 * Disable creation.
	 */
	private ColorUtilities() {}

	/**
	 * Get a hex string that presents the given color.
	 * 
	 * @param color the color to present
	 * @return a color information in the hex format, e.g., #FFFFFF.
	 */
	public static String toHexString(Color color) {
		String string = HTML_COLOR_PREFIX;
		string += NumberUtilities.toHexString(color.getRed());
		string += NumberUtilities.toHexString(color.getGreen());
		string += NumberUtilities.toHexString(color.getBlue());
		return string;
	}

	/**
	 * Get a color from the information presented in a hex string.
	 * 
	 * @param color the color information
	 * @return a color
	 */
	public static Color fromHexString(String color) {
		int red = parseInt(color.substring(RED_START_INDEX, RED_END_INDEX), RADIX);
		int green = parseInt(color.substring(GREEN_START_INDEX, GREEN_END_INDEX), RADIX);
		int blue = parseInt(color.substring(BLUE_START_INDEX), RADIX);
		return new Color(red, green, blue);
	}

	/**
	 * Change the event source for the specified listener.
	 * 
	 * @param oldSource the old event source
	 * @param newSource the new event source
	 * @param listener the listener that will be notified by the new event source
	 */
	public static void changeColorPropertyEventSource(ColorProperty oldSource, ColorProperty newSource, EditablePropertyListener listener) {
		if(oldSource != newSource) {
			if(oldSource != null) {
				oldSource.removeEditablePropertyListener(listener);
			}
			newSource.addEditablePropertyListener(listener);
		}
	}

	/**
	 * Change the event source for the specified listener.
	 * 
	 * @param oldColors the old event source
	 * @param newColors the new event source
	 * @param listener the listener that will be notified by the new event source
	 */
	public static void chageLeveledColorsEventSource(LeveledColors oldColors, LeveledColors newColors, EditablePropertyListener listener) {
		if(oldColors != newColors) {
			if(oldColors != null) {
				int levels = oldColors.getColorCount();
				for(int level = 0; level < levels; level++) {
					oldColors.getEditableColor(level).removeEditablePropertyListener(listener);
				}
			}
			int levels = newColors.getColorCount();
			for(int level = 0; level < levels; level++) {
				newColors.getEditableColor(level).addEditablePropertyListener(listener);
			}
		}
	}
}
