/* ColorProperty.java created on 2011/9/20
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
package tw.funymph.jsway.property;

import static java.awt.Color.white;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;

import java.awt.Color;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A class that extends {@link AbstractEditableProperty} to manages a color value.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class ColorProperty extends AbstractEditableProperty {

	private Color currentColor;
	private Color oldColor;

	/**
	 * Construct a <code>ColorProperty</code> instance with the specified
	 * name and the default color (white).
	 * 
	 * @param name the name of the color
	 * @throws NullPointerException if the name is null
	 */
	public ColorProperty(String name) {
		this(name, white);
	}

	/**
	 * Construct a <code>ColorProperty</code> instance with the specified name,
	 * the multiple language support, and the initial color.
	 * 
	 * @param name the name of the color
	 * @param support the multiple language support
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	public ColorProperty(String name, MultiLanguageSupport support) {
		this(name, support, white);
	}

	/**
	 * Construct a <code>ColorProperty</code> instance with the specified
	 * name and the initial color.
	 * 
	 * @param name the name of the color
	 * @param color the initial color
	 * @throws NullPointerException if any argument is null
	 */
	public ColorProperty(String name, Color color) {
		this(name, simpleSupport(name), color);
	}

	/**
	 * Construct a <code>ColorProperty</code> instance with the specified name,
	 * the multiple language support, and the initial color.
	 * 
	 * @param name the name of the color
	 * @param support the multiple language support
	 * @param color the initial color
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	public ColorProperty(String name, MultiLanguageSupport support, Color color) {
		super(name, support);
		setCurrentColor(color);
	}

	/**
	 * Get the old color
	 * 
	 * @return the old (replaced) color
	 */
	public Color getOldColor() {
		return oldColor;
	}

	/**
	 * Set the current color. Note that the specified color can not be null, and
	 * the replacement will invoke notification to all registered color change listeners.
	 * 
	 * @param color the new color
	 * @throws NullPointerException if the color is null
	 */
	public void setCurrentColor(Color color) {
		ObjectUtilities.requireNonNull(color);
		if(!color.equals(currentColor)) {
			oldColor = currentColor;
			currentColor = color;
			notifyEditablePropertyListeners(KEY_VALUE, currentColor, oldColor);
		}
	}

	/**
	 * Get the current color.
	 * 
	 * @return the current color
	 */
	public Color getColor() {
		return currentColor;
	}

	@Override
	public Object getValue() {
		return currentColor;
	}

	@Override
	public void setValue(Object value) {
		setCurrentColor((Color)value);
	}
}
