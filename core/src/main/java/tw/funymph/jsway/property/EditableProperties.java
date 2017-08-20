/* EditableProperties.java created on 2011/9/29
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

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.NamedObject;

/** A class can implement this interface to manage a suite of editable properties.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface EditableProperties extends NamedObject, MultiLanguageSupport {

	/** The type of the event that is notified when the display name is updated. */
	public static final String KEY_DISPLAY_NAME = "EditableProperties.displayName";

	/** The type of the event that is notified when a property is added into the container. */
	public static final String KEY_PROPERTY_ADDED = "EditableProperties.propertyAdded";

	/** The type of the event that is notified when a property is removed from the container. */
	public static final String KEY_PROPERTY_REMOVED = "EditableProperties.propertyRemoved";

	/**
	 * Get the size of the editable properties in the instance.
	 * 
	 * @return the size of the editable properties
	 */
	int size();

	/**
	 * Get the edit property at the specified index.
	 * 
	 * @param index the specified index
	 * @return the property
	 * @throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
	 */
	EditableProperty getProperty(int index);

	/**
	 * Get the edit property with the specified name.
	 * 
	 * @param name the specified name
	 * @return the property; null if the specified property name does not exist
	 */
	EditableProperty getProperty(String name);

	/**
	 * Add the specified property into the container
	 * 
	 * @param property the property to be added
	 */
	void addEditableProperty(EditableProperty property);

	/**
	 * Remove the specified property from the container
	 * 
	 * @param property the property to be removed
	 */
	void removeEditableProperty(EditableProperty property);

	/**
	 * Add the specified listener into the list that will be notified when the properties
	 * has any change (add a property or remove a property).
	 * 
	 * @param listener the listener to be added
	 */
	void addEditablePropertiesListener(EditablePropertiesListener listener);

	/**
	 * Remove the specified listener from the notification list.
	 * 
	 * @param listener the listener to be removed
	 */
	void removeEditablePropertiesListener(EditablePropertiesListener listener);
}
