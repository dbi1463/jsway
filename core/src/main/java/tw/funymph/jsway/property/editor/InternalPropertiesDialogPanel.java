/* InternalPropertiesDialogPanel.java created on 2012/10/13
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

import static javax.swing.BorderFactory.createCompoundBorder;
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BorderFactory.createTitledBorder;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.EditableProperties;

/**
 * The internal panel that is used by {@link PropertiesDialogPanel} to display
 * a group of editors for a set of editable properties. This class is designed
 * for internal use only (with package visibility).
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
class InternalPropertiesDialogPanel extends JPanel implements SelfDetachable, MultiLanguageSupportListener, PropertyEditorSizeChangeListener {

	private static final long serialVersionUID = -2920717060129168623L;

	private static final String NULL_PROPERTIES_EXCEPTION = "Cannot create a dialog panel for a null properties!";
	private static final String NULL_SIZE_MODEL_EXCEPTION = "Cannot create a dialog panel with a null size model!";

	/** The space between the outside border and its content. */
	private static final int SPACE = 5;

	private PropertyEditBar[] bars;
	private EditableProperties editingProperties;
	private PropertyEditorSizeModel editorSizes;

	/**
	 * Construct a <code>InternalPropertiesDialogPanel</code> instance with
	 * the properties to be edited, the factory to create editors, and sizes
	 * of the editors.
	 * 
	 * @param properties the properties to be edited
	 * @param factory the factory to create editors
	 * @param sizes the sizes of the editors
	 */
	InternalPropertiesDialogPanel(EditableProperties properties, PropertyEditorFactory factory, PropertyEditorSizeModel sizes) {
		editingProperties = requireNonNull(properties, NULL_PROPERTIES_EXCEPTION);
		editorSizes = requireNonNull(sizes, NULL_SIZE_MODEL_EXCEPTION);
		editorSizes.addPropertyEditorSizeChangeListener(this);
		editingProperties.addMultiLanguageSupportListener(this);
		bars = new PropertyEditBar[editingProperties.size()];
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		for(int index = 0; index < bars.length; index++) {
			bars[index] = new PropertyEditBar(editingProperties.getProperty(index), factory, editorSizes.getEditorWidth(index));
			add(bars[index]);
		}
		createBorder(editingProperties.getDisplayText());
	}

	/**
	 * Set the size of the index-th editor
	 * 
	 * @param index the index of the editor
	 * @param width the new width
	 */
	public void setEditorWidth(int index, int width) {
		if(index < editingProperties.size()) {
			editorSizes.setEditorWidth(index, width);
		}
	}

	/**
	 * Set the size of the editor specified by the property name
	 * 
	 * @param propertyName the property name
	 * @param width the new width
	 */
	public void setEditorWidth(String propertyName, int width) {
		int index = 0;
		boolean keepFinding = true;
		for(; keepFinding && index < editingProperties.size(); index++) {
			keepFinding = !editingProperties.getProperty(index).getName().equals(propertyName); 
		}
		setEditorWidth(index, width);
	}

	/**
	 * Set the new property editor size model. This method will set the width
	 * for every editor with the value in the new model.
	 * 
	 * @param sizes the new property editor size model
	 */
	public void setEditorSizeModel(PropertyEditorSizeModel sizes) {
		requireNonNull(sizes, NULL_SIZE_MODEL_EXCEPTION);
		editorSizes.removePropertyEditorSizeChangeListener(this);
		editorSizes = sizes;
		editorSizes.addPropertyEditorSizeChangeListener(this);
		for(int index = 0; index < bars.length; index++) {
			bars[index].setEditorWidth(editorSizes.getEditorWidth(index));
		}
	}

	/**
	 * Get the current property editor size model.
	 * 
	 * @return the current property editor size model
	 */
	public PropertyEditorSizeModel getEditorSizeModel() {
		return editorSizes;
	}

	/**
	 * Get the current editing properties.
	 * 
	 * @return the current editing properties
	 */
	public EditableProperties getEditableProperties() {
		return editingProperties;
	}

	@Override
	public void detach() {
		if(bars != null) {
			for(PropertyEditBar bar : bars) {
				bar.detach();
			}
		}
		editorSizes.removePropertyEditorSizeChangeListener(this);
		editingProperties.removeMultiLanguageSupportListener(this);
	}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		createBorder(source.getDisplayText());
	}

	@Override
	public void sizeChanged(int editorIndex, int width) {
		if(editorIndex >= 0 && editorIndex < bars.length) {
			bars[editorIndex].setEditorWidth(width);
		}
	}

	/**
	 * Create the titled border for the panel.
	 * 
	 * @param title the title to create the border
	 */
	private void createBorder(String title) {
		Border outsideBorder = createTitledBorder(title);
		Border insideBorder = createEmptyBorder(SPACE, SPACE, SPACE, SPACE);
		Border border = createCompoundBorder(outsideBorder, insideBorder);
		setBorder(border);
	}
}
