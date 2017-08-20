/* ColorPropertyCodec.java created on 2012/9/27
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

import static tw.funymph.jsway.color.ColorUtilities.fromHexString;
import static tw.funymph.jsway.color.ColorUtilities.toHexString;

import java.awt.Color;

/**
 * A codec that converts value between the color and string types.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class ColorPropertyCodec implements EditablePropertyCodec {

	@Override
	public Object decode(String text) {
		return fromHexString(text);
	}

	@Override
	public String encode(Object value) {
		return toHexString((Color)value);
	}
}
