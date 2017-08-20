/* SimpleMultiLanguageSupport.java created on 2012/9/30
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

import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * A simple implementation of {@link MultiLanguageSupport}. The display text
 * can be updated by calling {@link #setDisplayText(String)} method and this
 * method will notifiy all registered listeners.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class SimpleMultiLanguageSupport implements MultiLanguageSupport {

	private String displayText;
	private List<MultiLanguageSupportListener> listeners;

	/**
	 * A short-cut of creating a <code>SimpleMultiLanguageSupport</code> instance.
	 * 
	 * @param text the default display text
	 * @return a simple multi-language support
	 * @since 1.2
	 */
	public static SimpleMultiLanguageSupport simpleSupport(String text) {
		return new SimpleMultiLanguageSupport(text);
	}

	/**
	 * Construct a <code>SimpleMultiLanguageSupport</code> instance with
	 * the default display text.
	 * 
	 * @param text the default display text
	 * @throws NullPointerException if the display text is null
	 */
	public SimpleMultiLanguageSupport(String text) {
		displayText = requireNonNull(text);
		listeners = new ArrayList<MultiLanguageSupportListener>();
	}

	@Override
	public String getDisplayText() {
		return displayText;
	}

	/**
	 * Update the display text and notify the registered listeners if the
	 * given display text is different to the current display text.
	 * 
	 * @param text the new display text
	 */
	public void setDisplayText(String text) {
		if(!ObjectUtilities.equals(text, displayText)) {
			displayText = text;
			notifyDisplayTextChanged();
		}
	}

	@Override
	public void addMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		if(!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	@Override
	public void removeMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Notify all registered listeners.
	 */
	protected void notifyDisplayTextChanged() {
		for(MultiLanguageSupportListener listener : listeners) {
			listener.displayTextChanged(this);
		}
	}
}
