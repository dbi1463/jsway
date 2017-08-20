/* ObjectUtilities.java created on 2012/10/3
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
 * A set of helpful methods to handle objects.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class ObjectUtilities {

	/**
	 * Returns true if the arguments are equal to each other and false otherwise. Consequently,
	 * if both arguments are null, true is returned and if exactly one argument is null, false is
	 * returned. Otherwise, equality is determined by using the equals method of the first argument.
	 * This method is the same as {@link java.util.Objects#equals(Object, Object)} in J2SE 7.
	 * 
	 * @param a an object
	 * @param b an object to be compared with a for equality
	 * @return if the arguments are equal to each other and false otherwise
	 */
	public static boolean equals(Object a, Object b) {
		return (a == b) || (a != null && a.equals(b));
	}

	/**
	 * Checks that the specified object reference is not null. This method is designed primarily
	 * for doing parameter validation in methods and constructors.
	 * 
	 * @param object the object reference to check for nullity
	 * @return the given object if not null
	 * @throws NullPointerException if given object is null
	 */
	public static <T> T requireNonNull(T object) {
		return requireNonNull(object, "");
	}

	/**
	 * Checks that the specified object reference is not null. This method is designed primarily
	 * for doing parameter validation in methods and constructors.
	 * 
	 * @param object the object reference to check for nullity
	 * @param message the null pointer exception message
	 * @return the given object if not null
	 * @throws NullPointerException if given object is null
	 */
	public static <T> T requireNonNull(T object, String message) {
		if(object == null) {
			throw new NullPointerException(message);
		}
		return object;
	}
}
