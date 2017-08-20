/* CategorySelectionModel.java created on 2012/9/29
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

import static javax.swing.event.ListDataEvent.CONTENTS_CHANGED;
import static tw.funymph.jsway.MultiLanguageSupportUtilities.registerMultiLanguageSupport;
import static tw.funymph.jsway.MultiLanguageSupportUtilities.unregisterMultiLanguageSupport;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.property.CategorizedEditableProperties;
import tw.funymph.jsway.property.CategorizedEditablePropertiesListener;
import tw.funymph.jsway.property.Choice;
import tw.funymph.jsway.property.EditableProperties;
import tw.funymph.jsway.property.SimpleChoice;
import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A combo box model that is used by {@link CategorizedPropertiesEditPanel}.
 * This class is designed for internal use only.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
class CategorySelectionModel extends DefaultListSelectionModel implements ComboBoxModel, MultiLanguageSupportListener, CategorizedEditablePropertiesListener {

	private static final long serialVersionUID = -145050539687584779L;

	private List<Choice> choices;
	private Choice selectedChoice;

	private List<ListDataListener> listeners;
	private CategorizedEditableProperties categorizedProperties;

	/**
	 * Construct <code>CategorySelectionModel</code> instance.
	 */
	CategorySelectionModel() {
		choices = new ArrayList<Choice>();
		listeners = new ArrayList<ListDataListener>();
	}

	/**
	 * Construct <code>CategorySelectionModel</code> instance.
	 * 
	 * @param properties the categorized properties.
	 * @since 1.2
	 */
	CategorySelectionModel(CategorizedEditableProperties properties) {
		choices = new ArrayList<Choice>();
		listeners = new ArrayList<ListDataListener>();
		setCategorizedEditableProperties(properties);
	}

	/**
	 * Set the new categorized properties. If null is given, the combo box
	 * model will be an empty model.
	 * 
	 * @param properties the new categorized properties
	 */
	public void setCategorizedEditableProperties(CategorizedEditableProperties properties) {
		if(categorizedProperties != properties) {
			if(categorizedProperties != null) {
				categorizedProperties.removeCategorizedEditablePropertiesListener(this);
			}
			categorizedProperties = properties;
			if(categorizedProperties != null) {
				categorizedProperties.addCategorizedEditablePropertiesListener(this);
			}
			prepareChoices();
			fireListDataChangedEvent();
		}
	}

	/**
	 * Set the selected category.
	 * 
	 * @param category the new selected category
	 */
	public void setSelectedCategory(String category) {
		Choice choice = findCategoryChoice(category);
		if(choice != null) {
			setSelectedItem(choice);
			int index = choices.indexOf(choice);
			setSelectionInterval(index, index);
		}
	}

	/**
	 * Get the selected category.
	 * 
	 * @return the selected category; null if the model is empty
	 */
	public String getSelectedCategory() {
		return selectedChoice != null? (String)selectedChoice.getValue() : null;
	}

	@Override
	public int getMaxSelectionIndex() {
		return choices.indexOf(selectedChoice);
	}

	@Override
	public int getMinSelectionIndex() {
		return choices.indexOf(selectedChoice);
	}

	@Override
	public boolean isSelectedIndex(int index) {
		return index == choices.indexOf(selectedChoice);
	}

	@Override
	public int getSelectionMode() {
		return SINGLE_SELECTION;
	}

	@Override
	public void setSelectionInterval(int index0, int index1) {
		setSelectedItem(choices.get(index1));
		super.setSelectionInterval(index0, index1);
	}

	@Override
	public void categoriesChanged(String type, String category, List<EditableProperties> properties) {
		prepareChoices();
		fireListDataChangedEvent();
	}

	@Override
	public int getSize() {
		return choices.size();
	}

	@Override
	public Object getElementAt(int index) {
		return choices.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeListDataListener(ListDataListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedChoice = (Choice)anItem;
		fireListDataChangedEvent();
	}

	@Override
	public Object getSelectedItem() {
		return selectedChoice;
	}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		fireListDataChangedEvent();
	}

	/**
	 * Find the choice that represents the category
	 * 
	 * @param category the category to find
	 * @return the choice that represents the category; null if the choice does not exist
	 */
	private Choice findCategoryChoice(String category) {
		for(Choice choice : choices) {
			if(ObjectUtilities.equals(choice.getValue(), category)) {
				return choice;
			}
		}
		return null;
	}

	/**
	 * Prepare the choices from the categorized properties. Each category
	 * has a choice in the combo box model for selection.
	 */
	private void prepareChoices() {
		unregisterMultiLanguageSupport(choices, this);
		choices.clear();
		String selectedCategory = getSelectedCategory();
		String[] categories = categorizedProperties.getCategories();
		if(categories != null) {
			for(String category : categories) {
				MultiLanguageSupport support = categorizedProperties.getMultiLanguageSupport(category);
				choices.add(new SimpleChoice(category, support));
			}
		}
		registerMultiLanguageSupport(choices, this);
		setSelectedCategory(selectedCategory);
	}

	/**
	 * Notify all registered list data listeners.
	 */
	private void fireListDataChangedEvent() {
		ListDataEvent event = new ListDataEvent(this, CONTENTS_CHANGED, 0, getSize());
		for(ListDataListener listener : listeners) {
			listener.contentsChanged(event);
		}
	}
}
