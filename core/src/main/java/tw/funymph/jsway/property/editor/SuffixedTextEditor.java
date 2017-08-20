/* SuffixedTextEditor.java created on May 3, 2012
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

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.Color.white;
import static javax.swing.JFormattedTextField.COMMIT_OR_REVERT;
import static javax.swing.SwingConstants.RIGHT;
import static tw.funymph.jsway.property.EditableProperty.KEY_VALUE;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;
import tw.funymph.jsway.property.codecs.EditablePropertyCodec;

/**
 * A GUI that displays an editor with a label to show the suffix, e.g., an unit.
 * This class is designed for internal use (e.g., {@link IPv4AddressPropertyEdiot}
 * or {@link MACAddressPropertyEditor}).
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class SuffixedTextEditor extends JPanel implements SelfDetachable, PropertyChangeListener, EditablePropertyListener {

	private static final long serialVersionUID = 3625701438806549110L;

	private static final String PROPERTY_EVENT_KEY_VALUE = "value";

	private EditableProperty editingProperty;
	private EditablePropertyCodec propertyCodec;

	private String suffixText;
	private JLabel suffixLabel;
	private JFormattedTextField editor;
	private AbstractFormatter textFormatter;
	private SelectAllTextMouseListener selectAllListener;

	/**
	 * Construct a <code>SuffixedTextEditor</code> instance for the property
	 * with the suffix.
	 * 
	 * @param property the property to be edited
	 * @param suffix the suffix to be displayed
	 */
	SuffixedTextEditor(EditableProperty property, String suffix) {
		this(property, null, null, suffix);
	}

	/**
	 * Construct a <code>SuffixedTextEditor</code> instance for the property
	 * with the text formatter and the suffix.
	 * 
	 * @param property the property to be edited
	 * @param formatter the text formatter
	 * @param suffix the suffix to be displayed
	 */
	SuffixedTextEditor(EditableProperty property, AbstractFormatter formatter, String suffix) {
		this(property, null, formatter, suffix);
	}

	/**
	 * Construct a <code>SuffixedTextEditor</code> instance for the property
	 * with the property codec and the suffix.
	 * 
	 * @param property the property to be edited
	 * @param formatter the property codec
	 * @param suffix the suffix to be displayed
	 */
	SuffixedTextEditor(EditableProperty property, EditablePropertyCodec codec, String suffix) {
		this(property, codec, null, suffix);
	}

	/**
	 * Construct a <code>SuffixedTextEditor</code> instance for the property
	 * with the property codec, the text formatter, and the suffix.
	 * 
	 * @param property the property to be edited
	 * @param codec the property codec
	 * @param formatter the text formatter
	 * @param suffix the suffix to be displayed
	 */
	SuffixedTextEditor(EditableProperty property, EditablePropertyCodec codec, AbstractFormatter formatter, String suffix) {
		propertyCodec = codec;
		suffixText = suffix;
		editingProperty = property;
		textFormatter = formatter;
		initializeComponents();
		registerListeners();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if(editor != null) {
			editor.setEnabled(enabled);
		}
		if(suffixLabel != null) {
			suffixLabel.setEnabled(enabled);
		}
	}

	@Override
	public void detach() {
		unregisterListeners();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		if(KEY_VALUE.equals(event.getKey())) {
			if(propertyCodec != null) {
				editor.setText(propertyCodec.encode(event.getCurrentValue()));
			}
			else {
				editor.setValue(event.getCurrentValue());
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		if(PROPERTY_EVENT_KEY_VALUE.equals(e.getPropertyName())) {
			try {
				editor.commitEdit();
				if(propertyCodec != null) {
					editingProperty.setValue(propertyCodec.decode(editor.getText()));
				}
				else {
					editingProperty.setValue(editor.getValue());
				}
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Initialize all components by the conditions defined in the constructor.
	 */
	private void initializeComponents() {
		editor = new JFormattedTextField(textFormatter);
		editor.setValue((propertyCodec != null)? propertyCodec.encode(editingProperty.getValue()) : editingProperty.getValue());
		editor.setBorder(null);
		editor.setHorizontalAlignment(RIGHT);
		editor.setFocusLostBehavior(COMMIT_OR_REVERT);

		setLayout(new BorderLayout());
		setBackground(white);
		add(editor, CENTER);
		if(suffixText != null) {
			add(suffixLabel = new JLabel(suffixText), EAST);
		}
	}

	/**
	 * Register all useful listeners.
	 */
	private void registerListeners() {
		editor.addPropertyChangeListener(this);
		editingProperty.addEditablePropertyListener(this);
		editor.addMouseListener(selectAllListener = new SelectAllTextMouseListener(editor));
	}

	/**
	 * Unregister all registered listeners.
	 */
	private void unregisterListeners() {
		editor.removePropertyChangeListener(this);
		editor.removeMouseListener(selectAllListener);
		editingProperty.removeEditablePropertyListener(this);
	}
}
