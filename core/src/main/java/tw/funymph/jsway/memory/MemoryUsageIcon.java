/* MemoryUsageIcon.java created on 2011/9/23
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

import static java.awt.Color.black;
import static java.awt.Color.green;
import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;
import static java.lang.String.format;
import static tw.funymph.jsway.color.ColorUtilities.chageLeveledColorsEventSource;
import static tw.funymph.jsway.memory.MemoryUsageMonitor.getInstance;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JLabel;

import tw.funymph.jsway.AutoUpdateImageIcon;
import tw.funymph.jsway.color.LeveledColors;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * A small memory usage icon.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class MemoryUsageIcon extends AutoUpdateImageIcon implements MemoryUsageListener, EditablePropertyListener {

	private static final long serialVersionUID = -6882429860465936880L;

	private static final String DESCRIPTION_TEMPLATE = "Memory Usage: %1$.0f%%";
	private static final String NULL_SOURCE_EXCEPTION = "Cannot set a null source";

	private static int GRIDS = 4;
	private static int BORDERS = 2;
	private static int PERCENTAGES = 100;
	private static int COORDINATION_OFFSET = 1;
	private static int DEFAULT_ICON_WIDTH = 13;
	private static int DEFAULT_ICON_HEIGHT = 13;

	private double ratio;

	private LeveledColors colors;
	private MemoryUsageNotifier source;

	/**
	 * Construct a default <code>MemoryUsageIcon</code> instance.
	 */
	public MemoryUsageIcon() {
		this(getInstance());
	}

	/**
	 * Construct a <code>MemoryUsageIcon</code> instance by specifying the
	 * {@link MemoryUsageNotifier} source to receive memory usage update events.
	 * 
	 * @param source the memory usage event source
	 */
	public MemoryUsageIcon(MemoryUsageNotifier source) {
		this(DEFAULT_ICON_WIDTH, DEFAULT_ICON_HEIGHT, source, null);
	}

	/**
	 * Construct a <code>MemoryUsageIcon</code> instance by specifying the
	 * width, height, {@link MemoryUsageNofifier}, and {@link LeveledColors}
	 * to customize the icon view.
	 * 
	 * @param width the width of the icon
	 * @param height the height of the icon
	 * @param source the memory usage event source
	 * @param colors the colors to customize the icon view
	 */
	public MemoryUsageIcon(int width, int height, MemoryUsageNotifier source, LeveledColors colors) {
		setLeveledColors(colors);
		setIconSize(width, height);
		setMemoryUsageNotifier(source);
	}

	/**
	 * Replace the memory usage event sources. Note that if the previous source
	 * is not null, the icon will unregistered itself from the previous source.
	 * 
	 * @param newSource the new memory usage event source
	 * @throws NullPointerException if the given source is null
	 */
	public void setMemoryUsageNotifier(MemoryUsageNotifier newSource) {
		requireNonNull(newSource, NULL_SOURCE_EXCEPTION);
		if(source != newSource) {
			if(source != null) {
				source.removeMemoryUsageUpdateListener(this);
			}
			source = newSource;
			source.addMemoryUsageUpdateListener(this);
			memoryUsageUpdated(source.getLastNotifiedEvent());
		}
	}

	/**
	 * Set the leveled colors. Note that if the previous leveled colors is not
	 * null, the icon will unregistered itself from the previous colors.
	 * 
	 * @param newColors the new leveled colors
	 * @throws NullPointerException if the given color chips is null
	 */
	public void setLeveledColors(LeveledColors newColors) {
		chageLeveledColorsEventSource(colors, newColors, this);
		colors = newColors;
	}

	@Override
	public String getDescription() {
		return format(DESCRIPTION_TEMPLATE, ratio * PERCENTAGES);
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		if(c instanceof JLabel) {
			((JLabel)c).setToolTipText(getDescription());
		}
		super.paintIcon(c, g, x, y);
	}

	@Override
	public void memoryUsageUpdated(MemoryUsageEvent event) {
		ratio = event.getMemoryUsageRatio();
		updateImage();
		invalidateContainer();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		updateImage();
		invalidateContainer();
	}

	/**
	 * Set the new icon size.
	 * 
	 * @param width the width of the icon
	 * @param height the height of the icon
	 */
	private void setIconSize(int width, int height) {
		image = new BufferedImage(width, height, TYPE_4BYTE_ABGR);
	}

	/**
	 * Update the icon image.
	 */
	private void updateImage() {
		Graphics2D g2d = (Graphics2D)image.getGraphics();
		Color oldColor = g2d.getColor();

		// Draw background
		g2d.setColor(black);
		g2d.fillRect(0, 0, image.getWidth(), image.getHeight());

		Color color = (colors == null)? green : colors.getColor(ratio);
		g2d.setColor(color);
		drawGrids(g2d);
		drawMemoryUsage(g2d);
		g2d.setColor(oldColor);
	}

	/**
	 * Draw the grids.
	 * 
	 * @param g2d the Java 2D graphics system
	 */
	private void drawGrids(Graphics2D g2d) {
		// Draw borders
		int x1 = 0;
		int y1 = 0;
		int x2 = image.getWidth() - COORDINATION_OFFSET;
		int y2 = image.getHeight() - COORDINATION_OFFSET;
		g2d.drawLine(0, 0, 0, y2);
		g2d.drawLine(0, 0, x2, 0);
		g2d.drawLine(x2, 0, x2, y2);
		g2d.drawLine(0, y2, x2, y2);

		// Draw grids
		double width = image.getWidth() - BORDERS - (GRIDS - COORDINATION_OFFSET);
		double height = image.getHeight() - BORDERS - (GRIDS - COORDINATION_OFFSET);
		double xOffset = width / GRIDS;
		double yOffset = height / GRIDS;
		for(int i = 1; i < GRIDS; i++) {
			int x = (int)(x1 + (xOffset * i) + i);
			int y = (int)(y1 + (yOffset * i) + i);
			g2d.drawLine(x, 0, x, x2);
			g2d.drawLine(0, y, y2, y);
		}
	}

	/**
	 * Draw the memory usage block.
	 * 
	 * @param g2d the Java 2D graphics system
	 */
	private void drawMemoryUsage(Graphics2D g2d) {
		int height = (int)(ratio * (image.getHeight() - BORDERS));
		int width = image.getWidth() - BORDERS;
		int y = image.getHeight() - height - COORDINATION_OFFSET;
		g2d.fillRect(1, y, width, height);
	}
}
