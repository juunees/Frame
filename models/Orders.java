package database.frame.models;


import java.text.DateFormat;
import java.text.Collator;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import database.frame.DbHelper;
//import database.frame.controllers.OrdersController;


public class Orders{
	
	private int idOrder;
	private int idBook;
	private int idReader;
	private int ordersTypeId;
	private Date dataOfOrder;
	
	public Orders(){}
	public Orders(ResultSet rs) throws SQLException{
		
		setIdOrder(rs.getInt(1));
		setIdBook(rs.getInt(2));
		setIdReader(rs.getInt(3));
		setOrdersType_id(rs.getInt(4));
		setDataOfOrder(rs.getDate(5));	
	}
	
	public int getIdOrder() {
        return idOrder;
    }
    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
 
	public int getIdBook() {
	        return idBook;
	    }
	 public void setIdBook(int idBook) {
	        this.idBook = idBook;
	    }
	 
	 

	public int getIdReader() {
		        return idReader;
		    }
	public void setIdReader(int idReader) {
		        this.idReader = idReader;
		    }

		 
	
	
	public int getOrdersType_id() {
		        return ordersTypeId;
		    }

   
	 public String getType() {
	        try {
	            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
	            dbHelper.connectToDB();
	            ResultSet rs = dbHelper.getResultExecuteQuery(
	                    "select name from orders_types " +
	                            "where id = " + getOrdersType_id() + ";");
	            if (rs.next()) {
	                return rs.getString(1);
	            }
	            return null;
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }
	        return null;
	    }

	 
	    public void setOrdersType_id(int orders_type_id) {
	        this.ordersTypeId = orders_type_id;
	    }


		 
	 public Date getDataOfOrder() {
		        return dataOfOrder;
		    }
	public void setDataOfOrder(Date dataOfOrder) {
		        this.dataOfOrder = dataOfOrder;
		    }
	
	
    public String getReadersInfo() {

        try {
            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
            dbHelper.connectToDB();
            String query = "select full_name, job from readers where id_reader = " + getIdReader() + ";";
            ResultSet rs = dbHelper.getResultExecuteQuery(query);
            if (rs.next()) {
                String info = rs.getString(1) + ", место работы " + String.valueOf(rs.getInt(2));
                return info;
            }
            return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

	
	/*public String getSubscribersCity() {

        try {
            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
            dbHelper.connectToDB();
            String query = "select city from subscribers where id = " + getSubscriber_id() + ";";
            ResultSet rs = dbHelper.getResultExecuteQuery(query);
            if (rs.next()) {
                String city = rs.getString(1);
                return city;
            }
            return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }*/


    /*public String getReadersFull_name() {

        try {
            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
            dbHelper.connectToDB();
            String query = "select full_name from readers where id = " + getIdReader() + ";";
            ResultSet rs = dbHelper.getResultExecuteQuery(query);
            if (rs.next()) {
                String full_name = rs.getString(1);
                return full_name;
            }
            return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }*/

    
    
    public String getBooks_title() {
        try {
            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
            dbHelper.connectToDB();
            ResultSet rs = dbHelper.getResultExecuteQuery("select title from books where id_book = " + getIdBook() + ";");
            if (rs.next()) {
                String name = rs.getString(1);
                return name;
            }
            return null;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public String toString() {
        return idReader + " " + idBook + " " + ordersTypeId +  " " + DateFormat.getDateInstance(DateFormat.SHORT).format(dataOfOrder);
    }

    
    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }

}

	
	
	
