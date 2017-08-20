/* EditablePropertiesListener.java created on 2011/9/29
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

/** A class can implement this interface and register as a listener
 * to receive the property added or removed events.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface EditablePropertiesListener {

	/**
	 * Invoke when the properties has been changed (a property is added or removed
	 * that is specified by the type). The types are defined in {@link EditableProperties}:
	 * <ul>
	 * <li>{@link EditableProperties#KEY_PROPERTY_ADDED KEY_PROPERTY_ADDED}: means a property is added into the properties.</li>
	 * <li>{@link EditableProperties#KEY_PROPERTY_REMOVED KEY_PROPERTY_REMOVED}: means a property is removed from the properties.</li>
	 * </ul>
	 * 
	 * @param type the type of the event
	 * @param properties the new properties
	 */
	void propertiesChanged(String type, EditableProperties properties);
}
