/*
 * This file is a part of Tri Peaks Solitaire for Android
 *
 * Copyright (C) 2013-2014 by Valera Trubachev, Christian d'Heureuse, Todor 
 * Balabanov, Ina Baltadzhieva
 *
 * Tri Peaks Solitaire for Android is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 *
 * Tri Peaks Solitaire for Android is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General 
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with 
 * Tri Peaks Solitaire for Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.veldsoft.tri.peaks;

import java.text.DecimalFormat;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * Start Base64 encoding and decoding code.**NOTE*** This is NOT my code. This
 * code was written by Christian d'Heureuse to provide a more standard base64
 * coder that's fast and efficient. As such, I won't provide comments for that
 * code. Java does NOT provide a Base64 encoder/decoder as part of the API.
 * 
 * @author Christian d'Heureuse
 */

class CurrencyRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public CurrencyRenderer() {
		super();
	}

	/**
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		setText("");

		if (value == null) {
			return;
		}

		double num = 0.0;
		DecimalFormat format = null;

		if (value.getClass() == Integer.class) {
			format = new DecimalFormat("$###,###");
			num = ((Integer) value).intValue();
		} else if (value.getClass() == Double.class) {
			format = new DecimalFormat("$###,##0.00");
			num = ((Double) value).doubleValue();
		} else {
			return;
		}

		if (format != null) {
			setText(format.format(num));
		}
	}
}