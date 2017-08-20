/* PropertyNameLabel.java created on 2012/7/6
 *
 * Copyright (C) 2012. dbi1463 all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.property.editor;

import static tw.funymph.jsway.EmptyIcon.createGapIcon;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * A simple class that customize the label appearance in properties edit panels.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class PropertyNameLabel extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 226905184031191746L;

	private Color backgroundColor;

	/**
	 * Construct a <code>PropertyNameLabel</code> instance as the cell
	 * renderer factory with the specified background color.
	 * 
	 * @param bgColor the background color of the created label
	 */
	PropertyNameLabel(Color bgColor) {
		backgroundColor = bgColor;
	}

	/**
	 * Construct a <code>PropertyNameLabel</code> instance as the cell
	 * renderer with the specified background color.
	 * 
	 * @param value the displayed value
	 * @param bgColor the background color of the created label
	 */
	PropertyNameLabel(String value, Color bgColor) {
		super(value, createGapIcon(), LEADING);
		backgroundColor = bgColor;
	}

	/**
	 * Set the background color. Note that this is not equal to the {@link #setBackground(Color)}.
	 * 
	 * @param color the background color
	 */
	public void setBackgroundColor(Color color) {
		backgroundColor = color;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label = new PropertyNameLabel((String)value, backgroundColor);
		return label;
	}

	@Override
	public void paint(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(backgroundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paint(g);
		g.setColor(oldColor);
	}
}
