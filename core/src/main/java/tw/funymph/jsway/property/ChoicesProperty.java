/* ChoicesProperty.java created on Jun 5, 2012
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

import static tw.funymph.jsway.MultiLanguageSupportUtilities.registerMultiLanguageSupport;
import static tw.funymph.jsway.MultiLanguageSupportUtilities.unregisterMultiLanguageSupport;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.util.List;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;

/**
 * A class that extends {@link AbstractEditableProperty} to manages
 * a selection from multiple choices.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class ChoicesProperty extends AbstractEditableProperty implements MultiLanguageSupportListener {

	private static final String NULL_CHOICES_SET_EXCEPTION = "Cannot set a null set of choices";
	private static final String NON_EXISTING_VALUE_EXCEPTION = "Cannot set a non-existing value";

	public static final String KEY_CHOICES = "ChoicesProperty.choices";

	private Object selectedValue;
	private List<Choice> allChoices;

	/**
	 * Construct a <code>ChoicesProperty</code> instance.
	 * 
	 * @param name the name of the property
	 * @param value the current selection
	 * @param choices the available choices
	 * @throws NullPointerException if any argument is null or the given value does not exist in the choices
	 */
	public ChoicesProperty(String name, Object value, List<Choice> choices) {
		this(name, simpleSupport(name), value, choices);
	}

	/**
	 * Construct a <code>ChoicesProperty</code> instance.
	 * 
	 * @param name the name of the property
	 * @param support the multiple language support
	 * @param value the current selection
	 * @param choices the available choices
	 * @throws NullPointerException if any argument is null or the given value does not exist in the choices
	 * @since 1.1
	 */
	public ChoicesProperty(String name, MultiLanguageSupport support, Object value, List<Choice> choices) {
		super(name, support);
		setChoices(choices);
		setValue(value);
	}

	/**
	 * Get all the choices in the property.
	 * 
	 * @return all choices
	 */
	public List<Choice> getChoices() {
		return allChoices;
	}

	/**
	 * Change the choices.
	 * 
	 * @param choices the new choices
	 */
	public void setChoices(List<Choice> choices) {
		requireNonNull(choices, NULL_CHOICES_SET_EXCEPTION);
		if(!isSameChoices(allChoices, choices)) {
			List<? extends Choice> oldChoices = allChoices;
			unregisterMultiLanguageSupport(allChoices, this);
			allChoices = choices;
			registerMultiLanguageSupport(allChoices, this);
			notifyEditablePropertyListeners(KEY_CHOICES, allChoices, oldChoices);
		}
	}

	/**
	 * Get the selected choice.
	 * 
	 * @return the choice
	 */
	public Choice getSelectedChoice() {
		return findChoice(selectedValue);
	}

	@Override
	public Object getValue() {
		return selectedValue;
	}

	@Override
	public void setValue(Object value) {
		requireNonNull(value, NULL_VALUE_EXCEPTION);
		requireNonNull(findChoice(value), NON_EXISTING_VALUE_EXCEPTION);
		if(!value.equals(selectedValue)) {
			Object oldValue = selectedValue;
			selectedValue = value;
			notifyEditablePropertyListeners(KEY_VALUE, selectedValue, oldValue);
		}
	}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		notifyEditablePropertyListeners(KEY_CHOICES, allChoices, allChoices);
	}

	/**
	 * Find the choice whose value equals to the give value.
	 * 
	 * @param value the value to find
	 * @return the choice whose value equals to the give value; null if not found
	 */
	private Choice findChoice(Object value) {
		for(Choice choice : allChoices) {
			if(value != null && value.equals(choice.getValue())) {
				return choice;
			}
		}
		return null;
	}

	/**
	 * Check whether the two given sets of choices are the same.
	 * 
	 * @param choices1 a set of choices
	 * @param choices2 another set of choices
	 * @return true if two given sets of choices are the same
	 */
	private boolean isSameChoices(List<? extends Choice> choices1, List<? extends Choice> choices2) {
		if((choices1 != null && choices2 != null) && choices1.size() == choices2.size()) {
			boolean same = true;
			for(int index = 0; index < choices1.size() && same; index++) {
				same &= choices1.get(index).equals(choices2.get(index));
			}
			return same;
		}
		return false;
	}
}
