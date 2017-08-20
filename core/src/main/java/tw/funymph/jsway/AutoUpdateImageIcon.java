/* AutoUpdateImageIcon.java created on 2011/9/24
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

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

/**
 * An abstract base class that will register every component which includes
 * the icon as the observers. If the concrete implementation want to update
 * itself, the class can invoke <code>invalidateContainer()</code> to notify
 * all registered components to repaint.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public abstract class AutoUpdateImageIcon extends ImageIcon {

	private static final long serialVersionUID = 6067945788700929991L;

	protected List<Component> containers;
	protected BufferedImage image;

	/**
	 * Initialize the base class called by its concrete children.
	 */
	protected AutoUpdateImageIcon() {
		containers = new LinkedList<Component>();
	}

	@Override
	public int getIconWidth() {
		return image.getWidth();
	}

	@Override
	public int getIconHeight() {
		return image.getHeight();
	}

	@Override
	public Image getImage() {
		return image;
	}

	@Override
	public void paintIcon(Component c, Graphics g, int x, int y) {
		addContainer(c);
		g.drawImage(image, x, y, image.getWidth(), image.getHeight(), c);
	}

	/**
	 * Add a specific container.
	 * 
	 * @param container the container to be added.
	 */
	public void addContainer(Component container) {
		if(!containers.contains(container)) {
			containers.add(container);
		}
	}

	/**
	 * Remove the specific container.
	 * 
	 * @param container the container to be removed.
	 */
	public void removeContainer(Component container) {
		containers.remove(container);
	}

	/**
	 * Notify all containers to repaint.
	 */
	protected void invalidateContainer() {
		for(Component container : containers) {
			if(container != null) {
				container.repaint();
			}
		}
	}
}
