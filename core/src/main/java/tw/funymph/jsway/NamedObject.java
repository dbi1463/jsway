/* NamedObject.java created on 2012/9/30
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
package tw.funymph.jsway;

/**
 * A class can implement this interface to offer a name as
 * the identification.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public interface NamedObject {

	/**
	 * Get the name of the object.
	 * 
	 * @return the name of the object
	 */
	String getName();
}
