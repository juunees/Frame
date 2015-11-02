package database.frame.views.tables;


import database.frame.models.Readers;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class ReadersTableModel extends AbstractTableModel {

    @SuppressWarnings("rawtypes")
	private Vector readers;

    @SuppressWarnings("rawtypes")
	public ReadersTableModel(Vector readers) {
       this.readers = readers;
    }

    public int getRowCount() {
        if (readers != null) {
            return readers.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int column) {
        String[] colNames = {"ФИО", "Место работы", "Телефон","Адрес","Дата окончания читательского билета"};
        return colNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (readers != null) {
        	Readers sc = (Readers) readers.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return sc.getFullName();
                case 1:
                    return sc.getJob();
                case 2:
                    return sc.getPhoneNumber();
                case 3:
                    return sc.getAddress();
                 case 4:
                    return sc.getExpirationDate();
                    
            }
        }
        return null;
    }

    public Readers getReader(int rowIndex) {
        if (readers != null) {
            if (rowIndex < readers.size() && rowIndex >= 0) {
                return (Readers) readers.get(rowIndex);
            }
        }
        return null;
    }
}
