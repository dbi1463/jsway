/* MACAddressPropertyEditor.java created on 2011/10/13
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

import static java.awt.Color.white;
import static javax.swing.border.EtchedBorder.LOWERED;
import static tw.funymph.jsway.property.MACAddressProperty.ADDRESS_FIELDS;
import static tw.funymph.jsway.property.MACAddressProperty.LAST_FIELD_INDEX;
import static tw.funymph.jsway.property.MACAddressProperty.MAC_ADDRESS_RADIX;
import static tw.funymph.jsway.utils.ObjectUtilities.requireNonNull;

import java.awt.GridLayout;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.text.MaskFormatter;

import tw.funymph.jsway.property.EditableProperty;
import tw.funymph.jsway.property.EditablePropertyEvent;
import tw.funymph.jsway.property.EditablePropertyListener;
import tw.funymph.jsway.property.MACAddressProperty;
import tw.funymph.jsway.property.NumberProperty;
import tw.funymph.jsway.property.codecs.EditablePropertyCodec;
import tw.funymph.jsway.utils.NumberUtilities;

/**
 * A GUI editor to edit the value of a {@link MACAddressProperty}.
 * 
 * @author Pin-Ying Tu
 * @version 1.2
 * @since 1.0
 */
public class MACAddressPropertyEditor extends JPanel implements EditablePropertyListener, PropertyEditor {

	private static final long serialVersionUID = -2405004633440035795L;

	private static final String NULL_MAC_ADDRESS_EXCEPTION = "Cannot set a null MAC address";

	private static final int DEFAULT_ROWS = 1;

	private MACAddressCodec addressCodec;
	private MACAddressProperty addressProperty;

	private GridLayout gridLayout;
	private SuffixedTextEditor[] editors;

	/**
	 * Construct a <code>MACAddressPropertyEditor</code> instance as a factory.
	 */
	MACAddressPropertyEditor() {}

	/**
	 * Construct a <code>MACAddressPropertyEditor</code> with the specified address.
	 * 
	 * @param address the address to be edited
	 */
	public MACAddressPropertyEditor(MACAddressProperty address) {
		addressProperty = requireNonNull(address, NULL_MAC_ADDRESS_EXCEPTION);
		addressProperty.addEditablePropertyListener(this);
		try {
			addressCodec = new MACAddressCodec();
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
	public void propertyChanged(EditablePropertyEvent event) {
		setEnabled(event.getSource().isUIEditable());
	}

	@Override
	public JComponent getEditor(EditableProperty property) {
		if(property instanceof MACAddressProperty) {
			return new MACAddressPropertyEditor((MACAddressProperty)property);
		}
		return null;
	}

	/**
	 * Initialize all components.
	 */
	private void initializeComponents() {
		editors = new SuffixedTextEditor[ADDRESS_FIELDS];
		gridLayout = new GridLayout(DEFAULT_ROWS, ADDRESS_FIELDS);
		setLayout(gridLayout);
		setBackground(white);
		setBorder(new EtchedBorder(LOWERED));
		NumberProperty<Integer>[] parts = addressProperty.getEditableAddressParts();
		for(int index = 0; index < ADDRESS_FIELDS; index++) {
			String suffix = (index != LAST_FIELD_INDEX)? addressProperty.getSeparator() : null;
			editors[index] = new SuffixedTextEditor(parts[index], addressCodec, addressCodec, suffix);
			add(editors[index]);
		}
		setEnabled(addressProperty.isUIEditable());
	}

	/**
	 * A codec that converts the number to hex address, and converts the hex address to the number.
	 * 
	 * @author Pin-Ying Tu
	 * @version 1.2
	 * @since 1.0
	 */
	static class MACAddressCodec extends MaskFormatter implements EditablePropertyCodec {

		private static final long serialVersionUID = 8630199272423875765L;

		private static final String MASK = "**";
		private static final String VALID_CHARACTERS = "0123456789abcdefABCDEF";

		/**
		 * Construct a <code>MACAddressCodec</code> instance.
		 * 
		 * @throws ParseException if the mask is illegal
		 */
		public MACAddressCodec() throws ParseException {
			super(MASK);
			setValidCharacters(VALID_CHARACTERS);
			setCommitsOnValidEdit(true);
		}

		@Override
		public Object decode(String text) {
			return Integer.parseInt(text, MAC_ADDRESS_RADIX);
		}

		@Override
		public String encode(Object value) {
			return NumberUtilities.toHexString(((Number)value).intValue());
		}
	}
}
