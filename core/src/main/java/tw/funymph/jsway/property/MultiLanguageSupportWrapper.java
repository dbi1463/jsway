/* MultiLanguageSupportWrapper.java created on 2012/10/13
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
 * A class that wraps an existing property (e.g., {@link tw.jsway.memory.MemoryUsageMonitor#getRecycleBoundaryProperty()})
 * with the additional multiple language support.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class MultiLanguageSupportWrapper extends AbstractEditableProperty implements EditablePropertyListener {

	private EditableProperty wrappedProperty;

	/**
	 * Construct a wrapper instance with the actual property and the multiple
	 * language support.
	 * 
	 * @param property the actual property to be wrapped
	 * @param support the multiple language support
	 */
	public MultiLanguageSupportWrapper(EditableProperty property, MultiLanguageSupport support) {
		super(property.getName(), support);
		wrappedProperty = property;
		wrappedProperty.addEditablePropertyListener(this);
	}

	@Override
	public Object getValue() {
		return wrappedProperty.getValue();
	}

	@Override
	public void setValue(Object value) {
		wrappedProperty.setValue(value);
	}

	@Override
	public Class<?> getPropertyClass() {
		return wrappedProperty.getPropertyClass();
	}

	@Override
	public void setUIEditable(boolean enabled) {
		wrappedProperty.setUIEditable(enabled);
	}

	@Override
	public boolean isUIEditable() {
		return wrappedProperty.isUIEditable();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		notifyEditablePropertyListeners(event.getKey(), event.getCurrentValue(), event.getOldValue());
	}
}
