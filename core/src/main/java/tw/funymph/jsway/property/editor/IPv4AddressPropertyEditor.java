/* IPAddressPropertyEditor.java created on 2011/10/13
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

import static javax.swing.border.EtchedBorder.LOWERED;
import static tw.funymph.jsway.property.IPv4AddressProperty.*;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.NumberFormatter;

import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;
import tw.funymph.jsway.property.IPv4AddressProperty;
import tw.funymph.jsway.property.NumberProperty;

/**
 * A GUI editor to edit the value of a {@link IPv4AddressProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class IPv4AddressPropertyEditor extends JPanel implements EditablePropertyListener, PropertyEditor {

	private static final long serialVersionUID = 6505120059158402363L;

	private static final String NULL_IP_ADDRESS_EXCEPTION = "Cannot set a null IP address";

	private static final int DEFAULT_ROWS = 1;

	private IPv4AddressProperty addressProperty;

	private GridLayout gridLayout;
	private IPAddressFormatter formatter;
	private SuffixedTextEditor[] editors;

	/**
	 * Construct a <code>IPv4AddressPropertyEditor</code> instance as a factory.
	 */
	IPv4AddressPropertyEditor() {}

	/**
	 * Construct a <code>IPv4AddressPropertyEditor</code> with the specified address.
	 * 
	 * @param address the address to be edited
	 */
	public IPv4AddressPropertyEditor(IPv4AddressProperty address) {
		addressProperty = requireNonNull(address, NULL_IP_ADDRESS_EXCEPTION);
		formatter = new IPAddressFormatter();
		addressProperty.addEditablePropertyListener(this);
		initializeComponents();
	}

	@Override
	public void setEnabled(boolean enabled) {
		if(editors != null) {
			for(SuffixedTextEditor editor : editors) {
				editor.setEnabled(enabled);
			}
		}
	}

	@Override
	public void detach() {
		addressProperty.removeEditablePropertyListener(this);
		if(editors != null) {
			for(SuffixedTextEditor editor : editors) {
				editor.detach();
			}
		}
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof IPv4AddressProperty) {
			return new IPv4AddressPropertyEditor((IPv4AddressProperty)property);
		}
		return null;
	}

	@Override
	public void propertyChanged(EditablePropertyEvent event) {
		setEnabled(event.getSource().isUIEditable());
	}

	/**
	 * Initialize all components.
	 */
	private void initializeComponents() {
		editors = new SuffixedTextEditor[ADDRESS_FIELDS];
		gridLayout = new GridLayout(DEFAULT_ROWS, ADDRESS_FIELDS);
		setLayout(gridLayout);
		setBackground(Color.WHITE);
		setBorder(new EtchedBorder(LOWERED));
		NumberProperty<Integer>[] addresses = addressProperty.getEditableAddressParts();
		for(int index = 0; index < ADDRESS_FIELDS; index++) {
			String suffix = (index != LAST_FIELD_INDEX)? DOT_SUFFIX : null;
			editors[index] = new SuffixedTextEditor(addresses[index], formatter, suffix);
			add(editors[index]);
		}
		setEnabled(addressProperty.isUIEditable());
	}

	/**
	 * The number formatter for the IP address
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.0
	 */
	static class IPAddressFormatter extends NumberFormatter {

		private static final long serialVersionUID = -8017705545816802535L;

		/**
		 * Construct a <code>IPAddressFormatter</code> instance with
		 * the default maximum and minimum values.
		 */
		public IPAddressFormatter() {
			super();
			setMinimum(IPv4AddressProperty.DEFAULT_MINIMUM);
			setMaximum(IPv4AddressProperty.DEFAULT_MAXIMUM);
			setCommitsOnValidEdit(true);
		}
	}
}
