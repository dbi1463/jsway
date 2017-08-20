/* ColorButton.java created on 2011/10/13
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
package tw.funymph.jsway.property.editor;

import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import javax.swing.JButton;
import javax.swing.JComponent;

import tw.funymph.jsway.color.ShowColorChooserAction;
import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.property.EditableProperty;

/**
 * A factory that creates editor for {@link ColorProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public final class ColorButton extends JButton implements PropertyEditor {

	private static final long serialVersionUID = -8211274207945977946L;

	private static final String NULL_POST_ACTION_EXCEPTION = "Cannot create a color button from a null action!";

	private ShowColorChooserAction postAction;

	/**
	 * Construct a <code>ColorButton</code> instance as a factory.
	 */
	ColorButton() {}

	/**
	 * Construct a <code>ColorButton</code> instance with the specified action.
	 * 
	 * @param action the action contains the color property and other information
	 */
	public ColorButton(ShowColorChooserAction action) {
		super(action);
		postAction = requireNonNull(action, NULL_POST_ACTION_EXCEPTION);
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof ColorProperty) {
			return new ColorButton(new ShowColorChooserAction(((ColorProperty)property)));
		}
		return null;
	}

	@Override
	public void detach() {
		postAction.detach();
	}
}
