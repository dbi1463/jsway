/* PropertyTableCellEditor.java created on 2012/4/29
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

import static tw.funymph.jsway.property.editor.DefaultPropertyEditorFactory.createDefaultPropertyEditorFactory;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import tw.funymph.jsway.property.EditableProperty;

/**
 * A facade that creates renders and editors for different editable properties.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
class PropertyTableCellEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = -5027350701685486654L;

	private Object propertyValue;
	private PropertyEditorFactory editorFactory;

	/**
	 * Construct a <code>PropertyTableCellEditor</code> instance.
	 */
	public PropertyTableCellEditor() {
		editorFactory = createDefaultPropertyEditorFactory();
	}

	/**
	 * Register the new editor factory for the new property type.
	 * 
	 * @param type the new property type
	 * @param editor the new editor factory
	 */
	public void registerEditor(Class<?> propertyType, PropertyEditor editor) {
		editorFactory.registerEditor(propertyType, editor);
	}

	@Override
	public Object getCellEditorValue() {
		return propertyValue;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		return getTableCellEditorComponent(table, value, isSelected, row, column);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int column) {
		propertyValue = value;
		EditableProperty property = (EditableProperty)value;
		JComponent editor = editorFactory.getEditor(property);
		if(editor != null && selected && property.isUIEditable()) {
			editor.requestFocus();
		}
		return editor;
	}
}
