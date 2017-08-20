/* NumberProperty.java created on 2011/9/29
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

import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.Vector;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A class that extends {@link BoundProperty} to manages a numeric value.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class NumberProperty<T extends Comparable<T>> extends BoundProperty<T> {

	public static final String KEY_STEP = "NumberProperty.step";
	public static final String KEY_UNIT = "NumberProperty.unit";
	public static final String KEY_AVAILABLE_UNITS = "NumberProperty.availableUnits";

	private static final String NULL_STEP_EXCEPTION = "Cannot set a null step";

	private String selectedUnit;
	private Vector<String> allUnits;

	private Number numberStep;
	private Number currentValue;

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name,
	 * initial value, and step value. The minimum and maximum are set to null.
	 * 
	 * @param name the name of the property
	 * @param value the initial value
	 * @param step the step value
	 * @throws NullPointerException if any of the name, value, and step is null
	 */
	public NumberProperty(String name, Number value, Number step) {
		this(name, value, null, null, step, null);
	}

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name,
	 * initial value, step, and unit value. The minimum and maximum are set to null.
	 * 
	 * @param name the name of the property
	 * @param value the initial value
	 * @param step the step value
	 * @param unit the unit of the value
	 * @throws NullPointerException if any of the name, value, and step is null
	 */
	public NumberProperty(String name, Number value, Number step, String unit) {
		this(name, value, null, null, step, unit);
	}

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name,
	 * initial value, minimum, maximum, and step value.
	 * 
	 * @param name the name of the property
	 * @param value the initial value
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param step the step value
	 * @throws NullPointerException if any of the name, value, and step is null
	 */
	public NumberProperty(String name, Number value, T min, T max, Number step) {
		this(name, value, null, null, step, null);
	}

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name,
	 * initial value, minimum, maximum, step and unit value.
	 * 
	 * @param name the name of the property
	 * @param value the initial value
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param step the step value
	 * @param unit the unit of the value
	 * @throws NullPointerException if any of the name, support, value, and step is null
	 */
	public NumberProperty(String name, Number value, T min, T max, Number step, String unit) {
		this(name, simpleSupport(name), value, min, max, step, unit);
	}

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name, the
	 * multiple language support, the initial value, and step value. The minimum and
	 * maximum are set to null.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param value the initial value
	 * @param step the step value
	 * @throws NullPointerException if any of the name, support, value, and step is null
	 * @since 1.1
	 */
	public NumberProperty(String name, MultiLanguageSupport support, Number value, Number step) {
		this(name, support, value, null, null, step, null);
	}

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name, the
	 * multiple language support, the initial value, step, and unit value. The minimum
	 * and maximum are set to null.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param value the initial value
	 * @param step the step value
	 * @param unit the unit of the value
	 * @throws NullPointerException if any of the name, support, value, and step is null
	 * @since 1.1
	 */
	public NumberProperty(String name, MultiLanguageSupport support, Number value, Number step, String unit) {
		this(name, support, value, null, null, step, unit);
	}

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name, the
	 * multiple language support, the initial value, minimum, maximum, and step value.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param value the initial value
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param step the step value
	 * @throws NullPointerException if any of the name, support, value, and step is null
	 * @since 1.1
	 */
	public NumberProperty(String name, MultiLanguageSupport support, Number value, T min, T max, Number step) {
		this(name, support, value, null, null, step, null);
	}

	/**
	 * Construct a <code>NumberProperty</code> instance with the specified name, the multiple
	 * language support, the initial value, minimum, maximum, step and unit value.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param value the initial value
	 * @param min the minimum value
	 * @param max the maximum value
	 * @param step the step value
	 * @param unit the unit of the value
	 * @throws NullPointerException if any of the name, support, value, and step is null
	 * @since 1.1
	 */
	public NumberProperty(String name, MultiLanguageSupport support, Number value, T min, T max, Number step, String unit) {
		super(name, support, min, max);
		allUnits = new Vector<String>();
		setStep(step);
		setNumber(value);
		if(unit != null) {
			setUnit(unit);
			addAvailableUnit(unit);
		}
	}

	/**
	 * Get the current numeric value
	 * 
	 * @return the current value
	 */
	public Number getNumber() {
		return currentValue;
	}

	/**
	 * Get the step between the next value or previous value
	 * 
	 * @return the step
	 */
	public Number getStep() {
		return numberStep;
	}

	/**
	 * Get the unit of the value.
	 * 
	 * @return the unit of the value
	 */
	public String getUnit() {
		return selectedUnit;
	}

	/**
	 * Get the available units of the property
	 * 
	 * @return the available units
	 */
	public Vector<String> getAvailableUnits() {
		return allUnits;
	}

	@Override
	public Object getValue() {
		return currentValue;
	}

	/**
	 * Change the value of the property to the specified number. The number
	 * can not be null or this method will throw {@link NullPointerException}.
	 * 
	 * @param number the new property value
	 * @throws NullPointerException if the number is null
	 */
	@SuppressWarnings("unchecked")
	public void setNumber(Number number) {
		requireNonNull(number, NULL_VALUE_EXCEPTION);
		if(checkBound((T)number) && !number.equals(currentValue)) {
			Number oldValue = currentValue;
			currentValue = number;
			notifyEditablePropertyListeners(KEY_VALUE, currentValue, oldValue);
		}
	}

	/**
	 * Set the new step. Note that the step can not be null. This method will fire
	 * property changed event with the {@link #KEY_STEP} and the new step when a
	 * different step value is set.
	 * 
	 * @param step the new step
	 * @throws NullPointerException if the step is null
	 */
	public void setStep(Number step) {
		requireNonNull(step, NULL_STEP_EXCEPTION);
		if(!step.equals(numberStep)) {
			Number oldStep = numberStep;
			numberStep = step;
			notifyEditablePropertyListeners(KEY_STEP, numberStep, oldStep);
		}
	}

	/**
	 * Set the new unit of the value. This method will fire property changed event
	 * with the {@link #KEY_UNIT} and the new unit when a different unit value is set.
	 * 
	 * @param unit the new unit
	 */
	public void setUnit(String unit) {
		if(!ObjectUtilities.equals(unit, selectedUnit)) {
			String oldUnit = selectedUnit;
			selectedUnit = unit;
			notifyEditablePropertyListeners(KEY_UNIT, selectedUnit, oldUnit);
		}
	}

	/**
	 * Add an available unit for the property. The initial unit passed in the constructor
	 * is added into the available units by default.
	 * 
	 * @param unit the new available unit
	 * @throws NullPointerException if the unit is null
	 */
	public void addAvailableUnit(String unit) {
		requireNonNull(unit, "Cannot add a null unit");
		if(!allUnits.contains(unit)) {
			Vector<String> oldUnits = new Vector<String>(allUnits);
			allUnits.add(unit);
			notifyEditablePropertyListeners(KEY_AVAILABLE_UNITS, allUnits, oldUnits);
		}
	}

	@Override
	public void setValue(Object value) {
		setNumber((Number)value);
	}
}
