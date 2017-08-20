/* UnitSelectionModel.java created on Jun 5, 2012
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

import java.util.LinkedList;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import tw.funymph.jsway.property.NumberProperty;

/**
 * A helper class to handle the selection of multiple units of a number property.
 *  
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class UnitSelectionModel<T extends Comparable<T>> implements ComboBoxModel<String> {

	private NumberProperty<T> wrappedProperty;
	private LinkedList<ListDataListener> listeners;

	/**
	 * Construct a <code>UnitSelectionModel</code> instance for the specified
	 * number property.
	 * 
	 * @param property the number property
	 */
	public UnitSelectionModel(NumberProperty<T> property) {
		wrappedProperty = property;
		listeners = new LinkedList<ListDataListener>();
	}

	@Override
	public int getSize() {
		return wrappedProperty.getAvailableUnits().size();
	}

	@Override
	public String getElementAt(int index) {
		return wrappedProperty.getAvailableUnits().get(index);
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
		wrappedProperty.setUnit((String)anItem);
	}

	@Override
	public Object getSelectedItem() {
		return wrappedProperty.getUnit();
	}
}
