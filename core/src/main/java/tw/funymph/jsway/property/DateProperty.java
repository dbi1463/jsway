/* DateProperty.java created on 2011/9/29
 *
 * Copyright (C) 2011. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.property;

import static java.lang.System.currentTimeMillis;
import static java.util.Calendar.DAY_OF_MONTH;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.Date;

import tw.funymph.jsway.MultiLanguageSupport;

/**
 * A class that extends {@link BoundProperty} to manages a date value.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class DateProperty extends BoundProperty<Date> {

	public static final String KEY_CALENDAR_FIELD = "DateProperty.calendarField";

	private Date currentDate;
	private int calendarField;

	/**
	 * Construct a <code>DateProperty</code> instance with the name. The
	 * date is set to today.
	 * 
	 * @param name the name of the property
	 * @throws NullPointerException if the name is null
	 */
	public DateProperty(String name) {
		this(name, new Date(System.currentTimeMillis()), null, null, DAY_OF_MONTH);
	}

	/**
	 * Construct a <code>DateProperty</code> instance with the name and the multiple
	 * language support. The date is set to today.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	public DateProperty(String name, MultiLanguageSupport support) {
		this(name, support, new Date(currentTimeMillis()), null, null, DAY_OF_MONTH);
	}

	/**
	 * Construct a <code>DateProperty</code> instance with the name, the date, and the
	 * boundary.
	 * 
	 * @param name the name of the property
	 * @param value the date to be displayed
	 * @param start the start value of the boundary
	 * @param end the end value of the boundary
	 * @param field the calendar field to decide the next value and previous value
	 * @throws NullPointerException if either the name or the value is null
	 */
	public DateProperty(String name, Date value, Date start, Date end, int field) {
		this(name, simpleSupport(name), value, start, end, field);
	}

	/**
	 * Construct a <code>DateProperty</code> instance with the name, the multiple language
	 * support, the date, and the boundary.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param value the date to be displayed
	 * @param start the start value of the boundary
	 * @param end the end value of the boundary
	 * @param field the calendar field to decide the next value and previous value
	 * @throws NullPointerException if the name, the multiple language support, or the value is null
	 * @since 1.1
	 */
	public DateProperty(String name, MultiLanguageSupport support, Date value, Date start, Date end, int field) {
		super(name, support, start, end);
		setDate(value);
		calendarField = field;
	}

	/**
	 * Get the calendar field. Please refer to {@link java.util.Calendar} for the information
	 * about the calendar field.
	 * 
	 * @return the calendar field
	 */
	public int getCalendarField() {
		return calendarField;
	}

	/**
	 * Get the date.
	 * 
	 * @return the date
	 */
	public Date getDate() {
		return currentDate;
	}

	/**
	 * Set the calendar field. Please refer to {@link java.util.Calendar} for the information
	 * about the calendar field.
	 * 
	 * @param field the calendar field
	 */
	public void setCalendarField(int field) {
		if(calendarField != field) {
			int oldCalendarField = calendarField;
			calendarField = field;
			notifyEditablePropertyListeners(KEY_CALENDAR_FIELD, calendarField, oldCalendarField);
		}
	}

	/**
	 * Set the new date.
	 * 
	 * @param date the new date
	 * @throws NullPointerException if the date is null
	 */
	public void setDate(Date date) {
		requireNonNull(date, NULL_VALUE_EXCEPTION);
		if(checkBound(date) && !date.equals(currentDate)) {
			Date oldDate = currentDate;
			currentDate = date;
			notifyEditablePropertyListeners(KEY_VALUE, currentDate, oldDate);
		}
	}

	@Override
	public void setValue(Object value) {
		setDate((Date)value);
	}

	@Override
	public Object getValue() {
		return currentDate;
	}
}
