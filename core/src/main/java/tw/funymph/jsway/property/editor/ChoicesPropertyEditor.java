/* ChoicesPropertyEditor.java created on Jun 5, 2012
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

import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import javax.swing.JComboBox;
import javax.swing.JComponent;

import tw.funymph.jsway.property.ChoicesProperty;
import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * A GUI editor to edit the value of a {@link ChoicesProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class ChoicesPropertyEditor extends JComboBox implements EditablePropertyListener, PropertyEditor {

	private static final long serialVersionUID = 3265409668248168701L;

	private ChoicesProperty choicesProperty;
	private ChoiceSelectionModel selectionModel;

	/**
	 * Construct a <code>ChoicesPropertyEditor</code> instance as a factory.
	 */
	ChoicesPropertyEditor() {}

	/**
	 * Construct a <code>ChoicesPropertyEditor</code> instance with the
	 * specified property.
	 * 
	 * @param property the property to be edited
	 */
	public ChoicesPropertyEditor(ChoicesProperty property) {
		choicesProperty = requireNonNull(property, NULL_PROPERTY_EXCEPTION);
		setModel(selectionModel = new ChoiceSelectionModel(property));
		setEnabled(property.isUIEditable());
		property.addEditablePropertyListener(this);
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof ChoicesProperty) {
			return new ChoicesPropertyEditor((ChoicesProperty)property);
		}
		return null;
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		setEnabled(event.getSource().isUIEditable());
	}

	@Override
	public void detach() {
		selectionModel.detach();
		choicesProperty.removeEditablePropertyListener(this);
	}
}
