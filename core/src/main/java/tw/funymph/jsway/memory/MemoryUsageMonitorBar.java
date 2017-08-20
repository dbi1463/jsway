/* MemoryUsageMonitorBar.java created on 2011/9/19
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
package tw.funymph.jsway.memory;

import static java.lang.System.gc;
import static javax.swing.Action.ACTION_COMMAND_KEY;
import static tw.funymph.jsway.color.ColorUtilities.chageLeveledColorsEventSource;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.event.ChangeEvent;

import tw.funymph.jsway.MultiProgressMonitorBar;
import tw.funymph.jsway.color.LeveledColors;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * A memory usage monitor bar that shows the current free and used memory
 * size in many display formats. When the component is clicked, the garbage
 * collection is invoked to release the unused memory.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class MemoryUsageMonitorBar extends MultiProgressMonitorBar implements MouseListener, EditablePropertyListener {

	private static final long serialVersionUID = -2714607697832013390L;

	private static final int DEFAULT_WIDTH = 150;
	private static final int DEFAULT_HEIGHT = 20;

	private static final String NULL_MODEL_EXCEPTION = "Cannot set a null model";
	private static final String DEFAULT_TOOLTIP = "Click to invoke garbage collection";

	private Action clickAction;
	private LeveledColors colors;
	private MemoryUsageMonitorBarModel model;

	/**
	 * Construct a <code>MemoryUsageMonitorBar</code> with the default model without
	 * leveled memory usage colors.
	 */
	public MemoryUsageMonitorBar() {
		this(new DefaultMemoryUsageMonitorBarModel(), null);
	}

	/**
	 * Construct a <code>MemoryUsageMonitorBar</code> instance by specifying the
	 * {@link MemoryUsageMonitorBarModel} and {@link LeveledMemoryUsageColors}.
	 * The {@link MemoryUsageMonitorBarModel} offers the text and value. And the
	 * {@link LeveledMemoryUsageColors} offers the color information.
	 * 
	 * @param model the memory usage monitor bar model
	 * @param colors the leveled memory usage colors
	 */
	public MemoryUsageMonitorBar(MemoryUsageMonitorBarModel model, LeveledColors colors) {
		addMouseListener(this);
		setStringPainted(true);
		setToolTipText(DEFAULT_TOOLTIP);
		setLeveledMemoryUsageColors(colors);
		setMemoryUsageMonitorBarModel(model);
		setDefaultSizes(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	/**
	 * Set the memory usage monitor bar model. If the current model is not null and
	 * not the same to the given model, the method will remove itself from the current
	 * model and register itself into the new model.
	 * 
	 * @param newModel the memory usage bar model
	 */
	public void setMemoryUsageMonitorBarModel(MemoryUsageMonitorBarModel newModel) {
		requireNonNull(newModel, NULL_MODEL_EXCEPTION);
		if(model != newModel) {
			if(model != null) {
				model.removeChangeListener(this);
			}
			model = newModel;
			model.addChangeListener(this);
		}
	}

	/**
	 * Set the leveled memory usage colors model. If the current colors model is
	 * not null and not the same to the given model, the method will remove itself
	 * from the current model, and registered itself into the given model.
	 * 
	 * @param newColors the leveled memory usage colors model
	 */
	public void setLeveledMemoryUsageColors(LeveledColors newColors) {
		chageLeveledColorsEventSource(colors, newColors, this);
		colors = newColors;
	}

	/**
	 * Set the action that is invoked when the memory usage monitor bar is clicked.
	 * Note that the action will replace the default behavior on mouse clicked event.
	 * 
	 * @param action the action to be invoked
	 */
	public void setAction(Action action) {
		clickAction = action;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(clickAction == null) {
			gc();
		}
		else {
			String command = (String)clickAction.getValue(ACTION_COMMAND_KEY);
			clickAction.actionPerformed(new ActionEvent(this, e.getID(), command, e.getModifiers()));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void stateChanged(ChangeEvent e) {
		super.stateChanged(e);
		if(e.getSource() == model) {
			updateContents();
		}
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		updateContents();
	}

	/**
	 * Update everything from the models.
	 */
	private void updateContents() {
		setString(model.getFormattedMemoryUsageText());
		if(colors != null) {
			setForeground(colors.getColor(model.getMemoryUsageRatio()));
		}
	}

	/**
	 * Set the preferred, minimum, and maximum sizes of the monitor.
	 */
	private void setDefaultSizes(int width, int height) {
		setPreferredSize(new Dimension(width, height));
		setMaximumSize(new Dimension(width, height));
		setMinimumSize(new Dimension(width, height));
	}
}
