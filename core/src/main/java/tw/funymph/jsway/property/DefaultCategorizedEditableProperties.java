/* DefaultCategorizedEditableProperties.java created on Jun 5, 2012
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
package tw.funymph.jsway.property;

import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.property.EditableProperty.NULL_LANGUAGE_SUPPORT_EXCEPTION;
import static tw.funymph.jsway.property.EditableProperty.NULL_NAME_EXCEPTION;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;

/**
 * A default implementation of the {@link CategorizedEditableProperties} interface.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class DefaultCategorizedEditableProperties implements CategorizedEditableProperties {

	private static final String NULL_CATEGORY_NAME_EXCEPTION = "Cannot add a category with a null name!";
	private static final String NULL_CATEGORY_PROPERTIES_EXCEPTION = "Cannot add a category with null properties!";
	private static final String NULL_CATEGORY_MULTI_LANG_SUPPORT_EXCEPTION = "Cannot add a category with a null language support!";

	private String propertiesName;
	private MultiLanguageSupport multiLangSupport;
	private List<CategorizedEditablePropertiesListener> listeners;
	private Hashtable<String, List<EditableProperties>> categories;
	private Hashtable<String, MultiLanguageSupport> categoryMultiLangSupports;

	/**
	 * Construct a <code>EditablePropertiesMap</code> instance with name.
	 * 
	 * @param name the name of the categorized properties
	 */
	public DefaultCategorizedEditableProperties(String name) {
		this(name, simpleSupport(name));
	}

	/**
	 * Construct a <code>EditablePropertiesMap</code> instance with name and the multiple
	 * language support.
	 * 
	 * @param name the name of the categorized properties
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	public DefaultCategorizedEditableProperties(String name, MultiLanguageSupport support) {
		propertiesName = requireNonNull(name, NULL_NAME_EXCEPTION);
		multiLangSupport = requireNonNull(support, NULL_LANGUAGE_SUPPORT_EXCEPTION);
		categoryMultiLangSupports = new Hashtable<String, MultiLanguageSupport>();
		categories = new Hashtable<String, List<EditableProperties>>();
		listeners = new ArrayList<CategorizedEditablePropertiesListener>();
	}	

	@Override
	public void addCategory(String category, List<EditableProperties> properties) {
		addCategory(category, simpleSupport(category), properties);
	}

	@Override
	public void addCategory(String category, MultiLanguageSupport support, List<EditableProperties> properties) {
		requireNonNull(category, NULL_CATEGORY_NAME_EXCEPTION);
		requireNonNull(support, NULL_CATEGORY_MULTI_LANG_SUPPORT_EXCEPTION);
		requireNonNull(properties, NULL_CATEGORY_PROPERTIES_EXCEPTION);
		if(categories.containsKey(category)) {
			categories.get(category).addAll(properties);
		}
		else {
			categories.put(category, new ArrayList<EditableProperties>(properties));
			categoryMultiLangSupports.put(category, support);
		}
		notifyCategorizedEditablePrpertiesListeners(TYPE_CATEGORY_ADDED, category, properties);
	}

	@Override
	public void addProperties(String category, EditableProperties properties) {
		addProperties(category, simpleSupport(category), properties);
	}

	@Override
	public void addProperties(String category, MultiLanguageSupport support, EditableProperties properties) {
		requireNonNull(category, NULL_CATEGORY_NAME_EXCEPTION);
		requireNonNull(properties, NULL_CATEGORY_PROPERTIES_EXCEPTION);
		if(categories.containsKey(category)) {
			categories.get(category).add(properties);
		}
		else {
			List<EditableProperties> propertiesList = new ArrayList<EditableProperties>();
			propertiesList.add(properties);
			addCategory(category, support, propertiesList);
		}
	}

	@Override
	public void removeCategory(String category) {
		if(categories.containsKey(category)) {
			List<EditableProperties> properties = getProperties(category);
			categories.remove(category);
			notifyCategorizedEditablePrpertiesListeners(TYPE_CATEGORY_REMOVED, category, properties);
		}
	}

	@Override
	public List<EditableProperties> getProperties(String category) {
		return categories.get(category);
	}

	@Override
	public String getName() {
		return propertiesName;
	}

	@Override
	public String getDisplayText() {
		return multiLangSupport.getDisplayText();
	}

	@Override
	public void addMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		if(multiLangSupport != null) {
			multiLangSupport.addMultiLanguageSupportListener(listener);
		}
	}

	@Override
	public void removeMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		if(multiLangSupport != null) {
			multiLangSupport.removeMultiLanguageSupportListener(listener);
		}
	}

	@Override
	public String getCategoryDisplayText(String category) {
		return categoryMultiLangSupports.containsKey(category)? categoryMultiLangSupports.get(category).getDisplayText() : category;
	}

	@Override
	public MultiLanguageSupport getMultiLanguageSupport(String category) {
		return categoryMultiLangSupports.get(category);
	}

	@Override
	public int getCategoriesCount() {
		return categories.size();
	}

	@Override
	public String[] getCategories() {
		return categories.keySet().toArray(new String[0]);
	}

	@Override
	public void addCategorizedEditablePropertiesListener(CategorizedEditablePropertiesListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeCategorizedEditablePropertiesListener(CategorizedEditablePropertiesListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify all registered properties changed listeners.
	 * 
	 * @param type the type of the event
	 * @param category the added or removed category name
	 * @param properties the category of the added or removed properties
	 */
	private void notifyCategorizedEditablePrpertiesListeners(String type, String category, List<EditableProperties> properties) {
		for(CategorizedEditablePropertiesListener listener : listeners) {
			listener.categoriesChanged(type, category, properties);
		}
	}
}
