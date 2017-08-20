/* SwayUtilities.java created on 2011/10/20
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

import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenuItem;

/**
 * A helper class that offers a suite of methods to create Java Swing GUI
 * components quickly.
 * 
 * @author Pin-Ying Tu
 * @version 1.0
 * @since 1.0
 */
public class SwayUtilities {

	/**
	 * Create a tool bar button and register the action as the mouse event listener.
	 * 
	 * @param action the action that is aware of mouse events
	 * @return the created button
	 */
	public static JButton createToolBarButton(MouseHoverAwareAction action) {
		return createToolBarButton(action, action);
	}

	/**
	 * Create a menu item and register the action as the mouse event listener.
	 * 
	 * @param action the action that is aware of mouse events
	 * @return the created menu item
	 */
	public static JMenuItem createMenuItem(MouseHoverAwareAction action) {
		return createMenuItem(action, action);
	}

	/**
	 * Create a tool bar button and register the mouse event listener.
	 * 
	 * @param action the action
	 * @param listener the listener to handle mouse events
	 * @return the created button
	 */
	public static JButton createToolBarButton(Action action, MouseListener listener) {
		JButton button = new JButton(action);
		button.addMouseListener(listener);
		return button;
	}

	/**
	 * Create a menu item and register the mouse event listener.
	 * 
	 * @param action the action
	 * @param listener the listener to handle mouse events
	 * @return the created menu item
	 */
	public static JMenuItem createMenuItem(Action action, MouseListener listener) {
		JMenuItem item = new JMenuItem(action);
		item.addMouseListener(listener);
		return item;
	}
}
