/* DefaultMemoryUsageColors.java created on 2011/9/22
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
package tw.funymph.jsway.memory;

import java.awt.Color;

import tw.funymph.jsway.color.LeveledColors;
import tw.funymph.jsway.property.ColorProperty;

/**
 * A default implementation of {@link LeveledColors} that offers three
 * colors for the three levels: NORMAL, WARNING, and ALARM. Note that the total
 * level count is unable to change, but the color can be changed.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class DefaultMemoryUsageColors extends LeveledColors {

	private static final int NORMAL_LEVEL = 0;
	private static final int WARNING_LEVEL = 1;
	private static final int ALARM_LEVEL = 2;
	private static final int DEFAULT_LEVEL_COUNT = 3;

	private static final double ALARM_LEVEL_RATIO = 0.75;
	private static final double WARNING_LEVEL_RATIO = 0.5;

	private static final Color ALARM_COLOR = new Color(207, 0, 0);
	private static final Color NORMAL_COLOR = new Color(0, 84, 166);
	private static final Color WARNING_COLOR = new Color(163, 98, 10);

	private static final String COLORS_NAME = "Memory Usage Colors";
	private static final String ALARM_COLOR_NAME = "The alarm color, when the usage > " + ALARM_LEVEL_RATIO;
	private static final String NORMAL_COLOR_NAME = "The noraml color, when the usage <= " + WARNING_LEVEL_RATIO;
	private static final String WARNING_COLOR_NAME = "The warning color, when the usage > " + WARNING_LEVEL_RATIO;

	private ColorProperty alarmColor = new ColorProperty(ALARM_COLOR_NAME, ALARM_COLOR);
	private ColorProperty normalColor = new ColorProperty(NORMAL_COLOR_NAME, NORMAL_COLOR);
	private ColorProperty warningColor = new ColorProperty(WARNING_COLOR_NAME, WARNING_COLOR);

	private double alarmRatio;
	private double warningRatio;

	/**
	 * Construct a <code>DefaultLeveledMemoryUsageColors</code> with the
	 * default warning ratio (0.5) and alarm ratio (0.75).
	 */
	public DefaultMemoryUsageColors() {
		this(WARNING_LEVEL_RATIO, ALARM_LEVEL_RATIO);
	}

	/**
	 * Construct a <code>DefaultLeveledMemoryUsageColors</code> instance by
	 * specifying the ratios to change the color to the warning color and the
	 * alarm color, respectively. 
	 * 
	 * @param ratioToWarn the ratio to change the color to the warning color.
	 * @param ratioToAlerm the ratio to change the color to the alarm color.
	 */
	public DefaultMemoryUsageColors(double ratioToWarn, double ratioToAlerm) {
		super(COLORS_NAME);
		warningRatio = ratioToWarn;
		alarmRatio = ratioToAlerm;
	}

	@Override
	public int getColorCount() {
		return DEFAULT_LEVEL_COUNT;
	}

	@Override
	public int findLevel(double usage) {
		if(usage > alarmRatio) {
			return ALARM_LEVEL;
		}
		else if(usage > warningRatio) {
			return WARNING_LEVEL;
		}
		else {
			return NORMAL_LEVEL;
		}
	}

	@Override
	public ColorProperty getEditableColor(int level) {
		switch(level) {
		case WARNING_LEVEL:
			return warningColor;
		case ALARM_LEVEL:
			return alarmColor;
		case NORMAL_LEVEL:
		default:
			return normalColor;
		}
	}
}
