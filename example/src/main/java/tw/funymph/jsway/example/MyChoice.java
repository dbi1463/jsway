/* MyChoice.java created on 2012/9/30
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
package tw.funymph.jsway.example;

import java.util.Vector;

import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.property.Choice;

/**
 * @author Pin-Ying Tu
 * @version 1.1
 * @since 1.1
 */
public class MyChoice extends Choice {

	private boolean _english;

	private Number _value;
	private Vector<MultiLanguageSupportListener> _listeners;

	public MyChoice(int value) {
		_value = new Integer(value);
		_english = true;
		_listeners = new Vector<MultiLanguageSupportListener>();
	}

	public void setDisplayInEnglish(boolean english) {
		_english = english;
		notifyMultiLanguageSupportListeners();
	}

	@Override
	public String getDisplayText() {
		return (_english? "Choice " : "選擇") + _value.intValue();
	}

	@Override
	public void addMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		if(!_listeners.contains(listener)) {
			_listeners.add(listener);
		}
	}

	@Override
	public void removeMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
		_listeners.remove(listener);
	}

	@Override
	public Object getValue() {
		return _value;
	}

	private void notifyMultiLanguageSupportListeners() {
		for(MultiLanguageSupportListener listener : _listeners) {
			listener.displayTextChanged(this);
		}
	}
}
