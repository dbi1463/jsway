/* NumberPropertyCodec.java created on 2012/9/27
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
 * A codec that convert value between the number and string types.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class NumberPropertyCodec implements EditablePropertyCodec {

	@Override
	public Object decode(String text) {
		if(text.indexOf('.') >= 0) {
			return new Double(text);
		}
		else {
			return new Integer(text);
		}
	}

	@Override
	public String encode(Object value) {
		return value.toString();
	}
}
