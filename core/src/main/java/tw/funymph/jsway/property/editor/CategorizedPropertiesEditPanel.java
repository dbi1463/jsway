/* CategorizedPropertiesEditPanel.java created on Jun 5, 2012
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

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.CategorizedEditableProperties;
import tw.funymph.jsway.property.Choice;

/**
 * A GUI panel that displays the aligned editors for a suite of categorized editable properties.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class CategorizedPropertiesEditPanel extends JPanel implements SelfDetachable, ListDataListener {

	private static final long serialVersionUID = -1655107136276511444L;

	private static final String NULL_CATEGORY_EXCEPTION = "Cannot set a null category";

	private JComboBox<Choice> categorySelector;
	private PropertiesEditPanel propertiesEeditPanel;

	private CategorySelectionModel selectionModel;
	private CategorizedEditableProperties categorizedProperties;

	/**
	 * Construct an empty <code>CategorizedPropertiesEditPanel</code> instance.
	 */
	public CategorizedPropertiesEditPanel() {
		this(null);
	}

	/**
	 * Construct a <code>CategorizedPropertiesEditPanel</code> instance with the initial values.
	 * 
	 * @param properties the categorized editable properties 
	 */
	public CategorizedPropertiesEditPanel(CategorizedEditableProperties properties) {
		propertiesEeditPanel = new PropertiesEditPanel();
		selectionModel = new CategorySelectionModel();
		categorySelector = new JComboBox<Choice>(selectionModel);
		setLayout(new BorderLayout());
		add(categorySelector, BorderLayout.NORTH);
		add(propertiesEeditPanel, BorderLayout.CENTER);
		setCategorizedEditableProperties(properties);
	}

	/**
	 * Set the specified categorized properties to be displayed on the panel.
	 * 
	 * @param properties the properties to be displayed
	 */
	public void setCategorizedEditableProperties(CategorizedEditableProperties properties) {
		categorizedProperties = properties;
		selectionModel.setCategorizedEditableProperties(properties);
		if(categorizedProperties != null) {
			String currentCategory = categorizedProperties.getCategories()[0];
			selectionModel.setSelectedCategory(currentCategory);
			selectionModel.addListDataListener(this);
			propertiesEeditPanel.setProperties(categorizedProperties.getProperties(currentCategory));
		}
		else {
			propertiesEeditPanel.reset();
		}
	}

	/**
	 * Get the current editing categorized editable properties.
	 * 
	 * @return the current editing categorized editable properties
	 */
	public CategorizedEditableProperties getCategorizedEditableProperties() {
		return categorizedProperties;
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

	@Override
	public void removeAll() {
		detach();
		super.removeAll();
	}

	@Override
	public void detach() {
		propertiesEeditPanel.reset();
		selectionModel.removeListDataListener(this);
	}

	@Override
	public void contentsChanged(ListDataEvent e) {
		String category = selectionModel.getSelectedCategory();
		propertiesEeditPanel.setProperties(categorizedProperties.getProperties(category));
		setName(categorizedProperties.getMultiLanguageSupport(category).getDisplayText());
	}

	@Override
	public void intervalAdded(ListDataEvent e) {}

	@Override
	public void intervalRemoved(ListDataEvent e) {}
}
