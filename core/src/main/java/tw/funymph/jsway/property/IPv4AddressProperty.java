/* IPv4AddressProperty.java created on 2012/4/30
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

import static java.lang.Integer.parseInt;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.regex.PatternSyntaxException;

import tw.funymph.jsway.MultiLanguageSupport;

/**
 * A class that extends {@link AbstractEditableProperty} to manages a IPv4 Address.
 *
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class IPv4AddressProperty extends MultiplePartAddressProperty {

	public static final int LAST_FIELD_INDEX = 3;
	public static final int ADDRESS_FIELDS = 4;

	public static final String DOT_SEPARATOR = "\\.";
	public static final String DOT_SUFFIX = ".";

	/**
	 * Parse the specified address into four integer parts.
	 * 
	 * @param address the address to be parsed
	 * @return the address parts
	 * @throws NullPointerException if the address is null
	 * @throws PatternSyntaxException if the regular expression's syntax is invalid
	 * @throws NumberFormatException if the string part does not contain an integer
	 * @since 1.1
	 */
	public static int[] parseAddress(String address) {
		requireNonNull(address, NULL_VALUE_EXCEPTION);
		return parseAddressParts(address.split(DOT_SEPARATOR));
	}

	/**
	 * Parse the specified address parts into four integer parts.
	 * 
	 * @param parts the address parts to be parsed
	 * @return the address parts
	 * @throws NullPointerException if the parts is null
	 * @throws NumberFormatException if the string part does not contain an integer
	 * @since 1.1
	 */
	public static int[] parseAddressParts(String[] parts) {
		requireNonNull(parts, NULL_VALUE_EXCEPTION);
		int[] results = new int[ADDRESS_FIELDS];
		for(int index = 0; index < ADDRESS_FIELDS; index++) {
			results[index] = parseInt(parts[index]);
		}
		return results;
	}

	/**
	 * Construct a <code>IPv4AddressProperty</code> with the specified name
	 * and initial address. Note that the address must be the dot-decimal notation
	 * which consists of four decimal numbers, each ranging from 0 to 255, separated
	 * by dots, e.g., "172.16.254.1".
	 * 
	 * @param name the name of the property
	 * @param address the address presented by dotted-decimal notation
	 * @throws NullPointerException is any argument is null
	 */
	public IPv4AddressProperty(String name, String address) {
		this(name, simpleSupport(name), parseAddress(address));
	}

	/**
	 * Construct a <code>IPv4AddressProperty</code> with the specified name
	 * and initial address. Note that the address must be the dot-decimal notation
	 * which consists of four decimal numbers, each ranging from 0 to 255, separated
	 * by dots, e.g., "172.16.254.1".
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param address the address presented by dotted-decimal notation
	 * @throws NullPointerException is any argument is null
	 * @since 1.1
	 */
	public IPv4AddressProperty(String name, MultiLanguageSupport support, String address) {
		this(name, support, parseAddress(address));
	}

	/**
	 * Construct a <code>IPv4AddressProperty</code> with the specified name
	 * and initial address parts. Note that the size of the specified address
	 * part must be four, and each value ranging from 0 to 255.
	 * 
	 * @param name the name of the property
	 * @param parts the address parts
	 * @throws NullPointerException is any argument is null
	 */
	public IPv4AddressProperty(String name, int[] parts) {
		this(name, simpleSupport(name), parts);
	}

	/**
	 * Construct a <code>IPv4AddressProperty</code> with the specified name
	 * and initial address parts. Note that the size of the specified address
	 * part must be four, and each value ranging from 0 to 255.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param parts the address parts
	 * @throws NullPointerException is any argument is null
	 * @since 1.1
	 */
	public IPv4AddressProperty(String name, MultiLanguageSupport support, int[] parts) {
		super(name, support, parts, DOT_SUFFIX);
	}

	@Override
	public String getAddress(int[] parts) {
		String value = "";
		for(int i = 0; i < parts.length - 1; i++)
			value += addressParts[i].getNumber().intValue() + getSeparator();
		value += addressParts[parts.length - 1].getNumber().intValue();
		return value;
	}

	@Override
	public int[] parse(String address) {
		return parseAddress(address);
	}
}
