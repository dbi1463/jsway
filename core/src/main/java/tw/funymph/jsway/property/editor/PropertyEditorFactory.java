/* PropertyEditorFactory.java created on 2012/10/13
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
package tw.funymph.jsway.property.editor;

import javax.swing.JComponent;

import tw.funymph.jsway.property.EditableProperty;

/**
 * A class can implements this interface to manage a set of editor factories
 * (which implement {@link PropertyEditor} and create editors for different
 * types of properties.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public interface PropertyEditorFactory {

	/**
	 * Register the new editor factory for the new property type.
	 * 
	 * @param propertyType the new property type
	 * @param editor the new editor factory
	 */
	void registerEditor(Class<?> propertyType, PropertyEditor editor);

	/**
	 * Create an editor for the property.
	 * 
	 * @param property the property to be edited
	 * @return the editor for the property; null if the property is null or no suitable editor for the property
	 */
	JComponent getEditor(EditableProperty property);
}
