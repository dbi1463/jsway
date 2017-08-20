/* PropertiesTableModel.java created on 2012/9/25
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

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import tw.funymph.jsway.MultiLanguageSupport;
import tw.funymph.jsway.MultiLanguageSupportListener;
import tw.funymph.jsway.SelfDetachable;
import tw.funymph.jsway.property.EditableProperties;
import tw.funymph.jsway.property.EditablePropertiesListener;
import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;

/**
 * A class that transforms the interface from {@link EditableProperties} to {@link TableModel}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class PropertiesTableModel extends AbstractTableModel implements SelfDetachable, EditablePropertyListener, EditablePropertiesListener, MultiLanguageSupportListener {

	private static final long serialVersionUID = -2809252046168381982L;

	public static final int COLUMNS = 2;
	public static final int NAME_COLUMN_INDEX = 0;
	public static final int VALUE_COLUMN_INDEX = 1;

	private EditableProperties editingProperties;

	/**
	 * Construct an empty <code>EditablePropertiesTableModel</code> instance.
	 */
	PropertiesTableModel() {
		this(null);
	}

	/**
	 * Construct a <code>EditablePropertiesTableModel</code> instance with
	 * the specified properties.
	 * 
	 * @param properties the properties to be transformed
	 */
	PropertiesTableModel(EditableProperties properties) {
		setProperties(properties);
	}

	/**
	 * Set the properties to be transformed.
	 * 
	 * @param properties the properties to be transformed
	 */
	public void setProperties(EditableProperties properties) {
		if(editingProperties != null) {
			unregisterListeners();
		}
		editingProperties = properties;
		registerListeners();
	}


	@Override
	public void detach() {
		unregisterListeners();
	}

	@Override
	public int getRowCount() {
		return (editingProperties != null)? editingProperties.size() : 0;
	}

	@Override
	public int getColumnCount() {
		return COLUMNS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex < getRowCount()) {
			switch(columnIndex) {
			case NAME_COLUMN_INDEX:
				return editingProperties.getProperty(rowIndex).getDisplayText();
			case VALUE_COLUMN_INDEX:
				return editingProperties.getProperty(rowIndex);
			};
		}
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return (columnIndex == VALUE_COLUMN_INDEX) && editingProperties.getProperty(rowIndex).isUIEditable();
	}

	@Override
	public Class<?> getColumnClass(int column) {
		return (column == 0)? String.class : EditableProperty.class;
	}

	@Override
	public void propertiesChanged(String key, EditableProperties properties) {
		fireTableDataChanged();
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		fireTableDataChanged();
	}

	@Override
	public void displayTextChanged(MultiLanguageSupport source) {
		fireTableDataChanged();
	}

	/**
	 * Register the panel as the listener for all editable properties.
	 */
	private void registerListeners() {
		editingProperties.addEditablePropertiesListener(this);
		editingProperties.addMultiLanguageSupportListener(this);
		int propertiesCount = editingProperties.size();
		for(int index = 0; index < propertiesCount; index++) {
			EditableProperty property = editingProperties.getProperty(index);
			property.addEditablePropertyListener(this);
			property.addMultiLanguageSupportListener(this);
		}
	}

	/**
	 * Unregister the panel as the listener for all editable properties.
	 */
	private void unregisterListeners() {
		editingProperties.removeEditablePropertiesListener(this);
		editingProperties.removeMultiLanguageSupportListener(this);
		int propertiesCount = editingProperties.size();
		for(int index = 0; index < propertiesCount; index++) {
			EditableProperty property = editingProperties.getProperty(index);
			property.removeEditablePropertyListener(this);
			property.removeMultiLanguageSupportListener(this);
		}
	}
}
