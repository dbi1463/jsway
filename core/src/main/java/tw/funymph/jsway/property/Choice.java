/* Choice.java created on 2012/9/28
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
package tw.funymph.jsway.property;

import tw.funymph.jsway.MultiLanguageSupport;

/**
 * The smallest item that can be selected in a {@link ChoicesProperty}. An item
 * contains a pair of the display text and the actual value. The text comes from
 * {@link MultiLanguageSupport#getDisplayText()}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public abstract class Choice implements MultiLanguageSupport {

	/**
	 * Get the value represented by the choice.
	 * 
	 * @return the value represented by the choice
	 */
	public abstract Object getValue();

	@Override
	public String toString() {
		return getDisplayText();
	}
}
