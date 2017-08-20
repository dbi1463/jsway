/* ShowColorChooserAction.java created on 2011/9/15
 *
 * Copyright (C) 2011.  Pin-Ying Tu all rights reserved.
 *
 * This file is a part of JavaSway project.
 * 
 * The JavaSway project is published under the MIT license.
 * 
 * If you want to use the classes of this project in any commercial
 * product, please contact the author for the license.
 */
package tw.funymph.jsway.color;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.ColorProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

import static java.lang.String.format;
import static javax.swing.JColorChooser.showDialog;
import static tw.funymph.jsway.color.ColorUtilities.toHexString;
import static tw.funymph.jsway.property.EditableProperty.*;

/**
 * An action that can open a color chooser dialog and keep the old color and new
 * selected color in the {@link ColorProperty}. The selection of new color will
 * fire the NAME, SMALL_ICON, and CURRENT_COLOR properties changed events, so that
 * <code>ShowColorChooserAction</code> can be compatible with the Java Swing buttons,
 * e.g., {@link javax.swing.JButton JButton}, {@link javax.swing.JMenuItem JMenuItem},
 * {@link javax.swing.JToogleButton JToogleButton}, etc. The extended class can override
 * the {@link #postActionPerformed(ActionEvent)} method to offer the functionality after
 * the color property is set.
 * 
 * @author Pin-Ying Tu
 * @version 1.1
 * @since 1.0
 */
public class ShowColorChooserAction extends AbstractAction implements SelfDetachable, EditablePropertyListener, MultiLanguageSupportListener {

	private static final long serialVersionUID = -1685370686889434757L;

	public static final String CURRENT_COLOR = "Current Color";
	public static final String DEFAULT_CHOOSER_TITLE = "Select A Color";
	public static final String TEXT_COLOR_ICON_PATH = "icons/TextColor.png";
	public static final String BRUSH_COLOR_ICON_PATH = "icons/BrushColor.png";
	public static final String BACKGROUND_COLOR_ICON_PATH = "icons/BackgroundColor.png";

	private static final String DESCRIPTION_FORMAT = "%1s - %2s";

	private ColorBoxIcon icon;
	protected ColorProperty displayColor;

	private String baseIconPath;
	private String dialogTitle;

	private boolean showColorBoxIcon;
	private boolean useColorInfoAsActionName;

	/**
	 * Construct a <code>ShowColorChooserAction</code> instance with the specified name
	 * of the color.
	 * 
	 * @param name the name of the color.
	 */
	public ShowColorChooserAction(String name) {
		this(new ColorProperty(name, Color.black));
	}

	/**
	 * Construct a <code>ShowColorChooserAction</code> instance. The new selected
	 * color is kept in the given {@link ColorProperty} property. A color box icon
	 * will be created and the color information is used as the action name.
	 * 
	 * @param color the <code>ColorProperty</code> instance
	 */
	public ShowColorChooserAction(ColorProperty color) {
		this(color, null, true, true);
	}

	/**
	 * Construct a <code>ShowColorChooserAction</code> instance with a base icon.
	 * The new selected color is kept in the given {@link ColorProperty} property.
	 * Note that the color box is drawn on the base icon, so that the base icon may
	 * need to leave a space for the color box. The color information is used as the
	 * action name.
	 * 
	 * @param color the <code>ColorProperty</code> instance
	 * @param baseIconPath the base icon path
	 */
	public ShowColorChooserAction(ColorProperty color, String baseIconPath) {
		this(color, baseIconPath, true, true);
	}

	/**
	 * Construct a <code>ShowColorChooserAction</code> instance. The new selected
	 * color is kept in the given {@link ColorProperty} property. A color box icon
	 * will be created and the color information is used as the action name.
	 * By default, the color information is used as the action name. To change the
	 * default behavior, please specify the <code>showColorBoxIcon</code> and
	 * <code>useColorInfoActionName</code> parameters.
	 * 
	 * @param color the <code>ColorProperty</code> instance
	 * @param showColorBoxIcon a color box icon is created if <code>true</code> is given 
	 * @param useColorInfoAsActionName the color information is used as the action is <code>true</code> is given
	 */
	public ShowColorChooserAction(ColorProperty color, boolean showColorBoxIcon, boolean useColorInfoAsActionName) {
		this(color, null, showColorBoxIcon, useColorInfoAsActionName);
	}

