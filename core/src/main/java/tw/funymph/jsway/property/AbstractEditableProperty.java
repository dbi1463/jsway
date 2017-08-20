/* AbstractEditableProperty.java created on 2011/9/29
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
import tw.funymph.jsway.MultiLanguageSupportListener;

/**
 * A class that offers the most common implementation of {@link EditableProperty}
 * interface to reduce the duplicated code and effect of creating new property type.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0 
 */
public abstract class AbstractEditableProperty implements EditableProperty {

	protected MultiLanguageSupport multiLangSupport;
	protected EditablePropertyCoreImpl implementation;

	/**
	 * Construct an <code>AbstractEditableProperty</code> instance with the
	 * specified name by it children.
	 * 
	 * @param name the name of the property
	 */
	protected AbstractEditableProperty(String name) {
		this(name, simpleSupport(name));
	}

	/**
	 * Construct an <code>AbstractEditableProperty</code> instance with the
	 * specified name and the multiple language support by it children.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	protected AbstractEditableProperty(String name, MultiLanguageSupport support) {
		multiLangSupport = requireNonNull(support, NULL_LANGUAGE_SUPPORT_EXCEPTION);
		implementation = new EditablePropertyCoreImpl(name, this);
	}

	/**
	 * Set the name of the property
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		implementation.setName(name);
	}

	@Override
	public Class<?> getPropertyClass() {
		return getClass();
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

	@Override
	public void setUIEditable(boolean enabled) {
		implementation.setEnabled(enabled);
	}

	@Override
	public String getName() {
		return implementation.getName();
	}

	@Override
	public boolean isUIEditable() {
		return implementation.isUIEditable();
	}

	@Override
	public void addEditablePropertyListener(EditablePropertyListener listener) {
		implementation.addEditablePropertyListener(listener);
	}

	@Override
	public void removeEditablePropertyListener(EditablePropertyListener listener) {
		implementation.removeEditablePropertyListener(listener);
	}

	/**
	 * Notify all registered listeners that the property has been changed.
	 * 
	 * @param key the key of changed value
	 * @param value the changed value
	 */
	protected void notifyEditablePropertyListeners(String key, Object value, Object oldValue) {
		implementation.notifyEditablePropertyListeners(this, key, value, oldValue);
	}
}
