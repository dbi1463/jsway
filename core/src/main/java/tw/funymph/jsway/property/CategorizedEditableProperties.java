/* CategorizedEditableProperties.java created on Jun 5, 2012
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

import java.util.List;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.NamedObject;

/**
 * A class can implement this interface to manage a suite of categorized editable properties.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface CategorizedEditableProperties extends NamedObject, MultiLanguageSupport {

	/** The type of the event that is notified when a property is added into the container. */
	public static final String TYPE_CATEGORY_ADDED = "CategorizedEditableProperties.categoryAdded";

	/** The type of the event that is notified when a property is removed from the container. */
	public static final String TYPE_CATEGORY_REMOVED = "CategorizedEditableProperties.categoryRemoved";

	/** The type of the event that is notified when a property is removed from the container. */
	public static final String TYPE_CATEGORY_DISPLAYNAME_CHANGED = "CategorizedEditableProperties.categoryDisplayNameChanged";

	/**
	 * Add a suite of editable properties with category name. If the category has existed, the list of
	 * properties are merged with the existing cloned properties list that means this method does not
	 * change the list of properties given on first time calling {@link #addCategory(String, List)}.
	 * 
	 * @param category the category name of the properties
	 * @param properties a group of editable properties
	 * @throws NullPointerException if any argument is null
	 */
	void addCategory(String category, List<EditableProperties> properties);

	/**
	 * Add a suite of editable properties with category name. If the category has existed, the list of
	 * properties are merged with the existing cloned properties list that means this method does not
	 * change the list of properties given on first time calling {@link #addCategory(String, MultiLanguageSupport, List)}.
	 * 
	 * @param category the category name of the properties
	 * @param support the support to get category names in different languages
	 * @param properties a group of editable properties
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	void addCategory(String category, MultiLanguageSupport support, List<EditableProperties> properties);

	/**
	 * Add the editable properties into the specified category. If the category does not exist,
	 * create a category by calling {@link #addCategory(String, List)}.
	 * 
	 * @param category the category
	 * @param properties the editable properties
	 * @throws NullPointerException if any argument is null
	 */
	void addProperties(String category, EditableProperties properties);

	/**
	 * Add the editable properties into the specified category. If the category does not exist,
	 * create a category by calling {@link #addCategory(String, MultiLanguageSupport, List)}.
	 * 
	 * @param category the category
	 * @param support the support to get category names in different languages
	 * @param properties the editable properties
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	void addProperties(String category, MultiLanguageSupport support, EditableProperties properties);

	/**
	 * Remove the specified category of editable properties
	 * 
	 * @param category the category name of the properties to be removed
	 */
	void removeCategory(String category);

	/**
	 * Get the display text for the category.
	 * 
	 * @param category the category to get display text
	 * @return the display text for the category; null if the category does not exist
	 */
	String getCategoryDisplayText(String category);

	/**
	 * Get the multiple language support for the category.
	 * 
	 * @param category the category to get the support
	 * @return the multiple language support for the category; null if the category does not exist
	 * @since 1.1
	 */
	MultiLanguageSupport getMultiLanguageSupport(String category);

	/**
	 * Get the editable properties of the specified category name
	 * 
	 * @param category the category name
	 * @return the set of editable properties; null if the specified category does not exist
	 */
	List<EditableProperties> getProperties(String category);

	/**
	 * Get the number of the categories in the instance
	 * 
	 * @return the number of the categories
	 */
	int getCategoriesCount();

	/**
	 * Get the names of all categories in the instance
	 * 
	 * @return the names of all categories
	 */
	String[] getCategories();

	/**
	 * Add the specified listener into the list that will be notified when the properties
	 * has any change (add a category or remove a category).
	 * 
	 * @param listener the listener to be added
	 */
	void addCategorizedEditablePropertiesListener(CategorizedEditablePropertiesListener listener);

	/**
	 * Remove the specified listener from the notification list.
	 * 
	 * @param listener the listener to be removed
	 */
	void removeCategorizedEditablePropertiesListener(CategorizedEditablePropertiesListener listener);
}
