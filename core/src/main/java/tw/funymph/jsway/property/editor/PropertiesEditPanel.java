/* PropertiesEditPanel.java created on 2011/10/13
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

import static java.awt.BorderLayout.CENTER;
import static java.awt.Color.white;
import static javax.swing.Box.createGlue;
import static javax.swing.Box.createVerticalStrut;
import static javax.swing.BoxLayout.PAGE_AXIS;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.DefaultEditableProperties;
import tw.funymph.jsway.property.EditableProperties;

/**
 * A GUI panel that displays the aligned editors for a suite of editable properties.
 * Since the panel can do self detachment, when the panel is no longer used, please
 * invoke {@link #detach()} method to detach the listener dependency between the panel
 * and the editable properties.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class PropertiesEditPanel extends JPanel implements SelfDetachable {

	private static final long serialVersionUID = -5748497745456380024L;

	private static final int STRUT_HEIGHT = 1;

	private JPanel contentPane;
	private InternalPropertiesEditPanel[] tables;

	private PropertyNameLabel nameLabel;
	private PropertyTableCellEditor editorFactory;

	/**
	 * Construct an empty <code>PropertiesEditPanel</code>.
	 */
	public PropertiesEditPanel() {
		this(new DefaultEditableProperties(""));
	}

	/**
	 * Construct a <code>PropertiesEditPanel</code> instance for the specified properties.
	 * 
	 * @param properties the properties
	 */
	public PropertiesEditPanel(EditableProperties properties) {
		this(new EditableProperties[] { properties });
	}

	/**
	 * Construct a <code>PropertiesEditPanel</code> instance for the specified properties.
	 * 
	 * @param properties the properties
	 */
	public PropertiesEditPanel(EditableProperties[] properties) {
		nameLabel = new PropertyNameLabel(white);
		editorFactory = new PropertyTableCellEditor();
		contentPane = new JPanel();
		contentPane.setLayout(new BoxLayout(contentPane, PAGE_AXIS));
		setProperties(properties);
		setLayout(new BorderLayout());
		add(new JScrollPane(contentPane), CENTER);
	}

	/**
	 * Set the width of the name column.
	 * 
	 * @param width the width of the name column.
	 */
	public void setNameColumnWidth(int width) {
		if(tables != null) {
			for(InternalPropertiesEditPanel table : tables) {
				table.setNameColumnWidth(width);
			}
		}
	}

	/**
	 * Set the width of the value column.
	 * 
	 * @param width the width of the value column.
	 */
	public void setValueColumnWidth(int width) {
		if(tables != null) {
			for(InternalPropertiesEditPanel table : tables) {
				table.setValueColumnWidth(width);
			}
		}
	}

	/**
	 * Set the groups of the properties to be edited in the panel.
	 * 
	 * @param properties the groups of the properties to be edited
	 */
	public void setProperties(List<EditableProperties> properties) {
		setProperties(properties.toArray(new EditableProperties[0]));
	}

	/**
	 * Set the groups of the properties to be edited in the panel.
	 * 
	 * @param properties the groups of the properties to be edited
	 */
	public void setProperties(EditableProperties[] properties) {
		reset();
		if(properties != null) {
			int size = properties.length;
			tables = new InternalPropertiesEditPanel[size];
			for(int index = 0; index < size; index++) {
				tables[index] = new InternalPropertiesEditPanel(properties[index], nameLabel, editorFactory);
				contentPane.add(tables[index]);
			}
		}
		contentPane.add(createGlue());
		contentPane.add(createVerticalStrut(STRUT_HEIGHT));
	}

	/**
	 * Register the new editor for the new property type.
	 * 
	 * @param type the new property type
	 * @param editor the new editor
	 */
	public void registerEditor(Class<?> type, PropertyEditor editor) {
		editorFactory.registerEditor(type, editor);
	}

	/**
	 * Set the color of the grid.
	 * 
	 * @param color the color of the grid
	 */
	public void setGridColor(Color color) {
		if(tables != null) {
			for(InternalPropertiesEditPanel table : tables) {
				table.setGridColor(color);
			}
		}
	}

	/**
	 * Set the background color of the name labels.
	 * 
	 * @param color the background color the name labels.
	 */
	public void setNameLabelBackgroundColor(Color color) {
		nameLabel.setBackgroundColor(color);
		if(tables != null) {
			for(InternalPropertiesEditPanel table : tables) {
				table.invalidate();
			}
		}
	}

	/**
	 * Clear all editors and detach all dependencies between editors and properties.
	 */
	public void reset() {
		stopEditing();
		detach();
		contentPane.removeAll();
		tables = null;
	}

	@Override
	public void detach() {
		if(tables != null) {
			for(InternalPropertiesEditPanel table : tables) {
				table.detach();
			}
		}
	}

	@Override
	public void removeAll() {
		detach();
		super.removeAll();
	}

	/**
	 * Stop the editing (changing the cell editor to cell renderer).
	 */
	private void stopEditing() {
		if(tables != null) {
			for(InternalPropertiesEditPanel table : tables) {
				table.stopEditing();
			}
		}
	}
}
