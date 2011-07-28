/**
 * 
 */
package mulCal.gui;

import java.math.BigDecimal;

import javax.swing.table.AbstractTableModel;

import mulCal.history.History;
import mulCal.history.History.HistoryItem;
import mulCal.util.KeyException;

/**
 * @author James
 *
 */
public class HistoryTableModel extends AbstractTableModel {

    String[] columnNames = {"ID",
            "Equation",
            "Result",
            "Comment"};

	private History history;
    
	/**
	 * 
	 */
	private static final long serialVersionUID = -2010505193858913003L;

	/**
	 * 
	 */
	public HistoryTableModel(History history) {
		this.history = history;
		this.history.Add("1+1", new BigDecimal("2"));
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
        return this.history.size();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
        return columnNames.length;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
     * Don't need to implement this method unless your table's
     * data can change.
     */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		HistoryItem item;
		try {
			item = history.get(history.lookupIdFromRow(rowIndex));
			if (columnIndex == 0) {
		        return item.id;
			} else if (columnIndex == 1) {
		        return item.equation;
			} else if (columnIndex == 2) {
		        return item.result.doubleValue();
			} else if (columnIndex == 3) {
		        return item.comment;
			} else  {
		        throw new RuntimeException("");
			} 
		} catch (KeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("");
		}
	}
	

    public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex == 3) {
			HistoryItem item;
			try {
				item = history.get(history.lookupIdFromRow(rowIndex));
		        item.comment = (String) value;
			} catch (KeyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
	        throw new RuntimeException("");
		} 
        fireTableCellUpdated(rowIndex, columnIndex);
    }

	/* 
	 * 
	 */
    public String getColumnName(int col) {
        return columnNames[col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class<?> getColumnClass(int columnIndex) {
    	if (columnIndex == 2) {
	        return Double.class;
		} else {
			return String.class;
		} 
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (columnIndex == 3) {
            return true;
        } else {
            return false;
        }
    }

    public void newRow() {
        fireTableRowsInserted(
        	history.size() - 1,
        	history.size() - 1);
    }
}
