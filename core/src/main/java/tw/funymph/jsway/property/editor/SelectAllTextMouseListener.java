/* SelectAllTextMouseListener.java created on May 1, 2012
 *
 * Copyright (C) 2012. Pin-Ying Tu all rights reserved.
 *
 * This file is a part of the JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.property.editor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

/**
 * A text field mouse listener that selects all text when the mouse is clicked
 * on the specified text field.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class SelectAllTextMouseListener implements MouseListener {

	private JTextField textEditor;

	/**
	 * Construct a <code>SelectAllTextMouseListener</code> instance with the
	 * specified target editor.
	 * 
	 * @param editor the target editor
	 */
	public SelectAllTextMouseListener(JTextField editor) {
		textEditor = editor;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		textEditor.selectAll();
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}
}