	/**
	 * Construct a <code>ShowColorChooserAction</code> with a base icon. The new
	 * selected color is kept in the given {@link ColorProperty} property. Note that
	 * a smaller color box is drawn on the base icon, so that the base icon may need
	 * to leave a space for the color box. By default, the color information is used
	 * as the action name. To change the default behavior, please specify the
	 * <code>showColorBoxIcon</code> and <code>useColorInfoActionName</code>
	 * parameters.
	 * 
	 * @param color the <code>ColorProperty</code> instance
	 * @param iconPath the base icon path
	 * @param showColorBox a color box icon is created if <code>true</code> is given 
	 * @param useColorInfoAsName the color information is used as the action is <code>true</code> is given
	 */
	public ShowColorChooserAction(ColorProperty color, String iconPath, boolean showColorBox, boolean useColorInfoAsName) {
		displayColor = color;
		baseIconPath = iconPath;
		dialogTitle = (displayColor.getName() != null)? displayColor.getName() : DEFAULT_CHOOSER_TITLE;
		displayColor.addEditablePropertyListener(this);
		displayColor.addMultiLanguageSupportListener(this);
		updateActionShortDescription();
		setShowColorBoxIcon(showColorBox);
		setUseColorInfoAsActionName(useColorInfoAsName);
	}

	/**
	 * Set whether use the color information as the action name.
	 * 
	 * @param useColorInfoAsName the color information is used as the action is <code>true</code> is given
	 */
	public void setUseColorInfoAsActionName(boolean useColorInfoAsName) {
		if(useColorInfoAsActionName != useColorInfoAsName) {
			useColorInfoAsActionName = useColorInfoAsName;
			updateActionName();
		}
	}

	/**
	 * Set whether show the color box icon.
	 * 
	 * @param showColorBox the color box icon is created if <code>true</code> is given
	 */
	public void setShowColorBoxIcon(boolean showColorBox) {
		if(showColorBoxIcon != showColorBox) {
			showColorBoxIcon = showColorBox;
			updateActionIcon();
		}
	}

	/**
	 * Get the {@link ColorProperty} property inside the action
	 * 
	 * @return the color property
	 */
	public ColorProperty getColor() {
		return displayColor;
	}

	/**
	 * Overwrite the color chooser title. If the {@link ColorProperty} instance
	 * has a name, the color chooser will pop-up with the specific name; otherwise,
	 * the title is {@link #DEFAULT_CHOOSER_TITLE}. In both cases, the title
	 * can be overwritten.
	 * 
	 * @param title the new title
	 */
	public void setColorChooserTitle(String title) {
		dialogTitle = title;
	}

	public void detach() {
		displayColor.removeEditablePropertyListener(this);
		displayColor.removeMultiLanguageSupportListener(this);
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		if(KEY_VALUE.equals(event.getKey())) {
			updateActionName();
			updateActionShortDescription();
			firePropertyChange(CURRENT_COLOR, event.getOldValue(), event.getCurrentValue());
		}
		else if(KEY_UI_EDITABLE.equals(event.getKey())) {
			setEnabled((Boolean)event.getCurrentValue());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Color color = showDialog((JComponent)e.getSource(), dialogTitle, displayColor.getColor());
		if(color != null) {
			displayColor.setCurrentColor(color);
		}
		postActionPerformed(e);
	}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		updateActionName();
		updateActionShortDescription();
	}

	/**
	 * The extended class can override this method to offer the additional functionality
	 * after the color property is set.
	 * 
	 * @param e the action event
	 */
	protected void postActionPerformed(ActionEvent e) { }

	/**
	 * Update the {@link javax.swing.Action#NAME Action.NAME} property.
	 */
	private void updateActionName() {
		String name = useColorInfoAsActionName? toHexString(displayColor.getColor()) : displayColor.getDisplayText();
		putValue(NAME, name);
	}

	/**
	 * Update the {@link javax.swing.Action#SMALL_ICON Action.SMALL_ICON} property.
	 */
	private void updateActionIcon() {
		icon = showColorBoxIcon? new ColorBoxIcon(displayColor, baseIconPath) : null;
		putValue(SMALL_ICON, icon);
	}

	/**
	 * Update the {@link javax.swing.Action#SHORT_DESCRIPTION Action.SHORT_DESCRIPTION} property.
	 */
	private void updateActionShortDescription() {
		String description = toHexString(displayColor.getColor());
		String colorText = displayColor.getDisplayText();
		description = (colorText != null)? format(DESCRIPTION_FORMAT, colorText, description) : description;
		putValue(SHORT_DESCRIPTION, description);
	}
}
