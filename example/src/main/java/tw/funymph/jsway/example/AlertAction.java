/* AlertAction.java created on 2013/5/10
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
package tw.funymph.jsway.example;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * This class shows alert message when user clicked on it.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.2
 */
public class AlertAction extends AbstractAction {

	private static final long serialVersionUID = -4980858239481741284L;

	private String alertMessage;

	/**
	 * Construct a <code>AlertAction</code> instance with the alert message.
	 * 
	 * @param message the alert message
	 */
	public AlertAction(String message) {
		alertMessage = message;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		showMessageDialog(null, alertMessage);
	}
}
