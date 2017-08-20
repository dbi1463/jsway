/* EditableProperty.java created on 2011/9/29
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

/**
 * A class can implement the interface to offer a named property
 * that can be edited by a paired editors.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface EditableProperty extends NamedObject, MultiLanguageSupport {

	/** The type of the event that is notified when the name is updated. */
	public static final String KEY_NAME = "EditableProperty.name";

	/** The type of the event that is notified when the value is updated. */
	public static final String KEY_VALUE = "EditableProperty.value";

	/** The type of the event that is notified when the editable status is updated. */
	public static final String KEY_UI_EDITABLE = "EditableProperty.editable";

	/** The type of the event that is notified when the display text is updated. */
	public static final String KEY_DISPLAY_TEXT = "EditableProperty.displayText";

	static final String NULL_NAME_EXCEPTION = "Cannot set a null name!";
	static final String NULL_VALUE_EXCEPTION = "Cannot set a null value!";
	static final String NULL_LANGUAGE_SUPPORT_EXCEPTION = "Cannot set a null multiple language support!";

	/**
	 * Get the value of the property.
	 * 
	 * @return the value of the property
	 */
	Object getValue();

	/**
	 * Get the class of the property. In most cases, the method is equivalent to
	 * {@link Object#getClass()} method. Only the case that a class wrapped another
	 * property to offer additional functionalities but still want to be the same
	 * property class of the wrapped property.
	 * 
	 * @return the class of the property
	 * @since 1.1
	 */
	Class<?> getPropertyClass();

	/**
	 * Get whether the property can be edited by the editor UI.
	 * 
	 * @return true if the property can be edited by the editor UI
	 */
	boolean isUIEditable();

	/**
	 * Set the value of the property. This will fire an editable property
	 * changed event to all registered listeners.
	 * 
	 * @param value the value of the property
	 * @throws NullPointerException if the value is null
	 */
	void setValue(Object value);

	/**
	 * Set whether the property can be edited by the editor UI. A new value
	 * will fire an editable property changed event to all registered listeners.
	 * 
	 * @param editable whether the property can be edited by the editor UI
	 */
	void setUIEditable(boolean editable);

	/**
	 * Add the specified listener into the list that will be notified when the property
	 * has any change.
	 * 
	 * @param listener the listener to be added
	 */
	void addEditablePropertyListener(EditablePropertyListener listener);

	/**
	 * Remove the specified listener from the notification list.
	 * 
	 * @param listener the listener to be removed
	 */
	void removeEditablePropertyListener(EditablePropertyListener listener);
}
