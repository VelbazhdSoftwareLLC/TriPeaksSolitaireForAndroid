package eu.veldsoft.tri.peaks;

import java.text.DecimalFormat;

import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * @author
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