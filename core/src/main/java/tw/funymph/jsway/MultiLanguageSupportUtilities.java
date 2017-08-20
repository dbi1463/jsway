/* MultiLanguageSupportUtilities.java created on 2012/10/7
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

import java.util.List;

/**
 * The helper methods to handle the registration of listeners.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class MultiLanguageSupportUtilities {

	/**
	 * Register the listener into a list of multiple language supports.
	 * 
	 * @param supports the multiple language supports
	 * @param listener the listener
	 */
	public static <T extends MultiLanguageSupport> void registerMultiLanguageSupport(List<T> supports, MultiLanguageSupportListener listener) {
		if(supports != null) {
			for(T support : supports) {
				support.addMultiLanguageSupportListener(listener);
			}
		}
	}

	/**
	 * Unregister the listener from a list of multiple language supports.
	 * 
	 * @param supports the multiple language supports
	 * @param listener the listener
	 */
	public static <T extends MultiLanguageSupport> void unregisterMultiLanguageSupport(List<T> supports, MultiLanguageSupportListener listener) {
		if(supports != null) {
			for(T support : supports) {
				support.removeMultiLanguageSupportListener(listener);
			}
		}
	}
}
