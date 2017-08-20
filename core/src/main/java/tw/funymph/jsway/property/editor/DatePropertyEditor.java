/* DatePropertyEditorFactory.java created on Apr 30, 2012
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
import javax.swing.JSpinner;

import tw.funymph.jsway.property.DateProperty;
import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * A factory that creates editor for {@link DateProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public final class DatePropertyEditor extends JSpinner implements EditablePropertyListener, PropertyEditor {

	private static final long serialVersionUID = 7359999873325196447L;

	private DateProperty dateProperty;

	/**
	 * Construct a <code>DatePropertyEditor</code> instance as a factory.
	 */
	DatePropertyEditor() {}

	/**
	 * Construct a <code>DatePropertyEditor</code> instance with the
	 * specified property.
	 * 
	 * @param property the property to be edited
	 * @since 1.1
	 */
	public DatePropertyEditor(DateProperty property) {
		super(new DatePropertySpinnerModel(property));
		dateProperty = property;
		dateProperty.addEditablePropertyListener(this);
		setEnabled(property.isUIEditable());
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof DateProperty) {
			return new DatePropertyEditor((DateProperty)property);
		}
		return null;
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		setEnabled(event.getSource().isUIEditable());
	}

	@Override
	public void detach() {
		dateProperty.removeEditablePropertyListener(this);
	}
}
