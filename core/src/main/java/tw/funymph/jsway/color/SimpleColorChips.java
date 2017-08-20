/* SimpleColorChips.java created on 2012/4/28
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
package tw.funymph.jsway.color;

import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;

import java.util.ArrayList;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.property.ColorProperty;

/**
 * A simple implementation of {@link ColorChips}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class SimpleColorChips extends ColorChips {

	private ArrayList<ColorProperty> colors;

	/**
	 * Construct a <code>SimpleColorChips</code> instance with
	 * the specified name. 
	 * 
	 * @param name the name of the simple color chips
	 */
	public SimpleColorChips(String name) {
		super(name, simpleSupport(name));
	}

	/**
	 * Construct a <code>SimpleColorChips</code> instance with
	 * the specified name and the multiple language support
	 * 
	 * @param name the name of the simple color chips
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	public SimpleColorChips(String name, MultiLanguageSupport support) {
		super(name, support);
		colors = new ArrayList<ColorProperty>();
	}

	/**
	 * Add a color into the color chips.
	 * 
	 * @param color the color to be added
	 */
	public void addColor(ColorProperty color) {
		colors.add(color);
	}

	/**
	 * Remove a color from the color chips.
	 * 
	 * @param color the color to be removed
	 */
	public void removeColor(ColorProperty color) {
		colors.remove(color);
	}

	@Override
	public int getColorCount() {
		return colors.size();
	}

	@Override
	public ColorProperty getEditableColor(int index) {
		if(index > 0 && index < colors.size()) {
			return colors.get(index);
		}
		return null;
	}
}
