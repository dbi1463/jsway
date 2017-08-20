/* SimpleChoice.java created on 2012/9/30
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

import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;

/**
 * A simple implementation of {@link Choice}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class SimpleChoice extends Choice {

	private Object optionValue;
	private MultiLanguageSupport multiLangSupport;

	/**
	 * Construct a simple choice instance with the value and use <code>value.toString()</code>
	 * as the display text.
	 * 
	 * @param value the value of the choice
	 */
	public SimpleChoice(Object value) {
		this(value, simpleSupport(value.toString()));
	}

	/**
	 * Construct a simple choice instance with the value and use the given multiple
	 * language support to get the display text.
	 * 
	 * @param value the value of the choice
	 * @param support the multiple language support
	 */
	public SimpleChoice(Object value, MultiLanguageSupport support) {
		optionValue = requireNonNull(value, "Cannot set a null value");
		multiLangSupport = requireNonNull(support, "");
	}

	@Override
	public Object getValue() {
		return optionValue;
	}

	@Override
	public String getDisplayText() {
		return multiLangSupport.getDisplayText();
	}

	@Override
	public void addMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		multiLangSupport.addMultiLanguageSupportListener(listener);
	}

	@Override
	public void removeMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		multiLangSupport.removeMultiLanguageSupportListener(listener);
	}
}
