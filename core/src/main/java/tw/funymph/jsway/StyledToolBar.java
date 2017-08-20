/* StyledToolBar.java created on 2011/12/10
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
package tw.funymph.jsway;

import static java.lang.System.getProperty;
import static javax.swing.Box.createHorizontalStrut;
import static tw.funymph.jsway.utils.ImageUtilities.loadImage;
import static tw.funymph.jsway.utils.ImageUtilities.makeBufferedImage;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

/**
 * A customized tool bar to support both the PC/MAC styles.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class StyledToolBar extends JToolBar {

	private static final long serialVersionUID = 8963663650811738324L;

	// Property names
	private static final String OS_NAME_PROPERTY = "os.name";
	private static final String BUTTON_TYPE_PROPERTY = "JButton.buttonType";
	private static final String SEGMENT_POSITION_PROPERTY = "JButton.segmentPosition";

	// Property values
	private static final String SEGMENT_ONLY = "only";
	private static final String POSITION_LAST = "last";
	private static final String POSITION_FIRST = "first";
	private static final String POSITION_MIDDLE = "middle";
	private static final String OS_X_PROPERTY_VALUE = "OS X";
	private static final String SEGMENTED_CAPSULE_BUTTON = "segmentedCapsule";

	private static final int HORIZONTAL_STRUT_WIDTH = 5;

	private boolean macStyle;

	private TexturePaint texture;
	private BufferedImage background;

	/**
	 * Construct a <code>StyledToolBar</code> instance.
	 */
	public StyledToolBar() {
		macStyle = getProperty(OS_NAME_PROPERTY).contains(OS_X_PROPERTY_VALUE);
	}

	@Override
	public void addSeparator() {
		if(macStyle) {
			add(createHorizontalStrut(HORIZONTAL_STRUT_WIDTH));
		}
		else {
			super.addSeparator();
		}
	}

	@Override
	public Component add(Component component) {
		if(macStyle && component instanceof JButton) {
			JButton button = (JButton)component;
			button.putClientProperty(BUTTON_TYPE_PROPERTY, SEGMENTED_CAPSULE_BUTTON);
			button.putClientProperty(SEGMENT_POSITION_PROPERTY, SEGMENT_ONLY);
		}
		return super.add(component);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(texture != null) {
			Graphics2D g2d = (Graphics2D)g.create();
			Paint old = g2d.getPaint();
			g2d.setPaint(texture);
			g2d.fillRect(0, 0, getWidth(), getHeight());
			g2d.setPaint(old);
			g2d.dispose();
		}
	}

	/**
	 * Add a button group into the tool bar. There will be a separator before
	 * and after the group.
	 * 
	 * @param group the button group
	 */
	public void add(ButtonGroup group) {
		addSeparator();
		if(macStyle) {
			addButtonWithMacStyles(group);
		}
		else {
			Enumeration<AbstractButton> buttons = group.getElements();
			while(buttons.hasMoreElements()) {
				AbstractButton button = buttons.nextElement();
				super.add(button);
			}
		}
		addSeparator();
	}

	/**
	 * Add a group of buttons into the tool bar. Note that this method is not the same
	 * as the {@link #add(ButtonGroup)} because the buttons in the list may not have
	 * the mutual exclusive relationship. There will be a separator before and after
	 * the group.
	 * 
	 * @param group a list of buttons
	 */
	public void addTextButtons(List<JButton> group) {
		addSeparator();
		int actionCount = group.size();
		if(macStyle) {
			addFirstPositionButton(group.get(0));
			for(int index = 1; index < actionCount - 1; index++) {
				addMiddlePositionButton(group.get(index));
			}
			addLastPositionButton(group.get(actionCount - 1));
		}
		else {
			for(JButton button : group) {
				super.add(button);
			}
		}
		addSeparator();
	}

	/**
	 * Add a group of toggle buttons into the tool bar. Note that this method is not
	 * the same as the {@link #add(ButtonGroup)} because the buttons in the list may
	 * not have the mutual exclusive relationship. There will be a separator before
	 * and after the group.
	 * 
	 * @param group a list of buttons
	 */
	public void addToogleButtons(List<JToggleButton> group) {
		addSeparator();
		int actionCount = group.size();
		if(macStyle) {
			addFirstPositionButton(group.get(0));
			for(int index = 1; index < actionCount - 1; index++) {
				addMiddlePositionButton(group.get(index));
			}
			addLastPositionButton(group.get(actionCount - 1));
		}
		else {
			for(JToggleButton button : group) {
				super.add(button);
			}
		}
		addSeparator();
	}

	/**
	 * Add a group of actions into the tool bar. There will be a separator before
	 * and after the group.
	 * 
	 * @param group a list of actions
	 */
	public void add(List<Action> group) {
		addSeparator();
		int actionCount = group.size();
		if(macStyle) {
			createFirstButton(group.get(0));
			for(int index = 1; index < actionCount - 1; index++) {
				createMiddleButton(group.get(index));
			}
			createLastButton(group.get(actionCount - 1));
		}
		else {
			for(Action action : group) {
				super.add(action);
			}
		}
		addSeparator();
	}

	/**
	 * Add the specified button as the first position button (the appearance will
	 * be different from the middle one and the last one)
	 * 
	 * @param button the button to be added
	 */
	public void addFirstPositionButton(AbstractButton button) {
		setFirstPositionProperty(button);
		super.add(button);
	}

	/**
	 * Add the specified button as the middle position button (the appearance will
	 * be different from the first one and the last one)
	 * 
	 * @param button the button to be added
	 */
	public void addMiddlePositionButton(AbstractButton button) {
		setMiddlePositionProperty(button);
		super.add(button);
	}

	/**
	 * Add the specified button as the last position button (the appearance will
	 * be different from the first one and the middle one)
	 * 
	 * @param button the button to be added
	 */
	public void addLastPositionButton(AbstractButton button) {
		setLastPositionProperty(button);
		super.add(button);
	}

	/**
	 * Set the path of the background image.
	 * 
	 * @param path the path of the background image
	 * @since 1.2
	 */
	public void setBackgroundImage(String path) {
		setBackgroundImage(loadImage(path));
	}

	/**
	 * Set the background image.
	 * 
	 * @param image the background image
	 * @since 1.2
	 */
	public void setBackgroundImage(Image image) {
		if(image != null) {
			setOpaque(false);
			background = makeBufferedImage(image);
			// Creates the texture with the image
			Rectangle2D rect = new Rectangle2D.Double(0, 0, background.getWidth(), background.getHeight());
			texture = new TexturePaint(background, rect);
		}
	}

	/**
	 * Add the button group with the MAC specific styles.
	 * 
	 * @param group the button group
	 */
	private void addButtonWithMacStyles(ButtonGroup group) {
		int index = 0;
		int buttonCount = group.getButtonCount();
		Enumeration<AbstractButton> buttons = group.getElements();
		while(buttons.hasMoreElements()) {
			AbstractButton button = buttons.nextElement();
			if(index == 0) {
				addFirstPositionButton(button);
			}
			else if(index == buttonCount - 1) {
				addLastPositionButton(button);
			}
			else {
				addMiddlePositionButton(button);
			}
			index++;
		}
	}

	/**
	 * Create and add the action as the first position button.
	 * 
	 * @param action the action to be added
	 * @return the created and added button
	 */
	private JButton createFirstButton(Action action) {
		JButton button = super.add(action);
		setFirstPositionProperty(button);
		return button;
	}

	/**
	 * Create and add the action as the middle position button.
	 * 
	 * @param action the action to be added
	 * @return the created and added button
	 */
	private JButton createMiddleButton(Action action) {
		JButton button = super.add(action);
		setMiddlePositionProperty(button);
		return button;
	}

	/**
	 * Create and add the action as the last position button.
	 * 
	 * @param action the action to be added
	 * @return the created and added button
	 */
	private JButton createLastButton(Action action) {
		JButton button = super.add(action);
		setLastPositionProperty(button);
		return button;
	}

	/**
	 * Set the properties for the first position button.
	 * 
	 * @param button the button to set properties
	 */
	private void setFirstPositionProperty(AbstractButton button) {
		button.putClientProperty(SEGMENT_POSITION_PROPERTY, POSITION_FIRST);
		button.putClientProperty(BUTTON_TYPE_PROPERTY, SEGMENTED_CAPSULE_BUTTON);
	}

	/**
	 * Set the properties for the middle position button.
	 * 
	 * @param button the button to set properties
	 */
	private void setMiddlePositionProperty(AbstractButton button) {
		button.putClientProperty(SEGMENT_POSITION_PROPERTY, POSITION_MIDDLE);
		button.putClientProperty(BUTTON_TYPE_PROPERTY, SEGMENTED_CAPSULE_BUTTON);
	}

	/**
	 * Set the properties for the last position button.
	 * 
	 * @param button the button to set properties
	 */
	private void setLastPositionProperty(AbstractButton button) {
		button.putClientProperty(SEGMENT_POSITION_PROPERTY, POSITION_LAST);
		button.putClientProperty(BUTTON_TYPE_PROPERTY, SEGMENTED_CAPSULE_BUTTON);
	}
}
