/* DefaultPropertyEditorSizeModel.java created on 2012/10/15
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

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * The default implementation of {@link PropertyEditorSizeModel}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class DefaultPropertyEditorSizeModel implements PropertyEditorSizeModel {

	private static final int DEFAULT_EDITOR_WIDTH = 180;

	private Hashtable<Integer, Integer> sizes;
	private ArrayList<PropertyEditorSizeChangeListener> listeners;

	/**
	 * Construct a <code>DefaultPropertyEditorSizeModel</code> instance.
	 */
	public DefaultPropertyEditorSizeModel() {
		sizes = new Hashtable<Integer, Integer>();
		listeners = new ArrayList<PropertyEditorSizeChangeListener>();
	}

	@Override
	public int getEditorWidth(int index) {
		return sizes.containsKey(index)? sizes.get(index) : DEFAULT_EDITOR_WIDTH;
	}

	@Override
	public void setEditorWidth(int index, int width) {
		sizes.put(index, width);
		notifySizeChanged(index, width);
	}

	@Override
	public void addPropertyEditorSizeChangeListener(PropertyEditorSizeChangeListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removePropertyEditorSizeChangeListener(PropertyEditorSizeChangeListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify all registered listeners that the size of the specified editor
	 * is changed.
	 * 
	 * @param index the index of the changed editor
	 * @param width the new width
	 */
	private void notifySizeChanged(int index, int width) {
		for(PropertyEditorSizeChangeListener listener : listeners) {
			listener.sizeChanged(index, width);
		}
	}
}
