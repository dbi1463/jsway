/* OnOffSwitch.java created on 2011/9/30
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
package tw.funymph.jsway.property.editor;

import static java.awt.Color.gray;
import static java.awt.Color.lightGray;
import static java.awt.Color.white;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.lang.Boolean.valueOf;
import static java.lang.Math.abs;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;
import static tw.funymph.jsway.property.BooleanProperty.DEFAULT_OFF_TEXT;
import static tw.funymph.jsway.property.BooleanProperty.DEFAULT_ON_TEXT;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.LineMetrics;

import javax.swing.JComponent;
import javax.swing.JPanel;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.property.BooleanProperty;
import tw.funymph.jsway.property.BooleanPropertyWrapper;
import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * A GUI switch to set true/false of a {@link BooleanProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class OnOffSwitch extends JPanel implements EditablePropertyListener, PropertyEditor, MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -3339121190040194452L;

	private static final int DEFAULT_WIDTH = 75;
	private static final int DEFAULT_HEIGHT = 25;

	private static final String NULL_VALUE_EXCEPTION = "Cannot create an editor without value!";

	private static final double THUMB_WIDTH_RATIO = 0.45;
	private static final double THUMB_HEIGHT_RATIO = 1.0;

	private static final Color ON_COLOR = new Color(64, 96, 255);
	private static final Color OFF_COLOR = new Color(128, 128, 128);

	private static final int EXTENT = 6;
	private static final int INSET = 2;
	private static final int MIN_ARC_WIDTH = 6;
	private static final int MIN_ARC_HEIGHT = 6;
	private static final int DEFAULT_FONT_SIZE = 14;

	private int movedX;
	private int switchX;
	private int switchY;
	private int pressedX;
	private int thumbX;
	private int thumbY;
	private int thumbWidth;
	private int thumbHeight;
	private int switchWidth;
	private int switchHeight;

	private boolean pressed;

	private BooleanProperty onOffProperty;

	/**
	 * Construct an <code>OnOffSwitch</code> instance with the default status and
	 * default display texts.
	 */
	public OnOffSwitch() {
		this(false, DEFAULT_ON_TEXT, DEFAULT_OFF_TEXT);
	}

	/**
	 * Construct an <code>OnOffSwitch</code> instance with the specified status and
	 * default display texts.
	 * 
	 * @param value the default status of the switch.
	 */
	public OnOffSwitch(boolean value) {
		this(value, DEFAULT_ON_TEXT, DEFAULT_OFF_TEXT);
	}

	/**
	 * Construct an <code>OnOffSwitch</code> instance with the specified value and
	 * display texts.
	 * 
	 * @param value the default status of the switch.
	 * @param onText the text to be displayed when the property is on (true)
	 * @param offText the text to be displayed when the property is off (false)
	 */
	public OnOffSwitch(boolean value, String onText, String offText) {
		this(new BooleanProperty("", onText, offText, value));
	}

	/**
	 * Construct an <code>OnOffSwitch</code> instance with the specified {@link BooleanProperty}. and
	 * default display texts. The switch will directly update the property.
	 * 
	 * @param value the property to display and be updated.
	 */
	public OnOffSwitch(BooleanProperty value) {
		onOffProperty = requireNonNull(value, NULL_VALUE_EXCEPTION);
		movedX = 0;
		setMinimumSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);
		onOffProperty.addEditablePropertyListener(this);
	}

	/**
	 * Construct an <code>OnOffSwitch</code> instance with the specified {@link BooleanProperty}. and
	 * default display texts. The switch will directly update the property.
	 * 
	 * @param value the property to display and be updated.
	 * @param onText the text to be displayed when the property is on (true)
	 * @param offText the text to be displayed when the property is off (false)
	 */
	public OnOffSwitch(BooleanProperty value, String onText, String offText) {
		this(new BooleanPropertyWrapper(value, value, simpleSupport(onText), simpleSupport(offText)));
	}

	/**
	 * Construct an <code>OnOffSwitch</code> instance with the specified {@link BooleanProperty}. and
	 * default display texts. The switch will directly update the property.
	 * 
	 * @param value the property to display and be updated.
	 * @param support the multi-language support for the property name
	 */
	public OnOffSwitch(BooleanProperty value, MultiLanguageSupport support) {
		this(new BooleanPropertyWrapper(value, support));
	}

	/**
	 * Construct an <code>OnOffSwitch</code> instance with the specified {@link BooleanProperty}. and
	 * default display texts. The switch will directly update the property.
	 * 
	 * @param value the property to display and be updated.
	 * @param support the multi-language support for the property name
	 * @param onText the multi-language support for the ON text
	 * @param offText the multi-language support for the OFF text
	 */
	public OnOffSwitch(BooleanProperty value, MultiLanguageSupport support, MultiLanguageSupport onText, MultiLanguageSupport offText) {
		this(new BooleanPropertyWrapper(value, support, onText, offText));
	}

	/**
	 * Get the status of the switch
	 * 
	 * @return the on (true) or off (false) status
	 */
	public boolean isOn() {
		return (Boolean)onOffProperty.getValue();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		repaint();
	}

	@Override
	public void detach() {
		onOffProperty.removeEditablePropertyListener(this);
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof BooleanProperty) {
			return new OnOffSwitch((BooleanProperty)property);
		}
		return null;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g.create();
		switchWidth = (int)getWidth() - (INSET * 2);
		switchHeight = (int)getHeight() - (INSET * 2);
		thumbWidth = (int)(switchWidth * THUMB_WIDTH_RATIO);
		thumbHeight = (int)(switchHeight * THUMB_HEIGHT_RATIO);
		switchX = INSET;
		switchY = INSET;
		thumbY = switchY - ((thumbHeight - switchHeight) / 2);
		thumbX = ((Boolean)onOffProperty.getValue())? switchX + (switchWidth - thumbWidth) : switchX;
		int minX = switchX - EXTENT;
		int maxX = switchX  + switchWidth - thumbWidth + EXTENT;
		thumbX = ((thumbX + movedX) < minX)? minX : ((thumbX + movedX) > maxX)? maxX : (thumbX + movedX);
		drawBackground(g2d);
		drawOnOffText(g2d);
		drawThumb(g2d);
		g.dispose();
	}

	/**
	 * Draw the background of the switch
	 * 
	 * @param g2d the graphics context
	 */
	private void drawBackground(Graphics2D g2d) {
		g2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		Color color = ((Boolean)onOffProperty.getValue())? ON_COLOR : OFF_COLOR;
		g2d.setColor(color);
		g2d.fillRoundRect(switchX, switchY, switchWidth, switchHeight, MIN_ARC_WIDTH, MIN_ARC_HEIGHT);
		g2d.setColor(gray);
		g2d.drawRoundRect(switchX, switchY, switchWidth, switchHeight, MIN_ARC_WIDTH, MIN_ARC_HEIGHT);
	}

	/**
	 * Draw the thumb of the switch
	 * 
	 * @param g2d the graphics context
	 */
	private void drawThumb(Graphics2D g2d) {
		GradientPaint paint = new GradientPaint(0, 0, white, 0, (int)(switchHeight * 0.75), lightGray, true);
		g2d.setPaint(paint);
		g2d.fillRoundRect(thumbX, thumbY, thumbWidth, thumbHeight, MIN_ARC_WIDTH, MIN_ARC_HEIGHT);
		g2d.setColor(gray);
		g2d.drawRoundRect(thumbX, thumbY, thumbWidth, thumbHeight, MIN_ARC_WIDTH, MIN_ARC_HEIGHT);
	}

	/**
	 * Draw the on/off status of the switch
	 * 
	 * @param g2d the graphics context
	 */
	private void drawOnOffText(Graphics2D g2d) {
		g2d.setColor(Color.white);
		g2d.setFont(new Font(getFont().getFamily(), Font.BOLD, DEFAULT_FONT_SIZE));
		boolean onOff = (Boolean)onOffProperty.getValue();
		String status = onOffProperty.getStatusText();
		LineMetrics metrics = g2d.getFontMetrics().getLineMetrics(status, g2d);
		int textHeight = (int)metrics.getHeight();
		int textWidth = g2d.getFontMetrics().stringWidth(status);
		int textXOffset = ((switchWidth - thumbWidth) - textWidth) / 2;
		int textYOffset = (switchHeight - textHeight - (int)metrics.getLeading()) / 2;
		int textX = onOff? switchX + textXOffset : switchX + thumbWidth + textXOffset;
		int textY = switchY + textHeight - (int)metrics.getDescent() + textYOffset;
		g2d.drawString(status, textX, textY);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) {
			movedX = e.getX() - pressedX;
			updateUI();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(onOffProperty.isUIEditable() && e.getX() > thumbX && e.getX() < (thumbX + thumbWidth)) {
			pressed = true;
			pressedX = e.getX();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(pressed) {
			pressed = false;
			int diffX = e.getX() - pressedX;
			if(abs(diffX) > (getWidth() / 5)) {
				onOffProperty.setValue(valueOf((diffX) > 0));
			}
			movedX = 0;
			updateUI();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
