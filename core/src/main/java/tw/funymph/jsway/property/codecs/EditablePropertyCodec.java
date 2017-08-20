/* PropertyCodec.java created on May 3, 2012
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
package tw.funymph.jsway.property.codecs;

/**
 * A codec is a class that can decode the value of a property from the text
 * display on the editor and encode the value into the text.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public interface EditablePropertyCodec {

	/**
	 * Decode the value from the text.
	 * 
	 * @param text the display text of the editor
	 * @return the value
	 */
	Object decode(String text);

	/**
	 * Encode the value into the text.
	 * 
	 * @param value the value
	 * @return the text display on the editor
	 */
	String encode(Object value);
}
