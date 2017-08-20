/* DefaultPropertyEditors.java created on 2012/10/13
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

import java.util.Hashtable;

import javax.swing.JComponent;

import tw.funymph.jsway.property.BooleanProperty;
import tw.funymph.jsway.property.ChoicesProperty;
import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.property.DateProperty;
import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.IPv4AddressProperty;
import tw.funymph.jsway.property.MACAddressProperty;
import tw.funymph.jsway.property.NumberProperty;
import tw.funymph.jsway.property.TextProperty;

/**
 * The default collection of editor factories to create property editors
 * for most property types provided in the tw.jsway.property package.
 *  
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class DefaultPropertyEditorFactory implements PropertyEditorFactory {

	private Hashtable<Class<?>, PropertyEditor> factories;

	/**
	 * Create the default property editor factory.
	 * 
	 * @return the default property editor factory
	 */
	@SuppressWarnings("rawtypes")
	public static PropertyEditorFactory createDefaultPropertyEditorFactory() {
		PropertyEditorFactory factory = new DefaultPropertyEditorFactory();
		factory.registerEditor(ColorProperty.class, new ColorButton());
		factory.registerEditor(BooleanProperty.class, new OnOffSwitch());
		factory.registerEditor(DateProperty.class, new DatePropertyEditor());
		factory.registerEditor(TextProperty.class, new TextPropertyEditor());
		factory.registerEditor(NumberProperty.class, new NumberPropertyEditor());
		factory.registerEditor(IPv4AddressProperty.class, new IPv4AddressPropertyEditor());
		factory.registerEditor(MACAddressProperty.class, new MACAddressPropertyEditor());
		factory.registerEditor(ChoicesProperty.class, new ChoicesPropertyEditor());
		return factory;
	}

	/**
	 * Disable creation by calling the constructor. Please use {@link #createDefaultPropertyEditorFactory()}
	 * instead of.
	 */
	private DefaultPropertyEditorFactory() {
		factories = new Hashtable<Class<?>, PropertyEditor>();
	}

	/**
	 * Register the new editor factory for the specified property type. This method
	 * can be used to add new type or replace the existing type.
	 * 
	 * @param propertyType the new property type
	 * @param editor the new editor factory
	 */
	public void registerEditor(Class<?> propertyType, PropertyEditor editor) {
		factories.put(propertyType, editor);
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		JComponent editor = null;
		if(factories.containsKey(property.getPropertyClass())) {
			editor = factories.get(property.getPropertyClass()).getEditor(property);
		}
		return editor;
	}
}
