/* CategorizedEditablePropertiesListener.java created on Jun 5, 2012
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

/**
 * A class can implement this interface and register as a listener
 * to receive the categorized properties added or removed events.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface CategorizedEditablePropertiesListener {

	/** 
	 * Invoked when the properties has been changed (a category of properties is added or removed
	 * that is specified by the type). The types are defined in {@link CategorizedEditableProperties}:
	 * <ul>
	 * <li>{@link CategorizedEditableProperties#TYPE_CATEGORY_ADDED TYPE_CATEGORY_ADDED}: means a category of properties is added.</li>
	 * <li>{@link CategorizedEditableProperties#TYPE_CATEGORY_REMOVED TYPE_CATEGORY_REMOVED}: means a category of properties is removed.</li>
	 * </ul>
	 * 
	 * @param type the type of the event
	 * @param category the category of properties
	 * @param properties the added or removed properties
	 */
	void categoriesChanged(String type, String category, List<EditableProperties> properties);
}
