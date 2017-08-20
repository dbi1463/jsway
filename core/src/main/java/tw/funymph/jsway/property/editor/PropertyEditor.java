/* PropertyEditor.java created on 2011/10/13
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
package tw.funymph.jsway.property.editor;

import javax.swing.JComponent;

import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.EditableProperty;

/**
 * A property editor can implements this interface to handle how to create
 * an editor for editing the property.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface PropertyEditor extends SelfDetachable {

	static final String NULL_PROPERTY_EXCEPTION = "Cannot use a null property in an editor";

	/**
	 * Get an editor for the property. Note that the editor must bind the current
	 * property value for editing, and set the updated value back to the property
	 * automatically.
	 * 
	 * @param property the property to be edited
	 * @return the editor
	 */
	JComponent getEditor(EditableProperty property);
}
