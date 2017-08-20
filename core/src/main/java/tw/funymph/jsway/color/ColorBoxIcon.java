/* ColorBoxIcon.java created on 2011/9/16
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

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.lang.Math.min;
import static tw.funymph.jsway.utils.ImageUtilities.loadIcon;
import static tw.funymph.jsway.utils.ImageUtilities.makeBufferedImage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import tw.funymph.jsway.AutoUpdateImageIcon;
import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * An icon that will display a color box based on the current color in the
 * {@link ColorProperty}. The icon will be updated automatically when
 * the current color is changed.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class ColorBoxIcon extends AutoUpdateImageIcon implements EditablePropertyListener {

	private static final long serialVersionUID = 5260161198829149339L;

	private static int DEFAULT_ICON_WIDTH = 16;
	private static int DEFAULT_ICON_HEIGHT = 16;
	private static int COORDINATION_OFFSET = 1;

	private static double BIG_COLOR_BOX_SIZE_RATIO = 0.75d;
	private static double SMALL_COLOR_BOX_SIZE_RATIO = 0.9d;
	private static double COLOR_BOX_WIDTH_HEIGHT_RATIO = 0.3d;

	private ColorProperty displayColor;

	private boolean hasBaseIcon;

	/**
	 * Construct a <code>ColorBoxIcon</code> instance with the color property.
	 * 
	 * @param color the color property
	 */
	public ColorBoxIcon(ColorProperty color) {
		this(color, "");
	}

	/**
	 * Construct a <code>ColorBoxIcon</code> instance with a base icon.
	 * 
	 * @param color the color property
	 * @param baseIconPath the base icon path
	 */
	public ColorBoxIcon(ColorProperty color, String baseIconPath) {
		this(color, loadIcon(baseIconPath));
	}

	/**
	 * Construct a <code>ColorBoxIcon</code> instance with a base icon.
	 * 
	 * @param color the color property
	 * @param baseIcon the base icon
	 */
	public ColorBoxIcon(ColorProperty color, ImageIcon baseIcon) {
		hasBaseIcon = (baseIcon != null);
		displayColor = color;
		displayColor.addEditablePropertyListener(this);
		if(hasBaseIcon) {
			image = makeBufferedImage(baseIcon.getImage());
		}
		else {
			image = new BufferedImage(DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT, TYPE_4BYTE_ABGR);
		}
		updateImage();
	}

	@Override
	public String getDescription() {
		return displayColor.getName();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		updateImage();
	}

	/**
	 * Update the image in the icon.
	 */
	public void updateImage() {
		int maxSize = min(image.getWidth(), image.getHeight());
		if(hasBaseIcon) {
			int width = (int)(SMALL_COLOR_BOX_SIZE_RATIO * maxSize);
			int height = (int)(COLOR_BOX_WIDTH_HEIGHT_RATIO * width);
			int x = ((image.getWidth() - width) / 2) - COORDINATION_OFFSET;
			int y = image.getHeight() - height - COORDINATION_OFFSET;
			drawColorBox(x, y, width, height);
		}
		else {
			int size = (int)(BIG_COLOR_BOX_SIZE_RATIO * maxSize);
			int x = ((image.getWidth() - size) / 2) - COORDINATION_OFFSET;
			int y = ((image.getHeight() - size) / 2) - COORDINATION_OFFSET;
			drawColorBox(x, y, size, size);
		}
		invalidateContainer();
	}

	/**
	 * Draw a color box.
	 * 
	 * @param x the x location to draw the color box
	 * @param y the y location to draw the color box
	 * @param width the width of the color box
	 * @param height the height of the color box
	 */
	private void drawColorBox(int x, int y, int width, int height) {
		Graphics2D g2d = image.createGraphics();
		Color oldColor = g2d.getColor();
		g2d.setColor(displayColor.getColor());
		g2d.fillRect(x, y, width, height);
		g2d.setColor(Color.gray);
		g2d.drawRect(x, y, width, height);
		g2d.setColor(oldColor);
	}
}
