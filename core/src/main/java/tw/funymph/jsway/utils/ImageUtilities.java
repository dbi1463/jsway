/* ImageUtilities.java created on 2011/9/16
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
package tw.funymph.jsway.utils;

import static java.awt.image.BufferedImage.TYPE_4BYTE_ABGR;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;

/**
 * A set of useful image related methods.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class ImageUtilities {

	private static ImageUtilities utilities;

	/**
	 * Disable creation.
	 */
	private ImageUtilities() {}

	/**
	 * Load a image icon from the path. Note that the icon packed
	 * in a jar file can also be loaded.
	 * 
	 * @param path the path to load an icon
	 * @return the icon; null may return if the given path does not exist.
	 */
	public static ImageIcon loadIcon(String path) {
		if(path == null) {
			return null;
		}
		URL url = getIconUtilities().getClass().getClassLoader().getResource(path);
		if(url == null) {
			return null;
		}
		return new ImageIcon(url);
	}

	/**
	 * Load a image from the path. Note that the icon packed in a jar file
	 * can also be loaded.
	 * 
	 * @param path the path to load an icon
	 * @return the image; null may return if the given path does not exist.
	 */
	public static Image loadImage(String path) {
		if(path == null) {
			return null;
		}
		URL url = getIconUtilities().getClass().getClassLoader().getResource(path);
		if(url == null) {
			return null;
		}
		return new ImageIcon(url).getImage();
	}

	/**
	 * Create a <code>BufferedImage</code> instance from an existing image.
	 * 
	 * @param image the existing image
	 * @return the buffered image from the existing image
	 */
	public static BufferedImage makeBufferedImage(Image image) {
		return makeBufferedImage(image, TYPE_4BYTE_ABGR);
	}

	/**
	 * Create a <code>BufferedImage</code> instance from an existing image.
	 * 
	 * @param image the existing image
	 * @param type the color type
	 * @return the buffered image from the existing image
	 */
	public static BufferedImage makeBufferedImage(Image image, int type) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(image, null, null);
		return bufferedImage;
	}

	/**
	 * The Java class loader needs an instance so that the class apply the
	 * Singleton pattern to offer an instance of <code>ImageUtilities</code>.
	 * 
	 * @return the <code>ImageUtilities</code> instance
	 */
	private static ImageUtilities getIconUtilities() {
		if(utilities == null) {
			utilities = new ImageUtilities();
		}
		return utilities;
	}
}
