/* BooleanPropertyCodec.java created on 2012/9/27
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

import static java.lang.Boolean.parseBoolean;
import static java.lang.String.valueOf;

/**
 * A codec that converts value between the boolean and string types.
 *  
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class BooleanPropertyCodec implements EditablePropertyCodec {

	@Override
	public Object decode(String text) {
		return parseBoolean(text);
	}

	@Override
	public String encode(Object value) {
		return valueOf((Boolean)value);
	}
}
