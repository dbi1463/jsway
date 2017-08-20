/* NumberPropertyEditor.java created on 2011/10/13
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

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_KP_DOWN;
import static java.awt.event.KeyEvent.VK_KP_UP;
import static java.awt.event.KeyEvent.VK_UP;
import static javax.swing.JFormattedTextField.COMMIT_OR_REVERT;
import static tw.funymph.jsway.property.EditableProperty.KEY_VALUE;
import static tw.funymph.jsway.property.NumberProperty.*;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatterFactory;

import tw.funymph.jsway.property.*;

/**
 * A GUI editor to edit the value of a {@link NumberProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class NumberPropertyEditor<T extends Comparable<T>> extends JPanel implements PropertyEditor, KeyListener, MouseWheelListener, ChangeListener, EditablePropertyListener {

	private static final long serialVersionUID = 7931415521443179682L;

	private JSpinner valueEditor;
	private JComboBox<String> unitSelector;
	private NumberProperty<T> numberProperty;

	/**
	 * Construct a <code>NumberPropertyEditor</code> instance as a factory.
	 */
	NumberPropertyEditor() {}

	/**
	 * Construct a <code>NumberPropertyEditor</code> instance with the
	 * specified property.
	 * 
	 * @param property the property to be edited
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public NumberPropertyEditor(NumberProperty<T> property) {
		valueEditor = new JSpinner(new SpinnerNumberModel(property.getNumber(), property.getMinimum(), property.getMaximum(), property.getStep()));
		numberProperty = property;
		numberProperty.addEditablePropertyListener(this);

		setLayout(new BorderLayout());
		add(valueEditor, CENTER);
		valueEditor.getModel().addChangeListener(this);
		getValueTextEditor().addKeyListener(this);
		getValueTextEditor().addMouseWheelListener(this);
		getValueTextEditor().setFocusLostBehavior(COMMIT_OR_REVERT);
		if(numberProperty.getAvailableUnits().size() > 1) {
			unitSelector = new JComboBox(new UnitSelectionModel(numberProperty));
			add(unitSelector, EAST);
		}
		else {
			getValueTextEditor().setFormatterFactory(new DefaultFormatterFactory(new NumberWithUnitFormatter(numberProperty.getUnit())));
		}
		setEnabled(numberProperty.isUIEditable());
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		valueEditor.setEnabled(enabled);
		if(unitSelector != null) {
			unitSelector.setEnabled(enabled);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case VK_UP:
		case VK_KP_UP:
			setPreviousValue();
			break;
		case VK_DOWN:
		case VK_KP_DOWN:
			setNextValue();
			break;
		}
		valueEditor.requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(numberProperty.getNumber() != ((SpinnerNumberModel)valueEditor.getModel()).getNumber()) {
			numberProperty.setValue(valueEditor.getValue());
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void propertyChanged(EditablePropertyEvent event) {
		setEnabled(numberProperty.isUIEditable());
		if(KEY_VALUE.equals(event.getKey())) {
			valueEditor.setValue(event.getCurrentValue());
		}
		if(KEY_AVAILABLE_UNITS.equals(event.getKey())) {
			unitSelector.setModel(new UnitSelectionModel(numberProperty));
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getWheelRotation() > 0) {
			setNextValue();
		}
		else {
			setPreviousValue();
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof NumberProperty) {
			return new NumberPropertyEditor((NumberProperty)property);
		}
		return null;
	}

	@Override
	public void detach() {
		numberProperty.removeEditablePropertyListener(this);
	}

	/**
	 * Get the text editor from the spinner.
	 * 
	 * @return the text editor
	 */
	private JFormattedTextField getValueTextEditor() {
		return ((JSpinner.NumberEditor)valueEditor.getEditor()).getTextField();
	}

	/**
	 * Set the current value to the next value.
	 */
	private void setNextValue() {
		if(valueEditor.getModel().getNextValue() != null) {
			numberProperty.setValue(valueEditor.getModel().getNextValue());
		}
	}

	/**
	 * Set the current value to the previous value.
	 */
	private void setPreviousValue() {
		if(valueEditor.getModel().getPreviousValue() != null) {
			numberProperty.setValue(valueEditor.getModel().getPreviousValue());
		}
	}
}
