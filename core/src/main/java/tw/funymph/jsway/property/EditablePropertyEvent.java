/* EditablePropertyEvent.java created on Jun 6, 2012
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

/**
 * The class that contains the information about what changed in the source property.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class EditablePropertyEvent {

	private String eventKey;
	private Object oldValue;
	private Object currentValue;
	private EditableProperty eventSource;

	/**
	 * Construct a <code>EditablePropertyEvent</code> instance.
	 * 
	 * @param source the event source
	 * @param key the event type
	 * @param current the current value
	 * @param old the old value
	 */
	public EditablePropertyEvent(EditableProperty source, String key, Object current, Object old) {
		eventKey = key;
		oldValue = old;
		currentValue = current;
		eventSource = source;
	}

	/**
	 * Get the event source.
	 * 
	 * @return the event source
	 */
	public EditableProperty getSource() {
		return eventSource;
	}

	/**
	 * Get the type of the updated resource.
	 * 
	 * @return the type of the updated resource
	 */
	public String getKey() {
		return eventKey;
	}

	/**
	 * Get the current value of the specified resource type.
	 * 
	 * @return the current value.
	 */
	public Object getCurrentValue() {
		return currentValue;
	}

	/**
	 * Get the old value of the specified resource type.
	 * 
	 * @return the old value.
	 */
	public Object getOldValue() {
		return oldValue;
	}
}
