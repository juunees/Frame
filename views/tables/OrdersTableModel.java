package database.frame.views.tables;

import database.frame.models.Orders;
import java.text.DateFormat;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class OrdersTableModel extends AbstractTableModel {

 
	@SuppressWarnings("rawtypes")
	private Vector orders;

    public OrdersTableModel(@SuppressWarnings("rawtypes") Vector orders) {
        this.orders = orders;
    }

    public int getRowCount() {
        if (orders != null) {
            return orders.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 5;
    }

    public String getColumnName(int column) {
        String[] colNames = {"Книга", "Читатель", "Тип заказа", "Дата заказа"};
        return colNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (orders != null) {
        	Orders sc = (Orders) orders.get(rowIndex);

            switch (columnIndex) {
                case 0:
                   return sc.getBooks_title();    
                case 1:
                   return sc.getReadersInfo();
                case 2:
                    return sc.getType();
                case 3:
                    return DateFormat.getDateInstance(DateFormat.SHORT).format(sc.getDataOfOrder());
            }
        }
        return null;
    }

    public Orders getOrder(int rowIndex) {
        if (orders != null) {
            if (rowIndex < orders.size() && rowIndex >= 0) {
                return (Orders) orders.get(rowIndex);
            }
        }
        return null;
    }
}