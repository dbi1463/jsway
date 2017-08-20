/* MACAddressProperty.java created on 2012/4/30
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
import static tw.funymph.jsway.utils.NumberUtilities.HEXADECIAML_RADIX;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.regex.PatternSyntaxException;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.utils.NumberUtilities;

/**
 * A class that extends {@link AbstractEditableProperty} to manages a MAC address.
 *
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class MACAddressProperty extends MultiplePartAddressProperty {

	private static final String NULL_SEPARATOR_EXCEPTION = "Cannot use a null separator to separate the address";

	public static final int LAST_FIELD_INDEX = 5;
	public static final int ADDRESS_FIELDS = 6;
	public static final int MAC_ADDRESS_RADIX = 16;

	public static final String DEFAULT_SEPARATOR = "-";

	/**
	 * Parse the specified address into six integer parts.
	 * 
	 * @param address the address to be parsed
	 * @param separator the address separator
	 * @return the address parts
	 * @throws NullPointerException if any argument is null
	 * @throws PatternSyntaxException if the regular expression's syntax is invalid
	 * @throws NumberFormatException if the string part does not contain an integer
	 */
	public static int[] parseAddress(String address, String separator) {
		return parseAddress(address, separator, HEXADECIAML_RADIX);
	}

	/**
	 * Parse the specified address into six integer parts.
	 * 
	 * @param address the address to be parsed
	 * @param separator the address separator
	 * @param radix the address radix
	 * @return the address parts
	 * @throws NullPointerException if any argument is null
	 * @throws PatternSyntaxException if the regular expression's syntax is invalid
	 * @throws NumberFormatException if the string part does not contain an integer
	 * @since 1.2
	 */
	public static int[] parseAddress(String address, String separator, int radix) {
		requireNonNull(address, NULL_VALUE_EXCEPTION);
		requireNonNull(separator, NULL_SEPARATOR_EXCEPTION);
		return parseAddressParts(address.split(separator), radix);
	}

	/**
	 * Parse the specified address parts into six integer parts.
	 * 
	 * @param parts the address parts to be parsed
	 * @return the address parts
	 * @throws NullPointerException if the parts is null
	 * @throws NumberFormatException if the string part does not contain an integer
	 */
	public static int[] parseAddressParts(String[] parts) {
		return parseAddressParts(parts, HEXADECIAML_RADIX);
	}

	/**
	 * Parse the specified address parts into six integer parts.
	 * 
	 * @param parts the address parts to be parsed
	 * @param radix the address radix
	 * @return the address parts
	 * @throws NullPointerException if the parts is null
	 * @throws NumberFormatException if the string part does not contain an integer
	 * @since 1.2
	 */
	public static int[] parseAddressParts(String[] parts, int radix) {
		requireNonNull(parts, NULL_VALUE_EXCEPTION);
		int[] results = new int[ADDRESS_FIELDS];
		for(int index = 0; index < ADDRESS_FIELDS; index++) {
			results[index] = parseInt(parts[index], radix);
		}
		return results;
	}

	/**
	 * Construct a <code>IPv4AddressProperty</code> with the specified name and initial
	 * address. Note that the address must be the hyphen-hexadecimal notation which
	 * consists of six decimal numbers, each ranging from 0 to 255, separated by hyphens,
	 * e.g., 01-23-45-67-89-AB. If the address is not the hyphen-hexadecimal notation,
	 * please use {@link #MACAddressProperty(String, String, String)} to specify the
	 * separator or {@link #MACAddressProperty(String, String, String, int)} to specify
	 * both the separator and the radix of the instance.
	 * 
	 * @param name the name of the property
	 * @param address the address presented by hyphen-hexadecimal notation
	 * @throws NullPointerException if any argument is null
	 */
	public MACAddressProperty(String name, String address) {
		this(name, parseAddress(address, DEFAULT_SEPARATOR, MAC_ADDRESS_RADIX), DEFAULT_SEPARATOR);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and initial
	 * address parts. Note that the size of the specified address part must be six, and
	 * each value ranging from 0 to 255. The output of {@link #getAddress(int[])} will
	 * be the hyphen-hexadecimal notation. If you want to get colon-hexadecimal notation,
	 * please use {@link #MACAddressProperty(String, int[], String)} to specify the
	 * separator or {@link #MACAddressProperty(String, int[], String, int)} to specify
	 * both the separator and the radix of the instance.
	 * 
	 * @param name the name of the property
	 * @param parts the address parts
	 * @throws NullPointerException if any argument is null
	 */
	public MACAddressProperty(String name, int[] parts) {
		this(name, parts, DEFAULT_SEPARATOR);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and initial
	 * address, and the separator to output the complete address. Note that the separator
	 * is used to parse the address, so that a wrong separator will cause wrong address parts.
	 * 
	 * @param name the name of the property
	 * @param address the address
	 * @param separator the separator to separate the address
	 * @throws NullPointerException if any argument is null
	 */
	public MACAddressProperty(String name, String address, String separator) {
		this(name, parseAddress(address, separator, MAC_ADDRESS_RADIX), separator);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and
	 * initial address parts, and the separator to output the complete address.
	 * Note that the size of the specified address part must be six, and each
	 * value ranging from 0 to 255.
	 * 
	 * @param name the name of the property
	 * @param parts the address parts
	 * @param separator the separator the combine the address parts
	 * @throws NullPointerException if any argument is null
	 */
	public MACAddressProperty(String name, int[] parts, String separator) {
		this(name, simpleSupport(name), parts, separator);
	}

	/**
	 * Construct a <code>IPv4AddressProperty</code> with the specified name and initial
	 * address. Note that the address must be the hyphen-hexadecimal notation which
	 * consists of six decimal numbers, each ranging from 0 to 255, separated by hyphens,
	 * e.g., 01-23-45-67-89-AB. If the address is not the hyphen-hexadecimal notation,
	 * please use {@link #MACAddressProperty(String, MultiLanguageSupport, String, String)}
	 * to specify the separator or {@link #MACAddressProperty(String, MultiLanguageSupport, String, String, int)}
	 * to specify both the separator and the radix of the instance.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param address the address presented by hyphen-hexadecimal notation
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	public MACAddressProperty(String name, MultiLanguageSupport support, String address) {
		this(name, support, parseAddress(address, DEFAULT_SEPARATOR, MAC_ADDRESS_RADIX), DEFAULT_SEPARATOR);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and initial
	 * address parts. Note that the size of the specified address part must be six, and
	 * each value ranging from 0 to 255. The output of {@link #getAddress(int[])} will
	 * be the hyphen-hexadecimal notation. If you want to get colon-hexadecimal notation,
	 * please use {@link #MACAddressProperty(String, int[], String)} to specify the separator
	 * or {@link #MACAddressProperty(String, int[], String, int)} to specify both the
	 * separator and the radix of the instance.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param parts the address parts
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	public MACAddressProperty(String name, MultiLanguageSupport support, int[] parts) {
		this(name, support, parts, DEFAULT_SEPARATOR);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and
	 * initial address, and the separator to output the complete address.
	 * Note that the separator is used to parse the address, so that a wrong separator
	 * will cause wrong address parts.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param address the address
	 * @param separator the separator to separate the address
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	public MACAddressProperty(String name, MultiLanguageSupport support, String address, String separator) {
		this(name, support, parseAddress(address, separator, MAC_ADDRESS_RADIX), separator);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and
	 * initial address parts, and the separator to output the complete address.
	 * Note that the size of the specified address part must be six, and each
	 * value ranging from 0 to 255.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param parts the address parts
	 * @param separator the separator the combine the address parts
	 * @throws NullPointerException if any argument is null
	 * @since 1.1
	 */
	public MACAddressProperty(String name, MultiLanguageSupport support, int[] parts, String separator) {
		super(name, support, parts, separator);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and
	 * initial address, the separator, and the radix to output the complete address.
	 * Note that the separator is used to parse the address, so that a wrong separator
	 * will cause wrong address parts.
	 * 
	 * @param name the name of the property
	 * @param address the address
	 * @param separator the separator to separate the address
	 * @param radix the radix to parse address
	 * @throws NullPointerException if any argument is null
	 * @since 1.2
	 */
	public MACAddressProperty(String name, String address, String separator, int radix) {
		this(name, parseAddress(address, separator, radix), separator);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and
	 * initial address, the separator, and the radix to output the complete address.
	 * Note that the separator is used to parse the address, so that a wrong separator
	 * will cause wrong address parts.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param address the address
	 * @param separator the separator to separate the address
	 * @param radix the radix to parse address
	 * @throws NullPointerException if any argument is null
	 * @since 1.2
	 */
	public MACAddressProperty(String name, MultiLanguageSupport support, String address, String separator, int radix) {
		this(name, support, parseAddress(address, separator, radix), separator);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name and
	 * initial address parts, and the separator to output the complete address.
	 * Note that the size of the specified address part must be six, and each
	 * value ranging from 0 to 255.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param parts the address parts
	 * @param separator the separator the combine the address parts
	 * @param radix the radix to parse address
	 * @throws NullPointerException if any argument is null
	 * @since 1.2
	 */
	public MACAddressProperty(String name, MultiLanguageSupport support, int[] parts, String separator, int radix) {
		super(name, support, parts, radix, separator);
	}

	/**
	 * Construct a <code>MACAddressProperty</code> with the specified name, initial address
	 * parts, the separator, and the radix.
	 * 
	 * @param name the name of the property
	 * @param parts the address parts
	 * @throws NullPointerException if any argument is null
	 * @since 1.2
	 */
	public MACAddressProperty(String name, int[] parts, String separator, int radix) {
		this(name, parts, DEFAULT_SEPARATOR);
	}

	@Override
	public String getAddress(int[] parts) {
		String value = "";
		for(int i = 0; i < parts.length; i++) { 
			value += NumberUtilities.toString(addressParts[i].getNumber().intValue(), addressRadix);
			value += (i != (parts.length -1))? getSeparator() : "";
		}
		return value;
	}

	@Override
	public int[] parse(String address) {
		return parseAddress(address, getSeparator(), addressRadix);
	}
}
