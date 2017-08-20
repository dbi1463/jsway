/* PropertyEditorSizeChangeListener.java created on 2012/10/15
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
 * A class can implement this interface to register itself as an event
 * listener that will be notified when an editor size is changed.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public interface PropertyEditorSizeChangeListener {

	/**
	 * Invoke when the size of an editor is changed.
	 * 
	 * @param editorIndex the index of the changed editor
	 * @param width the new width
	 */
	void sizeChanged(int editorIndex, int width);
}
