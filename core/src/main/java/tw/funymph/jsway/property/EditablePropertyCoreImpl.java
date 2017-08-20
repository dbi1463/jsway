/* EditablePropertyCoreImpl.java created on 2011/10/13
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

import java.util.ArrayList;
import java.util.List;

import tw.funymph.jsway.utils.ObjectUtilities;

import static tw.funymph.jsway.property.EditableProperty.*;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

/**
 * A new property type that can not extends the {@link AbstractEditableProperty} class
 * can implement the {@link EditableProperty} interface and composite this class to reuse
 * the most common implementation.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0 
 */
class EditablePropertyCoreImpl {

	private static final String NULL_PROPERTY_SOURCE_EXCEPTION = "Cannot set a null property source";

	private String propertyName;
	private EditableProperty property;
	private List<EditablePropertyListener> listeners;

	private boolean editable;

	/**
	 * Construct a <code>EditablePropertyCoreImpl</code> instance with the
	 * specified name.
	 * 
	 * @param name the name of the property
	 * @param source the property source
	 * @throws NullPointerException if either the name or the source is null 
	 */
	public EditablePropertyCoreImpl(String name, EditableProperty source) {
		listeners = new ArrayList<EditablePropertyListener>();
		setName(name);
		property = requireNonNull(source, NULL_PROPERTY_SOURCE_EXCEPTION);
		editable = true;
	}

	/**
	 * Get the name of the property.
	 * 
	 * @return the name of the property
	 */
	public String getName() {
		return propertyName;
	}

	/**
	 * Get whether the property can be edited by the editor UI.
	 * 
	 * @return true if the property can be edited by the editor UI
	 */
	public boolean isUIEditable() {
		return editable;
	}

	/**
	 * Set the name of the property. A new name will fire an editable
	 * property changed event to all registered listeners.
	 * 
	 * @param name the name of the property
	 */
	public void setName(String name) {
		requireNonNull(name, NULL_NAME_EXCEPTION);
		if(!ObjectUtilities.equals(name, propertyName)) {
			String oldName = name;
			propertyName = name;
			notifyEditablePropertyListeners(property, KEY_NAME, name, oldName);
		}
	}

	/**
	 * Set whether the property can be edited by the editor UI. A new
	 * value will fire an editable property changed event to all registered
	 * listeners.
	 * 
	 * @param editable whether the property can be edited by the editor UI
	 */
	public void setEnabled(boolean enabled) {
		if(editable != enabled) {
			boolean oldValue = editable;
			editable = enabled;
			notifyEditablePropertyListeners(property, KEY_UI_EDITABLE, editable, oldValue);
		}
	}

	/**
	 * Add the specified listener into the list that will be notified when
	 * the property has any change.
	 * 
	 * @param listener the listener to be added
	 */
	public void addEditablePropertyListener(EditablePropertyListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Remove the specified listener from the notification list.
	 * 
	 * @param listener the listener to be removed
	 */
	public void removeEditablePropertyListener(EditablePropertyListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify all registered listeners that the property has been changed.
	 * 
	 * @param key the key of changed value
	 * @param value the changed value
	 */
	public void notifyEditablePropertyListeners(EditableProperty source, String key, Object value, Object oldValue) {
		EditablePropertyEvent event = new EditablePropertyEvent(source, key, value, oldValue);
		for(EditablePropertyListener listener : listeners) {
			listener.propertyChanged(event);
		}
	}
}
