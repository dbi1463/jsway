/* BoundProperty.java created on 2012/4/29
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

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A class defines a property that may have lower bound and upper bound.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
abstract class BoundProperty<T extends Comparable<T>> extends AbstractEditableProperty {

	public static final String KEY_MINIMUM = "BoundProperty.minimum";
	public static final String KEY_MAXIMUM = "BoundProperty.maximum";

	private static final String EXCEED_LOWER_BOUNDARY_EXCEPTION = "The value (%1$s) exceeds the lower boundary (%2$s)!";
	private static final String EXCEED_UPPER_BOUNDARY_EXCEPTION = "The value (%1$s) exceeds the upper boundary (%2$s)!";

	protected T minimum;
	protected T maximum;

	/**
	 * Construct a <code>BoundProperty</code> instance with the specified name.
	 * The lower and upper bound will be set to null.
	 * 
	 * @param name the name of the property
	 */
	protected BoundProperty(String name) {
		this(name, null, null);
	}

	/**
	 * Construct a <code>BoundProperty</code> instance with the specified name,
	 * the lower bound, and the upper bound.
	 * 
	 * @param name the name of the property
	 * @param min the lower bound
	 * @param max the upper bound
	 */
	protected BoundProperty(String name, T min, T max) {
		super(name);
		minimum = min;
		maximum = max;
	}

	/**
	 * Construct a <code>BoundProperty</code> instance with the specified name,
	 * the lower bound, and the upper bound.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param min the lower bound
	 * @param max the upper bound
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	protected BoundProperty(String name, MultiLanguageSupport support, T min, T max) {
		super(name, support);
		minimum = min;
		maximum = max;
	}

	/**
	 * Get the lower bound of the property.
	 * 
	 * @return the lower bound of the property
	 */
	public T getMinimum() {
		return minimum;
	}

	/**
	 * Get the upper bound of the property.
	 * 
	 * @return the upper bound of the property
	 */
	public T getMaximum() {
		return maximum;
	}

	/**
	 * Set the new upper bound of the property.
	 * 
	 * @param max the upper bound of the property
	 */
	public void setMaximum(T max) {
		if(!ObjectUtilities.equals(max, maximum)) {
			T oldMaximum = maximum;
			maximum = max;
			notifyEditablePropertyListeners(KEY_MAXIMUM, maximum, oldMaximum);
		}
	}

	/**
	 * Set the new lower bound of the property.
	 * 
	 * @param min the lower bound of the property
	 */
	public void setMinimum(T min) {
		if(!ObjectUtilities.equals(min, minimum)) {
			T oldMinimum = minimum;
			minimum = min;
			notifyEditablePropertyListeners(KEY_MAXIMUM, minimum, oldMinimum);
		}
	}

	/**
	 * Check whether the value is inside the boundary.
	 * 
	 * @param value the value to be checked
	 * @return true if the value is inside the boundary
	 * @throws RuntimeException if the value is outside the boundary
	 */
	protected boolean checkBound(T value) {
		if(minimum != null && value.compareTo(minimum) < 0) {
			throw new RuntimeException(String.format(EXCEED_LOWER_BOUNDARY_EXCEPTION, value, minimum));
		}
		if(maximum != null && value.compareTo(maximum) > 0) {
			throw new RuntimeException(String.format(EXCEED_UPPER_BOUNDARY_EXCEPTION, value, maximum));
		}
		return true;
	}
}
