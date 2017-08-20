/* CategorizedPropertiesDialogPanel.java created on 2012/10/15
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Hashtable;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.CategorizedEditableProperties;
import tw.funymph.jsway.property.Choice;
import tw.funymph.jsway.property.EditableProperties;

/**
 * A GUI that displays the categorized properties like a dialog panel, not like
 * a table in {@link CategorizedPropertiesEditPanel} . Each property editor is
 * aligned on the right side and can have its owned width in the panel. This class
 * is suitable used to design a preferences dialog.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class CategorizedPropertiesDialogPanel extends JPanel implements SelfDetachable, ListSelectionListener {

	private static final long serialVersionUID = 5364436334415120532L;

	private static final String NULL_CATEGORY_EXCEPTION = "Cannot set a null category!";
	private static final String ILLEGAL_SIZE_ARGUMENTS_EXCEPTION = "The size of the two arguments are not matched!";

	private JPanel selectorPanel;
	private JList<Choice> categorySelector;
	private PropertiesDialogPanel propertiesPanel;

	private CategorySelectionModel selectionModel;
	private CategorizedEditableProperties editingCategorizedProperties;
	private Hashtable<String, PropertyEditorSizeModel[]> categorizedSizes;

	/**
	 * Construct an empty categorized properties dialog panel.
	 */
	public CategorizedPropertiesDialogPanel() {
		this(null);
	}

	/**
	 * Construct a <code>CategorizedPropertiesDialogPanel</code> instance with the initial values.
	 * 
	 * @param categorizedProperties the categorized editable properties 
	 */
	public CategorizedPropertiesDialogPanel(CategorizedEditableProperties categorizedProperties) {
		this(categorizedProperties, prepareDefaultSizes(categorizedProperties));
	}

	/**
	 * Construct a <code>CategorizedPropertiesDialogPanel</code> instance with the initial values
	 * and the widths of each editors.
	 * 
	 * @param categorizedProperties the categorized editable properties 
	 */
	public CategorizedPropertiesDialogPanel(CategorizedEditableProperties categorizedProperties, Hashtable<String, PropertyEditorSizeModel[]> sizes) {
		selectorPanel = new JPanel();
		propertiesPanel = new PropertiesDialogPanel();
		selectionModel = new CategorySelectionModel();
		categorySelector = new JList<Choice>(selectionModel);
		categorySelector.setSelectionModel(selectionModel);
		categorySelector.setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout());
		categorySelector.addListSelectionListener(this);
		selectorPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		selectorPanel.setLayout(new BorderLayout());
		selectorPanel.add(categorySelector);
		add(selectorPanel, BorderLayout.WEST);
		add(propertiesPanel, BorderLayout.CENTER);
		setCategorizedEditableProperties(categorizedProperties, sizes);
	}

	/**
	 * Set the width of the category selector (a list on the left side).
	 * 
	 * @param width the new width of the category selector
	 */
	public void setCategorySelectorWidth(int width) {
		categorySelector.setPreferredSize(new Dimension(width, categorySelector.getPreferredSize().height));
	}

	/**
	 * Set the category of the properties to be displayed
	 * 
	 * @param category the category of the properties to be displayed
	 */
	public void setSelectedCategory(String category) {
		requireNonNull(category, NULL_CATEGORY_EXCEPTION);
		selectionModel.setSelectedCategory(category);
	}

	/**
	 * Set the width of the editor that is specified by the category, property group
	 * index, and the editor index.
	 * 
	 * @param category the category that the editor belongs to
	 * @param propertyGroupIndex the index of the property group that the editor belongs to
	 * @param editorIndex the index of the editor in the property group
	 * @param width the new width of the editor
	 */
	public void setEditorWidth(String category, int propertyGroupIndex, int editorIndex, int width) {
		if(categorizedSizes.containsKey(category)) {
			categorizedSizes.get(category)[propertyGroupIndex].setEditorWidth(editorIndex, width);
		}
	}

	/**
	 * Set the width of the editor that is specified by the category, the property group
	 * name, and the property name.
	 * 
	 * @param category the category that the editor belongs to
	 * @param propertyGroupName the name of the property group that the editor belongs to
	 * @param propertyName the name of the property in the property group
	 * @param width the new width of the editor
	 */
	public void setEditorWidth(String category, String propertyGroupName, String propertyName, int width) {
		if(categorizedSizes.containsKey(category)) {
			List<EditableProperties> properties = editingCategorizedProperties.getProperties(category);
			EditableProperties propertyGroup = null;
			int propertyGroupIndex = 0;
			boolean keepFindingGroup = true;
			for(; keepFindingGroup && propertyGroupIndex < properties.size(); propertyGroupIndex++) {
				if(properties.get(propertyGroupIndex).getName().equals(propertyGroupName)) {
					keepFindingGroup = false;
					propertyGroup = properties.get(propertyGroupIndex);
				}
			}
			int propertyIndex = 0;
			boolean keepFindingProperty = propertyGroup != null;
			for(; keepFindingProperty && propertyIndex < propertyGroup.size(); propertyIndex++) {
				if(propertyGroup.getProperty(propertyIndex).getName().equals(propertyName)) {
					keepFindingProperty = false;
				}
			}
			categorizedSizes.get(category)[propertyGroupIndex].setEditorWidth(propertyIndex, width);
		}
	}

	/**
	 * Set the specified categorized properties to be displayed on the panel.
	 * 
	 * @param categorizedProperties the properties to be displayed
	 */
	public void setCategorizedEditableProperties(CategorizedEditableProperties categorizedProperties) {
		Hashtable<String, PropertyEditorSizeModel[]> categorizedSizes = prepareDefaultSizes(categorizedProperties);
		setCategorizedEditableProperties(categorizedProperties, categorizedSizes);
	}

	/**
	 * Set the specified categorized properties to be displayed on the panel and
	 * the width of each editor.
	 * 
	 * @param categorizedProperties the properties to be displayed
	 * @param sizes the widths of each editors
	 */
	public void setCategorizedEditableProperties(CategorizedEditableProperties categorizedProperties, Hashtable<String, PropertyEditorSizeModel[]> sizes) {
		validateArguments(categorizedProperties, sizes);
		editingCategorizedProperties = categorizedProperties;
		categorizedSizes = sizes;
		selectionModel.setCategorizedEditableProperties(categorizedProperties);
		if(editingCategorizedProperties == null) {
			propertiesPanel.reset();
		}
		else {
			String currentCategory = editingCategorizedProperties.getCategories()[0];
			EditableProperties[] properties = editingCategorizedProperties.getProperties(currentCategory).toArray(new EditableProperties[0]);
			selectionModel.setSelectedCategory(currentCategory);
			propertiesPanel.setProperties(properties, sizes.get(currentCategory));
		}
	}

	/**
	 * Get the current editing categorized editable properties.
	 * 
	 * @return the current editing categorized editable properties
	 */
	public CategorizedEditableProperties getCategorizedEditableProperties() {
		return editingCategorizedProperties;
	}

	@Override
	public void removeAll() {
		detach();
		super.removeAll();
	}

	@Override
	public void detach() {
		propertiesPanel.reset();
		selectionModel.removeListSelectionListener(this);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		String category = ((Choice)categorySelector.getSelectedValue()).getValue().toString();
		if(category != null) {
			EditableProperties[] properties = editingCategorizedProperties.getProperties(category).toArray(new EditableProperties[0]);
			propertiesPanel.setProperties(properties, categorizedSizes.get(category));
			setName(editingCategorizedProperties.getMultiLanguageSupport(category).getDisplayText());
		}
	}

	/**
	 * Prepare the default editor sizes for the categorized properties when
	 * the user does not specify the sizes.
	 * 
	 * @param categorizedProperties the categorized properties to prepare for
	 * @return the default sizes
	 */
	private static Hashtable<String, PropertyEditorSizeModel[]> prepareDefaultSizes(CategorizedEditableProperties categorizedProperties) {
		Hashtable<String, PropertyEditorSizeModel[]> categorizedSizes = null;
		if(categorizedProperties != null) {
			categorizedSizes = new Hashtable<String, PropertyEditorSizeModel[]>();
			String[] categories = categorizedProperties.getCategories();
			if(categories != null) {
				for(String category : categories) {
					List<EditableProperties> properties = categorizedProperties.getProperties(category);
					PropertyEditorSizeModel[] sizes = new PropertyEditorSizeModel[properties.size()];
					for(int index = 0; index < sizes.length; index++) {
						sizes[index] = new DefaultPropertyEditorSizeModel();
					}
					categorizedSizes.put(category, sizes);
				}
			}
		}
		return categorizedSizes;
	}

	/**
	 * Validate whether the given arguments are legal (matched) or not. The legal
	 * arguments means in each category, the total number of the concrete properties
	 * and the the total number of the sizes are the same. 
	 * 
	 * @param categorizedProperties the categorized properties to be validated
	 * @param sizes the sizes to be validated
	 * @throws RuntimeException if these two arguments are not matched.
	 */
	private void validateArguments(CategorizedEditableProperties categorizedProperties, Hashtable<String, PropertyEditorSizeModel[]> sizes) {
		String[] categories = categorizedProperties.getCategories();
		if(categorizedProperties != null && sizes != null && categories != null) {
			boolean match = categories.length == sizes.size();
			for(int index = 0; match && index < sizes.size(); index++) {
				String category = categories[index];
				match &= categorizedProperties.getProperties(category).size() == sizes.get(category).length;
			}
			if(!match) {
				throw new RuntimeException(ILLEGAL_SIZE_ARGUMENTS_EXCEPTION);
			}
		}
	}
}
