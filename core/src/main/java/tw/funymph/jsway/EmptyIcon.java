/* EmptyIcon.java created on 2012/10/14
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

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

/**
 * A small empty area that can be used as a space while performing
 * complicated layout design.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
public class EmptyIcon implements Icon {

	private static final int GAP_ICON_SIZE = 1;

	private int iconWidth;
	private int iconHeight;

	/**
	 * Create a small icon which size is 1 x 1 as a gap between two components.
	 * 
	 * @return the small gap icon
	 */
	public static Icon createGapIcon() {
		return new EmptyIcon(GAP_ICON_SIZE);
	}

	/**
	 * Create an empty square icon which size is the given value.
	 * 
	 * @param size the icon size
	 */
	public EmptyIcon(int size) {
		this(size, size);
	}

	/**
	 * Create an empty icon which size is the given width x height.
	 * 
	 * @param width the icon width
	 * @param height the icon height
	 */
	public EmptyIcon(int width, int height) {
		iconWidth = width;
		iconHeight = height;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		// Do nothing
	}

	@Override
	public int getIconWidth() {
		return iconWidth;
	}

	@Override
	public int getIconHeight() {
		return iconHeight;
	}
}
