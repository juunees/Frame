package database.frame.views.tables;


import database.frame.models.Books;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

@SuppressWarnings("serial")
public class BooksTableModel extends AbstractTableModel {

   
	private Vector<?> books;

    public BooksTableModel(Vector<?> books) {
        this.books = books;
    }

    public int getRowCount() {
        if (books != null) {
            return books.size();
        }
        return 0;
    }

    public int getColumnCount() {
        return 7;
    }

    public String getColumnName(int column) {
        String[] colNames = {"Название", "Автор", "Год публикации", "Цена", "Число заказов", "Издательство"};
        return colNames[column];
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (books != null) {
        	Books pub = (Books)books.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return pub.getAuthor() ;
                case 1:
                    return pub.getTitle();
                case 2:
                    return pub.getYearOfPublish();
                case 3:
                    return pub.getPrice();
                case 4:
                    return pub.getCountOfOrders();
                case 5:
                    return pub.getPublishHouse();
              //  case 6:
                  //  return pub.getType();
                
            }
        }
        return null;
 
    }

    public Books getBook(int rowIndex) {
        if (books != null) {
            if (rowIndex < books.size() && rowIndex >= 0) {
                return (Books) books.get(rowIndex);
            }
        }
        return null;
    }
}

