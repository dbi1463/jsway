/* PropertiesDialogPanel.java created on 2012/10/13
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
import static javax.swing.BorderFactory.createEmptyBorder;
import static javax.swing.BoxLayout.PAGE_AXIS;
import static tw.funymph.jsway.property.editor.DefaultPropertyEditorFactory.createDefaultPropertyEditorFactory;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.DefaultEditableProperties;
import tw.funymph.jsway.property.EditableProperties;

/**
 * A GUI that displays editors for properties in multiple groups. In each group,
 * the editors are surrounded a titled border and arranged with the same widths.
 * The width of each concrete editor can be specified by {@link #setEditorWidth(int, int, int)}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class PropertiesDialogPanel extends JPanel implements SelfDetachable {

	private static final long serialVersionUID = 5688976150043090541L;

	private static final int BORDER_SIZE = 5;

	private static final String ILLEGAL_ARGUMENT_SIZE = "The sizes of the two arrays are not the same!";

	private JPanel contentPane;
	private JScrollPane scrollPane;

	private PropertyEditorFactory editorFactory;
	private InternalPropertiesDialogPanel[] internalPanels;

	/**
	 * Construct an empty <code>PropertiesDialogPanel</code> instance.
	 */
	public PropertiesDialogPanel() {
		this(new DefaultEditableProperties(""));
	}

	/**
	 * Construct a <code>PropertiesDialogPanel</code> instance to show editors
	 * for the specified editable properties.
	 * 
	 * @param properties the properties to be edited
	 */
	public PropertiesDialogPanel(EditableProperties properties) {
		this(new EditableProperties[] { properties });
	}

	/**
	 * Construct a <code>PropertiesDialogPanel</code> instance to show editors
	 * for the specified groups of editable properties
	 * 
	 * @param properties the properties to be edited
	 */
	public PropertiesDialogPanel(EditableProperties[] properties) {
		editorFactory = createDefaultPropertyEditorFactory();
		contentPane = new JPanel();
		scrollPane = new JScrollPane(contentPane);
		scrollPane.setBorder(null);
		contentPane.setLayout(new BoxLayout(contentPane, PAGE_AXIS));
		contentPane.setBorder(createEmptyBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE));
		setProperties(properties);
		setLayout(new BorderLayout());
		add(scrollPane, CENTER);
	}

	/**
	 * Set the groups of the properties to be edited in the panel with the
	 * default size models.
	 * 
	 * @param properties the groups of the properties to be edited
	 */
	public void setProperties(List<EditableProperties> properties) {
		setProperties(properties.toArray(new EditableProperties[0]));
	}

	/**
	 * Set the groups of the properties to be edited in the panel with the
	 * size models for the editors.
	 * 
	 * @param properties the groups of the properties to be edited
	 * @param sizes the size models
	 */
	public void setProperties(List<EditableProperties> properties, List<PropertyEditorSizeModel> sizes) {
		setProperties(properties.toArray(new EditableProperties[0]), sizes.toArray(new PropertyEditorSizeModel[0]));
	}

	/**
	 * Set the groups of the properties to be edited in the panel with the
	 * default size models.
	 * 
	 * @param properties the groups of the properties to be edited
	 */
	public void setProperties(EditableProperties[] properties) {
		PropertyEditorSizeModel[] sizes = new PropertyEditorSizeModel[properties.length];
		for(int index = 0; index < properties.length; index++) {
			sizes[index] = new DefaultPropertyEditorSizeModel();
		}
		setProperties(properties, sizes);
	}

	/**
	 * Set the groups of the properties to be edited in the panel with the
	 * size models for the editors.
	 * 
	 * @param properties the groups of the properties to be edited
	 * @param sizes the size models
	 */
	public void setProperties(EditableProperties[] properties, PropertyEditorSizeModel[] sizes) {
		if(properties.length != sizes.length) {
			throw new RuntimeException(ILLEGAL_ARGUMENT_SIZE);
		}
		reset();
		if(properties != null) {
			int size = properties.length;
			internalPanels = new InternalPropertiesDialogPanel[size];
			for(int index = 0; index < size; index++) {
				internalPanels[index] = new InternalPropertiesDialogPanel(properties[index], editorFactory, sizes[index]);
				contentPane.add(internalPanels[index]);
				contentPane.add(Box.createVerticalStrut(15));
			}
		}
		contentPane.add(Box.createGlue());
		contentPane.add(Box.createVerticalStrut(1));
		contentPane.updateUI();
	}

	/**
	 * Set the new width of the editor that is specified by the property group
	 * index and the editor index.
	 * 
	 * @param propertyGroupIndex the index of the property group
	 * @param editorIndex the index of the editor
	 * @param width the new width
	 */
	public void setEditorWidth(int propertyGroupIndex, int editorIndex, int width) {
		if(internalPanels != null && propertyGroupIndex < internalPanels.length) {
			internalPanels[propertyGroupIndex].setEditorWidth(editorIndex, width);
		}
	}

	/**
	 * Set the new width of the editor that is specified by the property group
	 * name and the property name.
	 * 
	 * @param propertyGroupName the name of the property group
	 * @param propertyName the name of the property
	 * @param width the new width
	 */
	public void setEditorWidth(String propertyGroupName, String propertyName, int width) {
		if(internalPanels != null) {
			int index = 0;
			boolean keepFinding = true;
			for(; keepFinding && index < internalPanels.length; index++) {
				if(internalPanels[index].getEditableProperties().getName().equals(propertyGroupName)) {
					keepFinding = false;
					internalPanels[index].setEditorWidth(propertyName, width);
				}
			}
		}
	}

	/**
	 * Set the new property editor size model for the specified property group.
	 * 
	 * @param propertyGroupIndex the index of the property group
	 * @param sizes the new size model
	 */
	public void setEditorSizeModel(int propertyGroupIndex, PropertyEditorSizeModel sizes) {
		internalPanels[propertyGroupIndex].setEditorSizeModel(sizes);
	}

	/**
	 * Get the property editor size model for the specified property group.
	 * 
	 * @param propertyGroupIndex the index of the property group
	 * @return the property editor size model
	 */
	public PropertyEditorSizeModel getEditorSizeModel(int propertyGroupIndex) {
		return internalPanels[propertyGroupIndex].getEditorSizeModel();
	}

	/**
	 * Register the new editor for the new property type.
	 * 
	 * @param type the new property type
	 * @param editor the new editor
	 */
	public void registerEditor(Class<?> type, PropertyEditor editor) {
		editorFactory.registerEditor(type, editor);
	}

	/**
	 * Clear all editors and detach all dependencies between editors and properties.
	 */
	public void reset() {
		detach();
		contentPane.removeAll();
		internalPanels = null;
	}

	@Override
	public void detach() {
		if(internalPanels != null) {
			for(InternalPropertiesDialogPanel internalPanel : internalPanels) {
				internalPanel.detach();
			}
		}
	}

	@Override
	public void removeAll() {
		detach();
		super.removeAll();
	}
}
