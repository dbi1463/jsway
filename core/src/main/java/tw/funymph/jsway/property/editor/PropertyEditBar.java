/* PropertyEditBar.java created on 2012/10/13
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

import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.BoxLayout.LINE_AXIS;
import static tw.funymph.jsway.property.editor.PropertyEditor.NULL_PROPERTY_EXCEPTION;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.EditableProperty;

/**
 * The smallest unit of property editing area. The class is designed for internal
 * use only (with package visibility).
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
class PropertyEditBar extends JPanel implements SelfDetachable, MultiLanguageSupportListener {

	private static final long serialVersionUID = -7888491901424514194L;

	private static final int DEFAULT_BAR_HEIGHT = 30;

	private EditableProperty editingProperty;

	private JLabel nameLabel;
	private JComponent editor;

	/**
	 * Construct a <code>PropertyEditBar</code> instance for the specified property
	 * with the editor factory and the width of the created editor.
	 * 
	 * @param property the property to be edited
	 * @param factory the factory to create an editor
	 * @param editorWidth the width of the created editor
	 */
	PropertyEditBar(EditableProperty property, PropertyEditorFactory factory, int editorWidth) {
		editingProperty = requireNonNull(property, NULL_PROPERTY_EXCEPTION);
		editingProperty.addMultiLanguageSupportListener(this);
		nameLabel = new JLabel(editingProperty.getDisplayText());
		editor = factory.getEditor(property);
		setEditorWidth(editorWidth);
		setLayout(new BoxLayout(this, LINE_AXIS));
		add(nameLabel);
		add(createHorizontalGlue());
		add(editor);
		setBorder(createEmptyBorder(5, 5, 0, 5));
	}

	/**
	 * Set the width of the editor.
	 * 
	 * @param width the width of the editor
	 */
	public void setEditorWidth(int width) {
		Dimension size = new Dimension(width, DEFAULT_BAR_HEIGHT);
		editor.setPreferredSize(size);
		editor.setMaximumSize(size);
	}

	@Override
	public void detach() {
		((PropertyEditor)editor).detach();
		editingProperty.removeMultiLanguageSupportListener(this);
	}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		nameLabel.setText(source.getDisplayText());
	}

	public Dimension getMinimumSize() {
		return new Dimension(super.getMinimumSize().width, DEFAULT_BAR_HEIGHT);
	}

	public Dimension getPreferredSize() {
		return new Dimension(super.getPreferredSize().width, DEFAULT_BAR_HEIGHT);
	}

	public Dimension getMaximumSize() {
		return new Dimension(super.getMaximumSize().width, DEFAULT_BAR_HEIGHT);
	}
}
