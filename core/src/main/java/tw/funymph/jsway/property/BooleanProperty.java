/* BooleanProperty.java created on 2011/9/29
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

import static java.lang.Boolean.valueOf;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;

/**
 * A class that extends {@link AbstractEditableProperty} to manages a boolean value.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class BooleanProperty extends AbstractEditableProperty implements MultiLanguageSupportListener {

	public static final String DEFAULT_ON_TEXT = "ON";
	public static final String DEFAULT_OFF_TEXT = "OFF";

	private Boolean value;
	private MultiLanguageSupport onTextSupport;
	private MultiLanguageSupport offTextSupport;

	/**
	 * Construct a <code>BooleanProperty</code> with the name and
	 * the default value (false).
	 * 
	 * @param name the name of the property
	 * @throws NullPointerException if the name is null
	 */
	public BooleanProperty(String name) {
		this(name, false);
	}

	/**
	 * Construct a <code>BooleanProperty</code> with the name and
	 * the default value (false).
	 * 
	 * @param name the name of the property
	 * @throws NullPointerException if either the name or the multiple language support is null
	 */
	public BooleanProperty(String name, MultiLanguageSupport support) {
		this(name, support, false);
	}

	/**
	 * Construct a <code>BooleanProperty</code> with the name and
	 * the initial value.
	 * 
	 * @param name the name of the property
	 * @param value the initial value
	 * @throws NullPointerException if the name is null
	 */
	public BooleanProperty(String name, boolean value) {
		this(name, DEFAULT_ON_TEXT, DEFAULT_OFF_TEXT, value);
	}

	/**
	 * Construct a <code>BooleanProperty</code> with the name, the
	 * initial value, and the multiple language support.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param value the initial value
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	public BooleanProperty(String name, MultiLanguageSupport support, boolean value) {
		this(name, support, simpleSupport(DEFAULT_ON_TEXT), simpleSupport(DEFAULT_OFF_TEXT), value);
	}

	/**
	 * Construct a <code>BooleanProperty</code> with the name, the
	 * initial value, and the multiple language support.
	 * 
	 * @param name the name of the property
	 * @param onText the display text when the value is true (on)
	 * @param offText the display text when the value is false (off)
	 * @param value the initial value
	 * @throws NullPointerException if any of the name, the on text, or the off text is null
	 * @since 1.1
	 */
	public BooleanProperty(String name, String onText, String offText, boolean value) {
		this(name, simpleSupport(name), simpleSupport(onText), simpleSupport(offText), value);
	}

	/**
	 * Construct a <code>BooleanProperty</code> with the name, the
	 * initial value, and the multiple language support.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param onText the support to get the display text when the value is true (on)
	 * @param offText the support to get the display text when the value is false (off)
	 * @param bool the initial value
	 * @throws NullPointerException if any of the name or the multiple language supports is null
	 * @since 1.1
	 */
	public BooleanProperty(String name, MultiLanguageSupport support, MultiLanguageSupport onText, MultiLanguageSupport offText, boolean bool) {
		super(name, support);
		onTextSupport = requireNonNull(onText, NULL_LANGUAGE_SUPPORT_EXCEPTION);
		offTextSupport = requireNonNull(offText, NULL_LANGUAGE_SUPPORT_EXCEPTION);
		onTextSupport.addMultiLanguageSupportListener(this);
		offTextSupport.addMultiLanguageSupportListener(this);
		value = bool;
	}

	/**
	 * Set the boolean value without warping the value as an object
	 * 
	 * @param newValue the new boolean value
	 */
	public void setBooleanValue(boolean newValue) {
		setValue(valueOf(newValue));
	}

	/**
	 * Get the boolean value without casting the return value
	 * of {@link #getValue()} to <code>Boolean</code>.
	 * 
	 * @return the boolean value of this property
	 */
	public boolean getBooleanValue() {
		return value.booleanValue();
	}

	/**
	 * Get the text for the current status, e.g., On or Off. 
	 * 
	 * @return the text for the current status
	 */
	public String getStatusText() {
		return getBooleanValue()? getOnText() : getOffText();
	}

	/**
	 * Get the text displayed when the property is true.
	 * 
	 * @return the text displayed when the property is true
	 */
	public String getOnText() {
		return onTextSupport.getDisplayText();
	}

	/**
	 * Get the text displayed when the property is false.
	 * 
	 * @return the text displayed when the property is false
	 */
	public String getOffText() {
		return offTextSupport.getDisplayText();
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public void setValue(Object newValue) {
		if(!newValue.equals(value)) {
			Boolean oldValue = new Boolean(value.booleanValue());
			value = (Boolean)newValue;
			notifyEditablePropertyListeners(KEY_VALUE, value, oldValue);
		}
	}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		notifyEditablePropertyListeners(KEY_DISPLAY_TEXT, source.getDisplayText(), null);
	}
}
