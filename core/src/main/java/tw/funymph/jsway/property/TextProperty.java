/* TextProperty.java created on 2011/9/29
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

import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import tw.funymph.jsway.MultiLanguageSupport;

/**
 * A class that extends {@link AbstractEditableProperty} to manages a text value.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class TextProperty extends AbstractEditableProperty {

	private String value;

	/**
	 * Construct a <code>TextProperty</code> instance with the specified
	 * name and initial value.
	 * 
	 * @param name the name of the property
	 * @param text the initial value of the property
	 * @throws NullPointerException if any argument is null
	 */
	public TextProperty(String name, String text) {
		this(name, simpleSupport(name), text);
	}

	/**
	 * Construct a <code>TextProperty</code> instance with the specified
	 * name, the multiple language support, and the initial value.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param text the initial value of the property
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	public TextProperty(String name, MultiLanguageSupport support, String text) {
		super(name, support);
		setText(text);
	}

	/**
	 * Change the value of the property to the specified text.
	 * 
	 * @param text the new property value
	 */
	public void setText(String text) {
		requireNonNull(text, NULL_VALUE_EXCEPTION);
		if(!text.equals(value)) {
			String oldValue = value;
			value = text;
			notifyEditablePropertyListeners(KEY_VALUE, value, oldValue);
		}
	}

	/**
	 * Get the text of the property.
	 * 
	 * @return the text of the property
	 */
	public String getText() {
		return value;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object value) {
		setText((String)value);
	}
}
