/* NumberUtilities.java created on May 1, 2012
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
package tw.funymph.jsway.utils;

/**
 * A set of utility methods to manipulate numeric data.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class NumberUtilities {

	public static final int DECIMAL_RADIX = 10;
	public static final int HEXADECIAML_RADIX = 16;

	private static final int DEFAULT_LENGTH = 2;
	private static final int DEFAULT_MAXIMUM = 255;

	/**
	 * Disable creation.
	 */
	private NumberUtilities() {}

	/**
	 * Get a string that presents the number in the common hex format.
	 * 
	 * @param number the number
	 * @return a hex string
	 */
	public static String toHexString(int number) {
		return toHexString(number, DEFAULT_MAXIMUM, DEFAULT_LENGTH);
	}

	/**
	 * Get a string that presents the number specified by the radix.
	 * 
	 * @param number the number
	 * @return a string
	 * @since 1.2
	 */
	public static String toString(int number, int radix) {
		return toString(number, DEFAULT_MAXIMUM, DEFAULT_LENGTH, radix);
	}

	/**
	 * Get a string that presents the number in the hex format with
	 * some constraints.
	 * 
	 * @param number the number
	 * @param maximum the maximum value to converted
	 * @param length the length of the return string 
	 * @return a hex string and its length must be the given length
	 */
	public static String toHexString(int number, int maximum, int length) {
		number = number > maximum? maximum : number;
		String string = Integer.toHexString(number).toUpperCase();
		String prefix = getZeroPrefix(length - string.length());
		return prefix + string;
	}

	/**
	 * Get a string that presents the number in the format with
	 * some constraints.
	 * 
	 * @param number the number
	 * @param maximum the maximum value to converted
	 * @param length the length of the returning string 
	 * @param radix the radix of the returning string
	 * @return a string and its length must be the given length
	 * @since 1.2
	 */
	public static String toString(int number, int maximum, int length, int radix) {
		number = number > maximum? maximum : number;
		String string = Integer.toString(number, radix);
		String prefix = getZeroPrefix(length - string.length());
		return prefix + string;
	}

	/**
	 * Get a string full filled with zero
	 * 
	 * @param length the length of string
	 * @return a string that is filled with zero in the given length
	 */
	public static String getZeroPrefix(int length) {
		String prefix = "";
		for(int index = 0; index < length; index++) {
			prefix += "0";
		}
		return prefix;
	}
}
