/* RatioColorPair.java created on 2012/04/27
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
package tw.funymph.jsway.color;

import tw.funymph.jsway.property.ColorProperty;

/**
 * A helper class that is used to sort the changeable colors.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
final class RatioColorPair implements Comparable<RatioColorPair> {

	private double lowerBound;
	private ColorProperty displayColor;

	/**
	 * Construct a ratio-color pair
	 * 
	 * @param ratio the ratio
	 * @param color the color
	 */
	public RatioColorPair(double ratio, ColorProperty color) {
		lowerBound = ratio;
		displayColor = color;
	}

	@Override
	public int compareTo(RatioColorPair rhs) {
		return (rhs.lowerBound == lowerBound)? 0 : (rhs.lowerBound > lowerBound)? -1 : 1;
	}

	/**
	 * Get the ratio in the pair
	 * 
	 * @return the ratio
	 */
	public double getRatio() {
		return lowerBound;
	}

	/**
	 * Get the color in the pair
	 * 
	 * @return the color
	 */
	public ColorProperty getColor() {
		return displayColor;
	}
}
