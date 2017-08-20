/* LeveledColors.java created on 2011/9/20
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

import java.awt.Color;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.property.ColorProperty;

/**
 * A class can extend the {@link LeveledColors} abstract class to offer
 * colors for different levels.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public abstract class LeveledColors extends ColorChips {

	/**
	 * Construct a <code>LeveledColors</code> instance with the name
	 * by its children.
	 * 
	 * @param name the name for the colors
	 */
	protected LeveledColors(String name) {
		super(name);
	}

	/**
	 * Construct a <code>LeveledColors</code> instance with the name
	 * and the multi-language support by its children.
	 * 
	 * @param name the name for the colors
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	protected LeveledColors(String name, MultiLanguageSupport support) {
		super(name, support);
	}

	/**
	 * Get the color for the specified value. This method use the {@link #findLevel(double)}
	 * method to get the color index in the color chip.
	 * 
	 * @param value the value
	 * @return the color for the value
	 */
	public Color getColor(double value) {
		return getColor(findLevel(value));
	}

	/**
	 * Get the editable color for the specified value. This method use the
	 * {@link #findLevel(double)} method to get editable color index in the
	 * color chip.
	 * 
	 * @param value the value
	 * @return the color for the value
	 */
	public ColorProperty getEditableColor(double value) {
		return getEditableColor(findLevel(value));
	}

	/**
	 * Get the level for the value.
	 * 
	 * @param value the value
	 * @return the level (index) of the color
	 */
	public abstract int findLevel(double value);
}
