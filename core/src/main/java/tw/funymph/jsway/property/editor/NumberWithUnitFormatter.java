/* NumberWithUnitFormatter.java created on May 3, 2012
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

import java.text.ParseException;

import javax.swing.text.NumberFormatter;

/**
 * The class formats the text for the number property with unit and
 * converts the text to value without unit.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class NumberWithUnitFormatter extends NumberFormatter {

	private static final long serialVersionUID = 8731963800793130904L;

	private static final String NUMBER_UNIT_SEPARATOR = " ";

	private String numberUnit;

	/** Construct a <code>NumberWithUnitFormatter</code> instance with the unit
	 * 
	 * @param unit the unit for the number property
	 */
	public NumberWithUnitFormatter(String unit) {
		numberUnit = unit;
	}

	public String valueToString(Object value) throws ParseException {
		return (numberUnit != null)? (super.valueToString(value) + NUMBER_UNIT_SEPARATOR + numberUnit) : super.valueToString(value);
	}

	public Object stringToValue(String text) throws ParseException {
		return (numberUnit != null)? super.stringToValue(text.replace(numberUnit, "").trim()) : super.stringToValue(text);
	}
}
