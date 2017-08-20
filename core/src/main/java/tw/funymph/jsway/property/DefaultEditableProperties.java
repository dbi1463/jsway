/* DefaultEditableProperties.java created on 2011/9/29
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
import static tw.funymph.jsway.property.EditableProperty.NULL_LANGUAGE_SUPPORT_EXCEPTION;
import static tw.funymph.jsway.property.EditableProperty.NULL_NAME_EXCEPTION;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A default implementation of the {@link EditableProperties} interface.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class DefaultEditableProperties implements EditableProperties {

	private String propertiesName;
	private MultiLanguageSupport multiLangSupport;
	private List<EditableProperty> properties;
	private List<EditablePropertiesListener> listeners;

	/**
	 * Construct an empty <code>DefaultEditableProperties</code> instance with
	 * the name.
	 * 
	 * @param name the name of the property container
	 * @throws NullPointerException if the name is null
	 */
	public DefaultEditableProperties(String name) {
		this(name, simpleSupport(name));
	}

	/**
	 * Construct an empty <code>DefaultEditableProperties</code> instance with
	 * the name and the multiple language support.
	 * 
	 * @param name the name of the property container
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	public DefaultEditableProperties(String name, MultiLanguageSupport support) {
		propertiesName = requireNonNull(name, NULL_NAME_EXCEPTION);
		multiLangSupport = requireNonNull(support, NULL_LANGUAGE_SUPPORT_EXCEPTION);
		properties = new ArrayList<EditableProperty>();
		listeners = new ArrayList<EditablePropertiesListener>();
	}

	@Override
	public String getName() {
		return propertiesName;
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
	public int size() {
		return properties.size();
	}

	@Override
	public EditableProperty getProperty(int index) {
		return properties.get(index);
	}

	@Override
	public EditableProperty getProperty(String name) {
		if(name != null) {
			for(EditableProperty property : properties) {
				if(ObjectUtilities.equals(name, property.getName())) {
					return property;
				}
			}
		}
		return null;
	}

	@Override
	public void addEditableProperty(EditableProperty property) {
		if(!properties.contains(property)) {
			properties.add(property);
			notifyEditablePrpertiesListeners(EditableProperties.KEY_PROPERTY_ADDED);
		}
	}

	@Override
	public void removeEditableProperty(EditableProperty property) {
		if(properties.contains(property)) {
			properties.remove(property);
			notifyEditablePrpertiesListeners(EditableProperties.KEY_PROPERTY_REMOVED);
		}
	}

	@Override
	public void addEditablePropertiesListener(EditablePropertiesListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeEditablePropertiesListener(EditablePropertiesListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify all registered properties changed listeners.
	 * 
	 * @param type the type of the event
	 */
	private void notifyEditablePrpertiesListeners(String type) {
		for(EditablePropertiesListener listener : listeners) {
			listener.propertiesChanged(type, this);
		}
	}
}
