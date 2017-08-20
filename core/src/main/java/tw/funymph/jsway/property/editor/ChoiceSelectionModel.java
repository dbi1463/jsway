/* ChoiceSelectionModel.java created on 2012/9/30
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
import static tw.funymph.jsway.property.EditableProperty.KEY_VALUE;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.Choice;
import tw.funymph.jsway.property.ChoicesProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;
import tw.funymph.jsway.utils.ObjectUtilities;

/** A class that manages the selection for the {@link ChoicesPropertyEditor}.
 * This class is designed for internal use only.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
class ChoiceSelectionModel implements SelfDetachable, ComboBoxModel, EditablePropertyListener {

	private ChoicesProperty choicesProperty;
	private List<ListDataListener> listeners;

	/**
	 * Construct a <code>ChoiceSelectionModel</code> instance with the given
	 * property as the choices source.
	 * 
	 * @param property the choices source
	 */
	ChoiceSelectionModel(ChoicesProperty property) {
		setProperty(property);
		listeners = new ArrayList<ListDataListener>();
	}

	/**
	 * Set the new choices source.
	 * 
	 * @param property the new choices source
	 */
	public void setProperty(ChoicesProperty property) {
		if(!ObjectUtilities.equals(choicesProperty, property)) {
			if(choicesProperty != null) {
				choicesProperty.removeEditablePropertyListener(this);
			}
			choicesProperty = property;
			if(choicesProperty != null) {
				choicesProperty.addEditablePropertyListener(this);
			}
		}
	}

	@Override
	public void detach() {
		choicesProperty.removeEditablePropertyListener(this);
	}

	@Override
	public int getSize() {
		return choicesProperty.getChoices().size();
	}

	@Override
	public Object getElementAt(int index) {
		if(index < choicesProperty.getChoices().size()) {
			return choicesProperty.getChoices().get(index);
		}
		return null;
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
	public void setSelectedItem(Object choice) {
		choicesProperty.setValue(((Choice)choice).getValue());
		fireListDataChangedEvent();
	}

	@Override
	public Object getSelectedItem() {
		return choicesProperty.getSelectedChoice();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent e) {
		if(KEY_VALUE.equals(e.getKey())) {
			fireListDataChangedEvent();
		}
	}

	/** Notify all registered list data listeners.
	 */
	private void fireListDataChangedEvent() {
		ListDataEvent event = new ListDataEvent(this, CONTENTS_CHANGED, 0, getSize());
		for(ListDataListener listener : listeners) {
			listener.contentsChanged(event);
		}
	}
}
