/* EditablePropertyListener.java created on 2011/9/29
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

/**
 * A class can implement this interface and register as a listener to
 * receive the property changed events.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface EditablePropertyListener {

	/**
	 * Invoke when the property has been changed. A change event contains a pair of key
	 * and value. The smallest set of keys are defined in {@link EditableProperty}:
	 * <ul>
	 * <li>{@link EditableProperty#KEY_NAME KEY_NAME}: means the value is the new property name</li>
	 * <li>{@link EditableProperty#KEY_VALUE KEY_VALUE}: means the value is the new property value</li>
	 * <li>{@link EditableProperty#KEY_UI_EDITABLE KEY_UI_EDITABLE}: means the value is whether the property can be edited by editor UI</li>
	 * </ul>
	 * Some property may define additional keys for other purposes.
	 * 
	 * @param event the property changed event
	 */
	void propertyChanged(EditablePropertyEvent event);
}
