/* ColorChips.java created on 2011/9/29
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

import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.Color;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.NamedObject;
import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.property.DefaultEditableProperties;
import tw.funymph.jsway.property.EditableProperties;

/**
 * A abstract class that defines how to interact with a set of editable colors.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public abstract class ColorChips implements NamedObject, MultiLanguageSupport {

	protected String propertyName;
	protected MultiLanguageSupport multiLangSupport;

	/**
	 * Construct a <code>ColorChips</code> instance with the name by
	 * its children.
	 * 
	 * @param name the name for the colors
	 */
	protected ColorChips(String name) {
		this(name, simpleSupport(name));
	}

	/**
	 * Construct a <code>ColorChips</code> instance with the name and
	 * the multi-language support by its children.
	 * 
	 * @param name the name for the colors
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	protected ColorChips(String name, MultiLanguageSupport support) {
		propertyName = requireNonNull(name);
		multiLangSupport = requireNonNull(support);
	}

	/**
	 * Get the number of colors in the color chips.
	 * 
	 * @return the number of colors
	 */
	public abstract int getColorCount();

	/**
	 * Get the index-specified editable color.
	 * 
	 * @param index the index of the editable color
	 * @return the editable color
	 */
	public abstract ColorProperty getEditableColor(int index);

	/**
	 * Get the index-specified color.
	 * 
	 * @param index the index of the color
	 * @return the color
	 */
	public Color getColor(int index) {
		return getEditableColor(index).getColor();
	}

	/**
	 * Convert to editable properties.
	 * 
	 * @return editable properties
	 */
	public EditableProperties toEditableProperties() {
		EditableProperties properties = new DefaultEditableProperties(getName(), multiLangSupport);
		int count = getColorCount();
		for(int index = 0; index < count; index++) {
			properties.addEditableProperty(getEditableColor(index));
		}
		return properties;
	}

	@Override
	public String getName() {
		return propertyName;
	}

	@Override
	public String getDisplayText() {
		return multiLangSupport.getDisplayText();
	}

	@Override
	public void addMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		multiLangSupport.addMultiLanguageSupportListener(listener);
	}

	@Override
	public void removeMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		multiLangSupport.removeMultiLanguageSupportListener(listener);
	}
}
