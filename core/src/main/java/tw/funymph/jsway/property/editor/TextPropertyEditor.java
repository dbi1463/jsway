/* TextPropertyEditor.java created on 2011/10/13
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

import static tw.funymph.jsway.property.EditableProperty.KEY_UI_EDITABLE;
import static tw.funymph.jsway.property.EditableProperty.KEY_VALUE;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;
import tw.funymph.jsway.property.TextProperty;

/**
 * A GUI editor to edit the value of a {@link TextProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class TextPropertyEditor extends JTextField implements PropertyEditor, KeyListener, EditablePropertyListener {

	private static final long serialVersionUID = -9189853706155371930L;

	private TextProperty editingProperty;

	/**
	 * Construct <code>TextPropertyEditor</code> instance as a factory.
	 */
	TextPropertyEditor() {}

	/**
	 * Construct a <code>TextPropertyEditor</code> instance with the
	 * specified property.
	 * 
	 * @param property the property to be edited
	 */
	public TextPropertyEditor(TextProperty property) {
		setProperty(property);
		addKeyListener(this);
	}

	/**
	 * Set the new property to be edited.
	 * 
	 * @param property the property to be edited
	 */
	public void setProperty(TextProperty property) {
		if(editingProperty != null) {
			editingProperty.removeEditablePropertyListener(this);
		}
		editingProperty = requireNonNull(property, NULL_PROPERTY_EXCEPTION);
		editingProperty.addEditablePropertyListener(this);
		setEnabled(editingProperty.isUIEditable());
		setText(editingProperty.getValue().toString());
	}

	@Override
	public void detach() {
		editingProperty.removeEditablePropertyListener(this);
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof TextProperty) {
			return new TextPropertyEditor((TextProperty)property);
		}
		return null;
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		if(KEY_UI_EDITABLE.equals(event.getKey())) {
			setEnabled(editingProperty.isUIEditable());
		}
		else if(KEY_VALUE.equals(event.getKey())) {
			setText(editingProperty.getValue().toString());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		editingProperty.setValue(getText());
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}
}
