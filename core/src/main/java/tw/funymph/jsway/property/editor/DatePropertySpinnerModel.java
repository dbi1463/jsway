/* DatePropertySpinnerModel.java created on 2012/4/30
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

import static tw.funymph.jsway.property.EditableProperty.KEY_VALUE;

import javax.swing.SpinnerDateModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import tw.funymph.jsway.property.DateProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * A class that transforms the interface from {@link DateProperty} to {@link SpinnerDateModel}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class DatePropertySpinnerModel extends SpinnerDateModel implements ChangeListener, EditablePropertyListener {

	private static final long serialVersionUID = -1164418858471041666L;

	private DateProperty dateProperty;

	/**
	 * Construct a <code>DatePropertySpinnerModel</code> instance with the specified property.
	 * 
	 * @param property the property to be transformed
	 */
	public DatePropertySpinnerModel(DateProperty property) {
		super(property.getDate(), property.getMinimum(), property.getMaximum(), property.getCalendarField());
		dateProperty = property;
		addChangeListener(this);
		dateProperty.addEditablePropertyListener(this);
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		if(KEY_VALUE.equals(event.getKey())) {
			setValue(event.getCurrentValue());
		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(!dateProperty.getDate().equals(getDate())) {
			dateProperty.setValue(getDate());
		}
	}
}
