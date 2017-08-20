/* MultiLanguageSupport.java created on 2012/9/29
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
 * A class can implement this interface to support changing display text to multiple
 * languages at runtime.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public interface MultiLanguageSupport {

	/**
	 * The type of the event that is notified when the display text is updated.
	 */
	static final String KEY_DISPLAY_TEXT = "MultiLanguageSupport.displayText";

	/**
	 * Get the display text for the object. The display text is usually
	 * more friendly to understand to the user.
	 * 
	 * @return the display name of the object
	 */
	String getDisplayText();

	/**
	 * Add the specified listener into the notification list that will be notified
	 * when display text is updated.
	 * 
	 * @param listener the listener to be added
	 */
	void addMultiLanguageSupportListener(MultiLanguageSupportListener listener);

	/**
	 * Remove the specified listener from the notification list that will be notified
	 * when display text is updated.
	 * 
	 * @param listener the listener to be removed
	 */
	void removeMultiLanguageSupportListener(MultiLanguageSupportListener listener);
}
