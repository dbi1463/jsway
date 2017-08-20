/* MultiLanguageSupportListener.java created on 2012/9/30
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
 * A class can implement this interface and register as a listener to be
 * notified when a display text is changed.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public interface MultiLanguageSupportListener {

	/**
	 * Invoke when the display text is changed.
	 * 
	 * @param source the source of the event
	 */
	void displayTextChanged(MultiLanguageSupport source);
}
