package eu.veldsoft.tri.peaks;

import java.text.DecimalFormat;

import javax.swing.table.DefaultTableCellRenderer;

class CurrencyRenderer extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CurrencyRenderer() {
		super();
	}

	public void setValue(Object value) {
		if (value == null)
			setText("");
		DecimalFormat format = null;
		double num = 0.0;
		if (value.getClass() == Integer.class) {
			format = new DecimalFormat("$###,###");
			num = ((Integer) value).intValue();
		} else if (value.getClass() == Double.class) {
			format = new DecimalFormat("$###,##0.00");
			num = ((Double) value).doubleValue();
		} else {
			setText("");
			return;
		}
		if (format == null) {
			setText("");
			return;
		}
		setText(format.format(num));
	}
}