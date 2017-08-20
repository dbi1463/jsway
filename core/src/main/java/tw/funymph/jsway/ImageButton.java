/* ImageButton.java created on 2013/5/8
 *
 * Copyright (C) 2013. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway;

import static java.lang.Math.max;
import static javax.swing.Action.SMALL_ICON;
import static tw.funymph.jsway.utils.ImageUtilities.loadIcon;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;

/**
 * A simple UI component that shows an image as a button. The image can be
 * changed based on the status: normal, mouse hovered, or mouse pressed.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.2
 */
public class ImageButton extends JComponent implements PropertyChangeListener {

	private static final long serialVersionUID = 5401741362895592434L;

	private MouseHoverAwareAction mouseHoverAwareAction;

	private int width;
	private int height;
	private Dimension size;

	/**
	 * Construct a <code>ImageButton</code> instance with the real action to
	 * perform when the button is clicked and the path to load the icon for
	 * the normal status.
	 * 
	 * @param action the real action to perform
	 * @param normal the normal icon path
	 */
	public ImageButton(Action action, String normal) {
		this(new InnerMouseHoverAwareAction(action, normal, null, null));
	}

	/**
	 * Construct a <code>ImageButton</code> instance with the real action to
	 * perform when the button is clicked and the paths to load the icons for
	 * the normal, mouse hovered, and mouse clicked statuses.
	 * 
	 * @param action the real action to perform
	 * @param normal the normal icon path
	 * @param hovered the hovered icon path
	 * @param pressed the pressed icon path
	 */
	public ImageButton(Action action, String normal, String hovered, String pressed) {
		this(new InnerMouseHoverAwareAction(action, normal, hovered, pressed));
	}

	/**
	 * Construct a <code>ImageButton</code> instance with the real action to
	 * perform when the button is clicked and the icon for the normal status.
	 * 
	 * @param action the real action to perform
	 * @param normal the normal icon
	 */
	public ImageButton(Action action, Icon normal) {
		this(new InnerMouseHoverAwareAction(action, normal, null, null));
	}

	/**
	 * Construct a <code>ImageButton</code> instance with the real action to
	 * perform when the button is clicked and the icons for the normal, mouse
	 * hovered, and mouse clicked statuses.
	 * 
	 * @param action the real action to perform
	 * @param normal the normal icon
	 * @param hovered the hovered icon
	 * @param pressed the pressed icon
	 */
	public ImageButton(Action action, Icon normal, Icon hovered, Icon pressed) {
		this(new InnerMouseHoverAwareAction(action, normal, hovered, pressed));
	}

	/**
	 * Construct a <code>ImageButton</code> instance with the mouse hover aware
	 * action that is the real action to perform when the button is clicked, but
	 * also the source to get the icons for the normal, mouse hovered, and mouse
	 * clicked statuses.
	 * 
	 * @param action the mouse hover aware action
	 */
	public ImageButton(MouseHoverAwareAction action) {
		mouseHoverAwareAction = action;
		calculateSize();
		addMouseListener(mouseHoverAwareAction);
		mouseHoverAwareAction.addPropertyChangeListener(this);
	}

	/**
	 * Set the small icon for the normal status.
	 * 
	 * @param icon the normal icon
	 */
	public void setNormalIcon(Icon normal) {
		mouseHoverAwareAction.setNormalIcon(normal);
	}

	/**
	 * Set the icon displayed when the mouse is hovered.
	 * 
	 * @param icon the hovered icon
	 */
	public void setHoveredIcon(Icon hovered) {
		mouseHoverAwareAction.setHoveredIcon(hovered);
	}

	/**
	 * Set the icon displayed when any button of the mouse is pressed.
	 * 
	 * @param icon the pressed icon
	 */
	public void setPressedIcon(Icon pressed) {
		mouseHoverAwareAction.setPressedIcon(pressed);
	}

	@Override
	public void propertyChange(PropertyChangeEvent e) {
		setEnabled(mouseHoverAwareAction.isEnabled());
		repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Icon currentIcon = (Icon)mouseHoverAwareAction.getValue(SMALL_ICON);
		int x = (width - currentIcon.getIconWidth()) / 2;
		int y = (height - currentIcon.getIconHeight()) / 2;
		currentIcon.paintIcon(this, g, x, y);
	}

	@Override
	public Dimension getPreferredSize() {
		return size;
	}

	@Override
	public Dimension getMinimumSize() {
		return size;
	}

	@Override
	public Dimension getMaximumSize() {
		return size;
	}

	public void calculateSize() {
		Icon normal = mouseHoverAwareAction.getNormalIcon();
		Icon hovered = mouseHoverAwareAction.getHoveredIcon();
		Icon pressed = mouseHoverAwareAction.getPressedIcon();
		width = max(max(getIconWidth(normal), getIconWidth(hovered)), getIconWidth(pressed));
		height = max(max(getIconHeight(normal), getIconHeight(hovered)), getIconHeight(pressed));
		size = new Dimension(width, height);
	}

	private int getIconWidth(Icon icon) {
		return icon != null? icon.getIconWidth() : 0;
	}

	private int getIconHeight(Icon icon) {
		return icon != null? icon.getIconHeight() : 0;
	}

	/**
	 * The class wrap an normal action with the icons for different statuses.
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.2
	 */
	private static class InnerMouseHoverAwareAction extends MouseHoverAwareAction {

		private static final long serialVersionUID = -917992032673182709L;

		private Action wrappedAction;

		/**
		 * Construct a <code>ImageButton</code> instance with the real action to
		 * perform when the button is clicked and the paths to load the icons for
		 * the normal, mouse hovered, and mouse clicked statuses.
		 * 
		 * @param action the real action to perform
		 * @param normal the normal icon path
		 * @param hovered the hovered icon path
		 * @param pressed the pressed icon path
		 */
		public InnerMouseHoverAwareAction(Action action, String normal, String hovered, String pressed) {
			this(action, loadIcon(normal), loadIcon(hovered), loadIcon(pressed));
		}

		/**
		 * Construct a <code>ImageButton</code> instance with the real action to
		 * perform when the button is clicked and the icons for the normal, mouse
		 * hovered, and mouse clicked statuses.
		 * 
		 * @param action the real action to perform
		 * @param normal the normal icon
		 * @param hovered the hovered icon
		 * @param pressed the pressed icon
		 */
		public InnerMouseHoverAwareAction(Action action, Icon normal, Icon hovered, Icon pressed) {
			super((String)action.getValue(NAME), normal, hovered, pressed);
			wrappedAction = action;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			wrappedAction.actionPerformed(new ActionEvent(e.getSource(), e.getID(), getName()));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			wrappedAction.actionPerformed(e);
		}
	}
}
