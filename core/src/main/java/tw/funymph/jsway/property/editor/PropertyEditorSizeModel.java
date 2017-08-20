/* PropertyEditorSizeModel.java created on 2012/10/15
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

/**
 * A class can implements this interface to provides information about the size
 * of each editor (in fact, right now, the width only) for {@link PropertiesDialogPanel}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public interface PropertyEditorSizeModel {

	/**
	 * Get the width of the index-th editor.
	 * 
	 * @param index the index of the editor
	 * @return the width of the editor
	 */
	int getEditorWidth(int index);

	/**
	 * Set the width of the index-th editor. The method should notify all registered
	 * listeners that the size is changed.
	 * 
	 * @param index the index of the editor
	 * @param width the new widthe of the editor
	 */
	void setEditorWidth(int index, int width);

	/**
	 * Add the specified listener into the notification list.
	 * 
	 * @param listener the listener who want to be notified
	 */
	void addPropertyEditorSizeChangeListener(PropertyEditorSizeChangeListener listener);

	/**
	 * Remove the specified listener from the notification list.
	 * 
	 * @param listener the listener who does not want to be notified
	 */
	void removePropertyEditorSizeChangeListener(PropertyEditorSizeChangeListener listener);
}
