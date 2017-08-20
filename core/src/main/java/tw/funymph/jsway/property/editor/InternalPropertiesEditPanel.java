/* InternalPropertiesEditPanel.java created on 2012/9/30
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

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.NORTH;
import static java.awt.Color.lightGray;
import static tw.funymph.jsway.EmptyIcon.createGapIcon;
import static tw.funymph.jsway.property.editor.PropertiesTableModel.NAME_COLUMN_INDEX;
import static tw.funymph.jsway.property.editor.PropertiesTableModel.VALUE_COLUMN_INDEX;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.EditableProperties;
import tw.funymph.jsway.property.EditableProperty;

/**
 * The real editable panel for editable properties. All editors are aligned
 * by a table and have the same widths. This class is designed for internal
 * use only (with package visibility).
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.1
 */
class InternalPropertiesEditPanel extends JPanel implements SelfDetachable {

	private static final long serialVersionUID = -6239115107272254320L;

	private static final int TABLE_ROW_HEIGHT = 25;

	private JTable table;
	private PropertiesNameLabel nameLabel;

	private PropertiesTableModel tableModel;
	private EditableProperties editingProperties;

	/**
	 * Construct a <code>InternalPropertiesEditPanel</code> instance with the
	 * properties to be edited, the name label (a table cell renderer factory),
	 * and the table cell editor factory. 
	 * 
	 * @param properties the properties to be edited
	 * @param label the factory to create name label
	 * @param editor the factory to create property table cell editor 
	 */
	InternalPropertiesEditPanel(EditableProperties properties, PropertyNameLabel label, PropertyTableCellEditor editor) {
		editingProperties = properties;
		tableModel = new PropertiesTableModel(editingProperties);
		table = new JTable(tableModel);
		nameLabel = new PropertiesNameLabel(editingProperties);

		table.setTableHeader(null);
		table.setGridColor(lightGray);
		table.setRowHeight(TABLE_ROW_HEIGHT);
		table.setDefaultRenderer(String.class, label);
		table.setDefaultEditor(EditableProperty.class, editor);
		table.setDefaultRenderer(EditableProperty.class, editor);

		setLayout(new BorderLayout());
		add(table, CENTER);
		add(nameLabel, NORTH);
	}

	/**
	 * Set the color of the grid.
	 * 
	 * @param color the color of the grid
	 */
	public void setGridColor(Color color) {
		table.setGridColor(color);
	}

	/**
	 * Stop the editing (changing the cell editor to cell renderer).
	 */
	public void stopEditing() {
		if(table.getCellEditor() != null) {
		    table.getCellEditor().stopCellEditing();
		}
	}

	/**
	 * Set the width of the name column.
	 * 
	 * @param width the width of the name column.
	 */
	public void setNameColumnWidth(int width) {
		table.getColumnModel().getColumn(NAME_COLUMN_INDEX).setPreferredWidth(width);
	}

	/**
	 * Set the width of the value column.
	 * 
	 * @param width the width of the value column.
	 */
	public void setValueColumnWidth(int width) {
		table.getColumnModel().getColumn(VALUE_COLUMN_INDEX).setPreferredWidth(width);
	}

	@Override
	public void detach() {
		nameLabel.detach();
		tableModel.detach();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMaximumSize() {
		return new Dimension(super.getMaximumSize().width, getPreferredSize().height);
	}

	/**
	 * The name label for the editing properties which will update its displaying
	 * text automatically when the language is changed.
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.1
	 */
	static class PropertiesNameLabel extends JLabel implements SelfDetachable, MultiLanguageSupportListener {

		private static final long serialVersionUID = 3074756548444685988L;

		private EditableProperties editingProperties;

		/**
		 * Construct a <code>PropertiesNameLabel</code> instance for
		 * the specified properties.
		 * 
		 * @param properties the properties source
		 */
		PropertiesNameLabel(EditableProperties properties) {
			super("", createGapIcon(), LEADING);
			if(properties != null) {
				editingProperties = properties;
				setText(editingProperties.getDisplayText());
				editingProperties.addMultiLanguageSupportListener(this);
			}
		}

		public Dimension getPreferredSize() {
			return new Dimension(super.getPreferredSize().width, TABLE_ROW_HEIGHT);
		}

		@Override
		public void displayTextChanged(MultiLanguageSupport source) {
			setText(source.getDisplayText());
		}

		@Override
		public void detach() {
			if(editingProperties != null) {
				editingProperties.removeMultiLanguageSupportListener(this);
			}
		}
	}
}
