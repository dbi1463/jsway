/* BooleanPropertyWrapper.java created on 2012/10/13
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
package tw.funymph.jsway.property;

import tw.funymph.jsway.MultiLanguageSupport;

/**
 * A class that wraps an existing boolean property (e.g., {@link tw.jsway.memory.MemoryUsageMonitor#getAutoRecycleProperty()})
 * to provide the display texts (name, on text, and off text) in different languages.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class BooleanPropertyWrapper extends BooleanProperty implements EditablePropertyListener {

	private BooleanProperty warppedProperty;

	/**
	 * Construct a wrapper instance with the actual property and the multiple language support.
	 * 
	 * @param property the actual property to be wrapped
	 * @param support the multiple language support
	 * @throws NullPointerException if either the property or the multiple language support is null
	 */
	public BooleanPropertyWrapper(BooleanProperty property, MultiLanguageSupport support) {
		super(property.getName(), support);
		warppedProperty = property;
		warppedProperty.addEditablePropertyListener(this);
	}

	/**
	 * Construct a wrapper instance with the actual property and the multiple language supports
	 * for the name, the on text, and the off text.
	 * 
	 * @param property the actual property to be wrapped
	 * @param support the multiple language support
	 * @param onText the support to get the text when the value is true (on)
	 * @param offText the support to get the text when the value is false (off)
	 * @throws NullPointerException if any of the property and the multiple language supports is null
	 */
	public BooleanPropertyWrapper(BooleanProperty property, MultiLanguageSupport support, MultiLanguageSupport onText, MultiLanguageSupport offText) {
		super(property.getName(), support, onText, offText, false);
		warppedProperty = property;
		warppedProperty.addEditablePropertyListener(this);
	}

	@Override
	public Object getValue() {
		return warppedProperty.getValue();
	}

	@Override
	public void setValue(Object value) {
		warppedProperty.setValue(value);
	}

	@Override
	public Class<?> getPropertyClass() {
		return warppedProperty.getPropertyClass();
	}

	@Override
	public void setUIEditable(boolean enabled) {
		warppedProperty.setUIEditable(enabled);
	}

	@Override
	public boolean isUIEditable() {
		return warppedProperty.isUIEditable();
	}

	@Override
	public String getStatusText() {
		return warppedProperty.getBooleanValue()? getOnText() : getOffText();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		notifyEditablePropertyListeners(event.getKey(), event.getCurrentValue(), event.getOldValue());
	}
}
