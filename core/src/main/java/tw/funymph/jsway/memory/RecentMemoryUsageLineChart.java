/* RecentMemoryUsageLineChart.java created on 2011/9/25
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

import static java.awt.Color.darkGray;
import static java.awt.Color.gray;
import static java.awt.Color.lightGray;
import static java.awt.Color.white;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB;
import static java.lang.Double.MAX_VALUE;
import static java.lang.Double.MIN_NORMAL;
import static java.lang.String.format;
import static tw.funymph.jsway.color.ColorUtilities.chageLeveledColorsEventSource;
import static tw.funymph.jsway.memory.MemoryUtilities.changeRecnetMemoryUsageHistory;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

import tw.funymph.jsway.color.LeveledColors;
import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;
import tw.funymph.jsway.utils.RecentHistory;

/**
 * A memory usage history line chart that shows the memory usage with the
 * time stamps.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class RecentMemoryUsageLineChart extends JPanel implements RecentMemoryUsageHistoryListener, EditablePropertyListener {

	private static final long serialVersionUID = 7102299714814210743L;

	private static final int GRIDS = 3;
	private static final int RATIO_OFFSET = 25;
	private static final int TEXT_X_OFFSET = 5;
	private static final int MAXIMUM_RATIO = 100;
	private static final int COORDINATION_OFFSET = 2;

	private static final float WIDTH_RATIO = 0.75f;
	private static final float HEIGHT_RATIO = 0.7f;
	private static final float DEFAULT_STROKE = 2.0f;

	private static final String RATIO_UNIT = " %";
	private static final String TIMESTAMP_FORMAT = "%1$tH:%1$tM:%1$tS";
	private static final String NULL_HISTORY_EXCEPTION = "Cannot set a null history";
	private static final String NULL_TEXTUAL_COLOR_EXCEPTION = "Cannot set a null textual color";
	private static final String NULL_FOREGROUND_COLOR_EXCEPTION = "Cannot set a null foreground color";
	private static final String NULL_BACKGROUND_COLOR_EXCEPTION = "Cannot set a null background color";
	private static final String TEXTUAL_COLOR_PROPERTY_NAME = "Recent Memory Usage Line Chart Textual Color";
	private static final String BACKGROUND_COLOR_PROPERTY_NAME = "Recent Memory Usage Line Chart Background Color";
	private static final String FOREGROUND_COLOR_PROPERTY_NAME = "Recent Memory Usage Line Chart Foreground Color";

	private int originX;
	private int originY;
	private int width;
	private int height;

	private LeveledColors usageColors;
	private ColorProperty textualColor;
	private ColorProperty foregroundColor;
	private ColorProperty backgroundColor;
	private RecentMemoryUsageHistory usageHistory;

	private Stroke stroke;
	private Stroke oldStroke;

	private boolean useColors;

	/**
	 * Construct a <code>RecentMemoryUsageLineChart</code> instance with the specified
	 * recent memory usage history.
	 * 
	 * @param history the history to be displayed
	 */
	public RecentMemoryUsageLineChart(RecentMemoryUsageHistory history) {
		this(history, null);
	}

	/**
	 * Construct a <code>RecentMemoryUsageLineChart</code> instance with the specified
	 * recent memory usage history and colors.
	 * 
	 * @param history the history to be displayed
	 * @param colors the colors that are used to display different memory usage
	 */
	public RecentMemoryUsageLineChart(RecentMemoryUsageHistory history, LeveledColors colors) {
		usageColors = colors;
		stroke = new BasicStroke(DEFAULT_STROKE);
		textualColor = new ColorProperty(TEXTUAL_COLOR_PROPERTY_NAME, gray);
		foregroundColor = new ColorProperty(FOREGROUND_COLOR_PROPERTY_NAME, gray);
		backgroundColor = new ColorProperty(BACKGROUND_COLOR_PROPERTY_NAME, white);
		textualColor.addEditablePropertyListener(this);
		backgroundColor.addEditablePropertyListener(this);
		foregroundColor.addEditablePropertyListener(this);
		setBackground(backgroundColor.getColor());
		setRecentMemoryUsageHistory(history);
	}

	/**
	 * Set the recent memory usage history to be displayed
	 * 
	 * @param history the history to be displayed
	 * @throws NullPointerException if the history is null
	 */
	public void setRecentMemoryUsageHistory(RecentMemoryUsageHistory history) {
		requireNonNull(history, NULL_HISTORY_EXCEPTION);
		changeRecnetMemoryUsageHistory(usageHistory, history, this);
		usageHistory = history;
		updateUI();
	}

	/**
	 * Get the textual color property. The change applied to the returned property
	 * will update the appearance of the line chart automatically.
	 * 
	 * @return the textual color property
	 */
	public ColorProperty getTextualColorProperty() {
		return textualColor;
	}

	/**
	 * Get the background color property. The change applied to the returned property
	 * will update the appearance of the line chart automatically.
	 * 
	 * @return the background color property
	 */
	public ColorProperty getBackgroundColorProperty() {
		return backgroundColor;
	}

	/**
	 * Get the foreground color property. The change applied to the returned property
	 * will update the appearance of the line chart automatically.
	 * 
	 * @return the foreground color property
	 */
	public ColorProperty getForegroundColorProperty() {
		return foregroundColor;
	}

	/**
	 * Get the colors that are used to display different memory usage.
	 * 
	 * @return the colors that are used to display different memory usage
	 */
	public LeveledColors getColors() {
		return usageColors;
	}

	/**
	 * Set the colors that are used to display different memory usage
	 * 
	 * @param colors the colors that are used to display different memory usage
	 */
	public void setColors(LeveledColors colors) {
		chageLeveledColorsEventSource(usageColors, colors, this);
		usageColors = colors;
		updateUI();
	}

	/**
	 * Set whether use the colors to draw the statistic data
	 * 
	 * @param useOrNot use colors to draw the statistic data or not 
	 */
	public void setUseColorsToDrawStatisticData(boolean useOrNot) {
		useColors = useOrNot;
	}

	/**
	 * Set the textual color. This is equal to call the {@link ColorProperty#setCurrentColor(Color)} method
	 * of the return value of {@link #getTextualColorProperty()}.
	 * 
	 * @param color the new textual color
	 * @throws NullPointerException if the given textual color is null
	 */
	public void setTextualColor(Color color) {
		requireNonNull(color, NULL_TEXTUAL_COLOR_EXCEPTION);
		textualColor.setCurrentColor(color);
	}

	/**
	 * Set the background color. This is equal to call the {@link ColorProperty#setCurrentColor(Color)} method
	 * of the return value of {@link #getBackgroundColorProperty()}.
	 * 
	 * @param color the new background color
	 * @throws NullPointerException if the given background color is null
	 */
	public void setBackgroundColor(Color color) {
		requireNonNull(color, NULL_BACKGROUND_COLOR_EXCEPTION);
		backgroundColor.setCurrentColor(color);
	}

	/**
	 * Set the foreground color. This is equal to call the {@link ColorProperty#setCurrentColor(Color)} method
	 * of the return value of {@link #getForegroundColorProperty()}.
	 * 
	 * @param color the new foreground color
	 * @throws NullPointerException if the given foreground color is null
	 */
	public void setForegroundColor(Color color) {
		requireNonNull(color, NULL_FOREGROUND_COLOR_EXCEPTION);
		foregroundColor.setCurrentColor(color);
	}

	@Override
	public void historyChanged(RecentMemoryUsageHistory history) {
		updateUI();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		setBackground(backgroundColor.getColor());
		updateUI();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g.create();
		g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_LCD_HRGB);
		width = (int)(getWidth() * WIDTH_RATIO);
		height = (int)(getHeight() * HEIGHT_RATIO);
		originX = (getWidth() - width) / 2;
		originY = (getHeight() - height) / 2;
		drawCoordinations(g2d);
		drawMemoryUsage(g2d);
		drawTimeStamp(g2d);
		g2d.dispose();
	}

	/**
	 * Draw the coordinations.
	 * 
	 * @param g the graphic system
	 */
	private void drawCoordinations(Graphics2D g) {
		// Draw the two axis.
		setStroke(g, stroke);
		g.setColor(foregroundColor.getColor());
		g.drawLine(originX, originY, originX, originY + height);
		g.drawLine(originX, originY + height, originX + width, originY + height);
		recoverStroke(g);

		// Draw the horizontal lines
		int xGridOffset = (int)((double)width / (double)(GRIDS + 1));
		int yGridOffset = (int)((double)height / (double)(GRIDS + 1));
		g.setColor(foregroundColor.getColor());
		for(int i = 0; i <= GRIDS; i++) {
			int x = originX + (xGridOffset * (i + 1));
			int y = originY + (yGridOffset * i);
			g.drawLine(originX, y, originX + width, y);
			g.drawLine(x, originY, x, originY + height);
		}

		// Draw the vertical lines
		g.setColor(textualColor.getColor());
		int textYOffset = (g.getFontMetrics().getAscent() / 2);
		for(int i = 0, ratio = MAXIMUM_RATIO; ratio >= 0; i++, ratio -= RATIO_OFFSET) {
			String string = ratio + RATIO_UNIT;
			int x = originX - (int)g.getFontMetrics().getStringBounds(string, g).getWidth() - TEXT_X_OFFSET;
			g.drawString(string, x, originY + (i * yGridOffset) + textYOffset);
		}
	}

	/**
	 * Draw the memory usage.
	 * 
	 * @param g2d the Java 2D graphics system
	 */
	private void drawMemoryUsage(Graphics2D g2d) {
		if(usageHistory != null && !usageHistory.getEvents().isEmpty()) {
			RecentHistory<MemoryUsageEvent> history = usageHistory.getEvents();
			double xPointOffset = (double)width / (double)history.getMaximumRecentEventCapacity();;
			int i = 0;
			int x1 = originX + COORDINATION_OFFSET, x2 = originX + COORDINATION_OFFSET;
			int y1 = originY + height, y2 = originY + (int)(height * (1 - history.get(0).getMemoryUsageRatio()));
			double total = 0, ratio = 0;
			double min = MAX_VALUE, max = MIN_NORMAL;
			setStroke(g2d, stroke);
			for(MemoryUsageEvent event : history) {
				ratio = event.getMemoryUsageRatio();
				min = Math.min(min, ratio);
				max = Math.max(max, ratio);
				total += ratio;
				g2d.setColor((usageColors != null)? usageColors.getColor(ratio) : darkGray);
				x1 = x2;
				y1 = y2;
				x2 = originX + (int)(xPointOffset * i) + COORDINATION_OFFSET;
				y2 = originY + (int)(height * (1 - ratio));
				g2d.drawLine(x1, y1, x2, y2);
				i++;
			}
			recoverStroke(g2d);
			double average = total / history.size();
			boolean canUseColors = useColors && (usageColors != null);
			drawStatisticData(g2d, min, canUseColors);
			drawStatisticData(g2d, average, canUseColors);
			drawStatisticData(g2d, max, canUseColors);
		}
	}

	/**
	 * Draw the time stamp on the horizontal axis.
	 * 
	 * @param g the Java 2D graphic system
	 */
	private void drawTimeStamp(Graphics2D g) {
		g.setColor(textualColor.getColor());
		int size = usageHistory.getEvents().size();
		int indexOffset = usageHistory.getEvents().getMaximumRecentEventCapacity() / (GRIDS + 1);
		int stampXOffset = width / (GRIDS + 1);
		int textY = originY + height + g.getFontMetrics().getHeight();
		for(int index = indexOffset, i = 1; index < size; index += indexOffset, i++) {
			MemoryUsageEvent event = usageHistory.getEvents().get(index);
			String stamp = format(TIMESTAMP_FORMAT, event.getTimeStamp());
			if(event != null) {
				int textX = originX + (stampXOffset * i) - (int)(g.getFontMetrics().getStringBounds(stamp, g).getWidth() / 2);
				g.drawString(stamp, textX, textY);
			}
		}
	}

	/**
	 * Draw the statistic data.
	 * 
	 * @param g the Java 2D graphic system
	 * @param data the statistic data
	 * @param useColors use the leveled colors or not
	 */
	private void drawStatisticData(Graphics2D g, double data, boolean useColors) {
		int y2 = originY + (int)(height * (1 - data));
		String string = (int)(data * MAXIMUM_RATIO) + RATIO_UNIT;
		g.setColor((useColors)? usageColors.getColor(data) : lightGray);
		g.drawLine(originX, y2, originX + width, y2);
		g.drawString(string, originX + width + TEXT_X_OFFSET, y2);
	}

	/**
	 * Set the new stroke and keep the old stroke for recover later.
	 * 
	 * @param g the Java 2D graphic system
	 * @param stroke the new stroke
	 */
	private void setStroke(Graphics2D g, Stroke stroke) {
		oldStroke = g.getStroke();
		g.setStroke(stroke);
	}

	/**
	 * Recover to the old stroke.
	 * 
	 * @param g the Java 2D graphic system
	 */
	private void recoverStroke(Graphics2D g) {
		g.setStroke(oldStroke);
	}
}
