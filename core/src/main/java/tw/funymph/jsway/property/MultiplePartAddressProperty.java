/* MultiplePartAddressProperty.java created on May 1, 2012
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

import static tw.funymph.jsway.utils.NumberUtilities.DECIMAL_RADIX;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A base class that handle the multiple-part address that is common used in
 * IPv4, IPv6, or MAC address.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
abstract class MultiplePartAddressProperty extends AbstractEditableProperty implements EditablePropertyListener {

	private static final String FIELD_NAME_PREFIX = "Field ";
	private static final String ILLEGAL_ADDRESS_EXCEPTION = "Illegal address format!";

	private static final Integer DEFAULT_ADDRESS_STEP = 1;

	public static final Integer DEFAULT_MINIMUM = 0;
	public static final Integer DEFAULT_MAXIMUM = 255;

	protected String completeAddress;
	protected String addressSeparator;
	protected int addressRadix;
	protected NumberProperty<Integer>[] addressParts;

	/**
	 * Construct a <code>MultiplePartAddressProperty</code> instance with the name, the address parts,
	 * the default maximum value (255) of each part, and the separator by its children.
	 * 
	 * @param name the name of the address property
	 * @param support the multiple language support
	 * @param parts the address parts
	 * @param separator the separator
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	protected MultiplePartAddressProperty(String name, MultiLanguageSupport support, int[] parts, String separator) {
		this(name, support, parts, DEFAULT_MAXIMUM, separator);
	}

	/**
	 * Construct a <code>MultiplePartAddressProperty</code> instance with the name, the address parts,
	 * the maximum value of each part, and the separator by its children.
	 * 
	 * @param name the name of the address property
	 * @param support the multiple language support
	 * @param parts the address parts
	 * @param partMaximum the maximum value of each part
	 * @param separator the separator
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	protected MultiplePartAddressProperty(String name, MultiLanguageSupport support, int[] parts, int partMaximum, String separator) {
		this(name, support, parts, DEFAULT_MAXIMUM, DECIMAL_RADIX, separator);
	}

	/**
	 * Construct a <code>MultiplePartAddressProperty</code> instance with the name, the address parts,
	 * the maximum value of each part, and the separator by its children.
	 * 
	 * @param name the name of the address property
	 * @param support the multiple language support
	 * @param parts the address parts
	 * @param partMaximum the maximum value of each part
	 * @param separator the address separator
	 * @param radix the address part radix
	 * @throws NullPointerException if any argument is null
	 * @since 1.2
	 */
	@SuppressWarnings("unchecked")
	protected MultiplePartAddressProperty(String name, MultiLanguageSupport support, int[] parts, int partMaximum, int radix, String separator) {
		super(name, support);
		addressRadix = radix;
		addressSeparator = requireNonNull(separator, "Cannot use a null as the address separator");
		requireNonNull(parts, "Cannot use a null array as the address parts");
		addressParts = new NumberProperty[parts.length];
		for(int index = 0; index < parts.length; index++) {
			String fieldName = FIELD_NAME_PREFIX + index;
			addressParts[index] = new NumberProperty<Integer>(fieldName, parts[index], DEFAULT_MINIMUM, partMaximum, DEFAULT_ADDRESS_STEP);
			addressParts[index].addEditablePropertyListener(this);
		}
		completeAddress = getAddress(getAddressParts());
	}

	/**
	 * Get the address parts.
	 * 
	 * @return the address parts
	 */
	public int[] getAddressParts() {
		int[] parts = new int[addressParts.length];
		for(int index = 0; index < addressParts.length; index++) {
			parts[index] = addressParts[index].getNumber().intValue();
		}
		return parts;
	}

	/**
	 * Set the address parts. Note that if the size of the specified parts does not fit
	 * the expected size, it will throw illegal address exception.
	 * 
	 * @param parts the address parts
	 * @throws RuntimeException if the size of the specified parts is not legal
	 */
	public void setAddressParts(int[] parts) {
		if(parts.length == addressParts.length) {
			String oldAddress = completeAddress;
			String newAddress = getAddress(parts);
			if(!ObjectUtilities.equals(oldAddress, newAddress)) {
				for(int index = 0; index < addressParts.length; index++) {
					addressParts[index].setNumber(parts[index]);
				}
				completeAddress = newAddress;
				notifyEditablePropertyListeners(KEY_VALUE, completeAddress, oldAddress);
			}
		}
		else {
			throw new RuntimeException(ILLEGAL_ADDRESS_EXCEPTION);
		}
	}

	/**
	 * Get the editable address parts.
	 * 
	 * @return the editable address parts
	 */
	public NumberProperty<Integer>[] getEditableAddressParts() {
		return addressParts;
	}

	/**
	 * Get the separator to separate the address into address parts.
	 * 
	 * @return the separator
	 */
	public String getSeparator() {
		return addressSeparator;
	}

	/**
	 * Get the radix to parse/generate address part text.
	 * 
	 * @return the radix
	 * @since 1.2
	 */
	public int getRadix() {
		return addressRadix;
	}

	/**
	 * Set the radix to parse/generate address part text.
	 * 
	 * @param radix the radix
	 * @since 1.2
	 */
	public void getRadix(int radix) {
		addressRadix = radix;
	}

	@Override
	public Object getValue() {
		return completeAddress;
	}

	@Override
	public void setValue(Object value) {
		setAddressParts(parse((String)value));
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		String newAddress = getAddress(getAddressParts());
		if(KEY_VALUE.equals(event.getKey()) && !ObjectUtilities.equals(completeAddress, newAddress)) {
			String oldAddress = completeAddress;
			completeAddress = newAddress;
			notifyEditablePropertyListeners(KEY_VALUE, completeAddress, oldAddress);
		}
	}

	/** Get the complete address.
	 * 
	 * @return the complete address
	 * @since 1.1
	 */
	public abstract String getAddress(int[] parts);

	/** Parse the specified address into integer parts.
	 * 
	 * @param address the address to be parsed
	 * @return the address parts
	 * @throws NullPointerException if the address is null
	 * @throws PatternSyntaxException if the regular expression's syntax is invalid
	 * @throws NumberFormatException if the string part does not contain an integer
	 * @since 1.1
	 */
	public abstract int[] parse(String address);
}
