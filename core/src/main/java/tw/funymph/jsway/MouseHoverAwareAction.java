/* MouseHoverAwareAction.java created on 2011/10/20
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

import static tw.funymph.jsway.utils.ImageUtilities.loadIcon;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 * The abstract class offers the additional behavior and icon to handle the mouse
 * entered/exited/pressed/released events from a button or a menu item (class that
 * implements abstract button)
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public abstract class MouseHoverAwareAction extends AbstractAction implements MouseListener {

	private static final long serialVersionUID = 6838162189267948656L;

	protected Icon normalIcon;
	protected Icon hoveredIcon;
	protected Icon pressedIcon;

	/**
	 * Construct a <code>MouseHoverAwareAction</code> without the name by
	 * its children.
	 */
	protected MouseHoverAwareAction() {
		super();
	}

	/**
	 * Construct a <code>MouseHoverAwareAction</code> with name by its children.
	 * 
	 * @param name the action name
	 */
	protected MouseHoverAwareAction(String name) {
		super(name);
	}

	/**
	 * Construct a <code>MouseHoverAwareAction</code> with name and the icon
	 * by its children.
	 * 
	 * @param name the action name
	 * @param normal the icon for the normal state
	 */
	protected MouseHoverAwareAction(String name, Icon normal) {
		this(name, normal, null, null);
	}

	/**
	 * Construct a <code>MouseHoverAwareAction</code> with name and the icon
	 * by its children.
	 * 
	 * @param name the action name
	 * @param normal the icon for the normal state
	 * @since 1.2
	 */
	protected MouseHoverAwareAction(String name, String normal) {
		this(name, normal, null, null);
	}

	/**
	 * Construct a <code>MouseHoverAwareAction</code> with name and three icons for
	 * the normal, hovered, pressed states by its children.
	 * 
	 * @param name the action name
	 * @param normal the icon path for the normal state
	 * @param hover the icon path for the mouse hovered state
	 * @param pressed the icon path for the mouse pressed state
	 * @since 1.2
	 */
	protected MouseHoverAwareAction(String name, String normal, String hover, String pressed) {
		this(name, loadIcon(normal), loadIcon(hover), loadIcon(pressed));
	}

	/**
	 * Construct a <code>MouseHoverAwareAction</code> with name and three icons for
	 * the normal, hovered, pressed states by its children.
	 * 
	 * @param name the action name
	 * @param normal the icon for the normal state
	 * @param hover the icon for the mouse hovered state
	 * @param pressed the icon for the mouse pressed state
	 */
	protected MouseHoverAwareAction(String name, Icon normal, Icon hover, Icon pressed) {
		super(name, normal);
		normalIcon = normal;
		hoveredIcon = hover;
		pressedIcon = pressed;
	}

	/**
	 * Get the normal (small) icon.
	 * 
	 * @return the normal (small) icon
	 * @since 1.2
	 */
	public Icon getNormalIcon() {
		return normalIcon;
	}

	/**
	 * Get the icon displayed when the mouse is hovered.
	 * 
	 * @return the hovered icon
	 * @since 1.2
	 */
	public Icon getHoveredIcon() {
		return hoveredIcon;
	}

	/**
	 * Get the icon displayed when any button of the mouse is pressed.
	 * 
	 * @return the pressed icon
	 * @since 1.2
	 */
	public Icon getPressedIcon() {
		return pressedIcon;
	}

	/**
	 * Set the small icon.
	 * 
	 * @param icon the small icon
	 */
	public void setNormalIcon(Icon icon) {
		normalIcon = icon;
		putValue(SMALL_ICON, normalIcon);
	}

	/**
	 * Set the icon displayed when the mouse is hovered.
	 * 
	 * @param icon the hovered icon
	 */
	public void setHoveredIcon(Icon icon) {
		hoveredIcon = icon;
	}

	/**
	 * Set the icon displayed when any button of the mouse is pressed.
	 * 
	 * @param icon the pressed icon
	 */
	public void setPressedIcon(Icon icon) {
		pressedIcon = icon;
	}

	/**
	 * Get the action name.
	 * 
	 * @return the action name
	 * @since 1.2
	 */
	public String getName() {
		return (String)getValue(NAME);
	}

	/**
	 * Get the current icon (based on the current status).
	 * 
	 * @return the current icon
	 * @since 1.2
	 */
	public Icon getCurrentIcon() {
		return (Icon)getValue(SMALL_ICON);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(hoveredIcon != null) {
			putValue(SMALL_ICON, hoveredIcon);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(getValue(SMALL_ICON) != normalIcon) {
			putValue(SMALL_ICON, normalIcon);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(pressedIcon != null) {
			putValue(SMALL_ICON, pressedIcon);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(getValue(SMALL_ICON) != normalIcon) {
			putValue(SMALL_ICON, normalIcon);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}
}
