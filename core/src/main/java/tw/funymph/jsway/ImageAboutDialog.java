/* ImageAboutDialog.java created on 2011/10/15
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

import static com.sun.awt.AWTUtilities.setWindowOpaque;
import static java.awt.BorderLayout.CENTER;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_ESCAPE;
import static tw.funymph.jsway.SimpleMultiLanguageSupport.simpleSupport;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A simple class that shows a large image as the about dialog. The user can
 * press "ESC" or click right mouse key on the image to dismiss the dialog.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class ImageAboutDialog extends JDialog implements KeyListener, MouseListener, MouseMotionListener, MultiLanguageSupportListener {

	private static final long serialVersionUID = 5686150602794533482L;

	private int pressedX;
	private int pressedY;

	private boolean pressed;

	private JLabel imageLabel;
	private JPanel contentPane;
	private List<OverlappingText> overlappingTexts;

	/**
	 * Use the specified image to show a modal dialog.
	 * 
	 * @param parent the parent frame
	 * @param image the dialog image
	 */
	public static void showDialog(JFrame parent, ImageIcon image) {
		JDialog dialog = new ImageAboutDialog(parent, image);
		dialog.setVisible(true);
	}

	/**
	 * Construct an <code>ImageAboutDialog</code> instance with the
	 * specified parent frame and dialog image.
	 * 
	 * @param parent the parent frame
	 * @param image the dialog image
	 */
	@SuppressWarnings("restriction")
	public ImageAboutDialog(JFrame parent, ImageIcon image) {
		super(parent);
		pressed = false;
		contentPane = new JPanel();
		imageLabel = new JLabel(image);
		overlappingTexts = new ArrayList<OverlappingText>();

		contentPane.addKeyListener(this);
		contentPane.addMouseListener(this);
		contentPane.addMouseMotionListener(this);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(imageLabel, CENTER);

		setContentPane(contentPane);
		setSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				contentPane.requestFocus();
			}
		});

		setModal(true);
		setUndecorated(true);
		setLocationRelativeTo(parent);
		setWindowOpaque(this, false);
	}

	/**
	 * Add a text that will be overlapped on the background image. The text will
	 * be displayed on the specified (x, y) with the color.
	 * 
	 * @param text the text to be overlapped
	 * @param x the display location x
	 * @param y the display location y
	 * @param color the display color
	 */
	public void addOverlappingTex(String text, int x, int y, Color color) {
		addOverlappingTex(text, x, y, color, getFont());
	}

	/**
	 * Add a text that will be overlapped on the background image. The text will
	 * be displayed on the specified (x, y) with the color.
	 * 
	 * @param support the overlapping text with multiple language support
	 * @param x the display location x
	 * @param y the display location y
	 * @param color the display color
	 */
	public void addOverlappingTex(MultiLanguageSupport support, int x, int y, Color color) {
		addOverlappingTex(support, x, y, color, getFont());
	}

	/**
	 * Add a text that will be overlapped on the background image. The text will
	 * be displayed on the specified (x, y) with the color and font.
	 * 
	 * @param text the text to be overlapped
	 * @param x the display location x
	 * @param y the display location y
	 * @param color the display color
	 * @param font the display font
	 */
	public void addOverlappingTex(String text, int x, int y, Color color, Font font) {
		if(text != null) {
			overlappingTexts.add(new OverlappingText(text, x, y, color, font));
		}
	}

	/**
	 * Add a text that will be overlapped on the background image. The text will
	 * be displayed on the specified (x, y) with the color and font.
	 * 
	 * @param support the overlapping text with multiple language support
	 * @param x the display location x
	 * @param y the display location y
	 * @param color the display color
	 * @param font the display font
	 */
	public void addOverlappingTex(MultiLanguageSupport support, int x, int y, Color color, Font font) {
		if(support != null && support.getDisplayText() != null) {
			support.addMultiLanguageSupportListener(this);
			overlappingTexts.add(new OverlappingText(support, x, y, color, font));
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
		Font oldFont = g2d.getFont();
		Color oldColor = g2d.getColor();
		for(OverlappingText overlapping : overlappingTexts) {
			g2d.setFont(overlapping.getFont());
			g2d.setColor(overlapping.getColor());
			g2d.drawString(overlapping.getText(), overlapping.getX(), overlapping.getY());
		}
		g2d.setFont(oldFont);
		g2d.setColor(oldColor);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		dispose();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		pressedX = e.getX();
		pressedY = e.getY();
		pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int offsetX = e.getX() - pressedX;
		int offsetY = e.getY() - pressedY;
		setLocation(getX() + offsetX, getY() + offsetY);
		pressed = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(pressed) {
			int offsetX = e.getX() - pressedX;
			int offsetY = e.getY() - pressedY;
			setLocation(getX() + offsetX, getY() + offsetY);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == VK_ESCAPE || e.getKeyCode() == VK_ENTER) {
			dispose();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		contentPane.requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		repaint();
	}

	@Override
	public void dispose() {
		for(OverlappingText text : overlappingTexts) {
			text.removeMultiLanguageSupportListener(this);
		}
		overlappingTexts.clear();
		super.dispose();
	}

	/**
	 * An internal data structure to record the overlapping text with the
	 * displaying attributes (font, color, and location)
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.0
	 * @since 1.0
	 */
	private class OverlappingText {

		private int displayX;
		private int displayY;

		private Font displayFont;
		private Color foregroundColor;
		private MultiLanguageSupport multiLangSupport;

		/**
		 * Construct <code>OverlappingText</code> instance to record overlapping text with
		 * display location, color, and font.
		 * 
		 * @param text the overlapping text
		 * @param x the display location x
		 * @param y the display location y
		 * @param color the display color
		 * @param font the display font
		 */
		public OverlappingText(String text, int x, int y, Color color, Font font) {
			this(simpleSupport(text), x, y, color, font);
		}

		/**
		 * Construct <code>OverlappingText</code> instance to record overlapping text with
		 * display location, color, and font.
		 * 
		 * @param support the overlapping text with multiple language support
		 * @param x the display location x
		 * @param y the display location y
		 * @param color the display color
		 * @param font the display font
		 */
		public OverlappingText(MultiLanguageSupport support, int x, int y, Color color, Font font) {
			displayX = x;
			displayY = y;
			displayFont = font;
			foregroundColor = color;
			multiLangSupport = support;
		}

		/**
		 * Get the display color
		 * 
		 * @return the display color
		 */
		public Color getColor() {
			return foregroundColor;
		}

		/**
		 * Get the display font
		 * 
		 * @return the display font
		 */
		public Font getFont() {
			return displayFont;
		}

		/**
		 * Get the display location x
		 * 
		 * @return the display location x
		 */
		public int getX() {
			return displayX;
		}

		/**
		 * Get the display location y
		 * 
		 * @return the display location y
		 */
		public int getY() {
			return displayY;
		}

		/**
		 * Get the overlapping text
		 * 
		 * @return the overlapping text
		 */
		public String getText() {
			return multiLangSupport.getDisplayText();
		}

		/**
		 * Remove the listener from the multiple language support inside the
		 * overlapping text object.
		 * 
		 * @param listener the listener to be removed
		 */
		public void removeMultiLanguageSupportListener(MultiLanguageSupportListener listener) {
			multiLangSupport.removeMultiLanguageSupportListener(listener);
		}
	}
}
