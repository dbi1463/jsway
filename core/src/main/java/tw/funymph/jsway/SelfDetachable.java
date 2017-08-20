/* SelfDetachable.java created on 2012/10/13
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
 * Any object that can implements this interface to provide a mechanism to
 * detach the dependency between itself (listener) and all registered subjects.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public interface SelfDetachable {

	/**
	 * Detach the dependency between itself (listener) and all registered subjects.
	 */
	void detach();
}
