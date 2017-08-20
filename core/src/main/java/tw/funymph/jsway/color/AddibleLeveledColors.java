/* AddibleLeveledColors.java created on 2011/9/22
 *
 * Copyright (C) 2011. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.color;

import static java.util.Collections.sort;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;

import java.util.ArrayList;
import java.util.List;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.utils.ObjectUtilities;

/**
 * This class implement the {@link LeveledColors} so that the user can add a pair
 * of [double value, changeable color] into the object. All pairs will be sorted
 * based on the double value so that these values becomes a series of ranges like:
 * value1 - value2 - ... - valueN. When {@link #findLevel(double)} method is called,
 * the object will return the index so that index<i>th</i> value is larger than the
 * given value. Then, the user can use the index to retrieve the color.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class AddibleLeveledColors extends LeveledColors {

	private List<RatioColorPair> colors;
	private List<AddibleLeveledColorsListener> listeners;

	/**
	 * Construct an <code>AddibleLeveledColors</code> instance with the name.
	 * 
	 * @param name the name for the addible leveled colors
	 */
	public AddibleLeveledColors(String name) {
		this(name, simpleSupport(name));
	}

	/**
	 * Construct an <code>AddibleLeveledColors</code> instance with the name
	 * and the multi-language support.
	 * 
	 * @param name the name for the addible leveled colors
	 * @param support the multiple language support
	 * @throws NullPointerException if either the name or the multiple language support is null
	 * @since 1.1
	 */
	public AddibleLeveledColors(String name, MultiLanguageSupport support) {
		super(name, support);
		colors = new ArrayList<RatioColorPair>();
		listeners = new ArrayList<AddibleLeveledColorsListener>();
	}

	/**
	 * Add a color with the specific ratio. Note that the method will not check the
	 * duplication. If there is a the same ratio in the object, the new added color
	 * and the existing color will both exist in the object, but only one color
	 * will be retrieved.
	 * 
	 * @param ratio the ratio as key
	 * @param color the color to be added
	 */
	public void addColor(double ratio, ColorProperty color) {
		colors.add(new RatioColorPair(ratio, color));
		sort(colors);
		notifyColorAdded(ratio, color); 
	}

	/**
	 * Remove the color from this object. If there are two ratio paired with the
	 * same color, the smallest ratio-color pair is removed.
	 * 
	 * @param color the color to be removed
	 */
	public void removeColor(ColorProperty color) {
		RatioColorPair found = null;
		for(RatioColorPair pair : colors) {
			if(ObjectUtilities.equals(pair.getColor(), color)) {
				found = pair;
				colors.remove(pair);
				sort(colors);
				notifyColorRemoved(found);
				return;
			}
		}
	}

	/**
	 * Remove the color from this object. If there are two the same ratios in the
	 * object, the first found color in the sorted order is removed.
	 * 
	 * @param ratio the color paired with the ratio to be removed
	 */
	public void removeColor(double ratio) {
		RatioColorPair found = null;
		for(RatioColorPair pair : colors) {
			if(pair.getRatio() == ratio) {
				found = pair;
				colors.remove(pair);
				sort(colors);
				notifyColorRemoved(found);
				return;
			}
		}
	}

	/**
	 * Notify the registered listeners that a color is added.
	 * 
	 * @param ratio the added color ratio
	 * @param color the added color
	 */
	private void notifyColorAdded(double ratio, ColorProperty color) {
		for(AddibleLeveledColorsListener listener : listeners) {
			listener.colorAdded(ratio, color);
		}
	}

	/**
	 * Notify the registered listeners that a color is removed.
	 * 
	 * @param removed the removed ratio-color pair.
	 */
	private void notifyColorRemoved(RatioColorPair removed) {
		if(removed != null) {
			for(AddibleLeveledColorsListener listener : listeners) {
				listener.colorRemoved(removed.getRatio(), removed.getColor());
			}
		}
	}

	@Override
	public int getColorCount() {
		return colors.size();
	}

	@Override
	public int findLevel(double ratio) {
		int i = 0;
		for(; i < colors.size() - 1; i++) {
			RatioColorPair pair = colors.get(i);
			if(ratio < pair.getRatio()) {
				return i;
			}
		}
		return i;
	}

	@Override
	public ColorProperty getEditableColor(int index) {
		return colors.get(index).getColor();
	}
}
