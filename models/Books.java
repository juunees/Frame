package database.frame.models;

//import database.frame.DbHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Locale;


public class Books {

	private int idBook;
	private String title;
	private String author;
	private int yearOfPublish;
	private int price;
	private int countOfOrders;
	private String publishHouse;
	//private int books_type_id;
	
	
	public Books(){}
	public Books(ResultSet rs) throws SQLException{
		
		setIdBook(rs.getInt(1));
		setTitle(rs.getString(2));
		setAuthor(rs.getString(3));
		setYearOfPublish(rs.getInt(4));
		setPrice(rs.getInt(5));
		setCountOfOrders(rs.getInt(6));
		setPublishHouse(rs.getString(7));
		//setBooksType_id(rs.getInt(8));
			
	}
	
	public int getIdBook() {
	        return idBook;
	    }
	 public void setIdBook(int idBook) {
	        this.idBook = idBook;
	    }
	 
	 public String getTitle() {
	        return title;
	    }
	 public void setTitle(String title) {
	        this.title = title;
	    }
	 
     public String getAuthor() {
	        return author;
	    }
	 public void setAuthor(String author) {
	        this.author = author;
	    }
	
	 public int getYearOfPublish() {
	        return yearOfPublish;
	    }
	 public void setYearOfPublish(int yearOfPublish) {
	        this.yearOfPublish = yearOfPublish;
	    }
	 
	 
	  public int getCountOfOrders() {
	        return countOfOrders;
	    }
	 public void setCountOfOrders(int countOfOrders) {
	        this.countOfOrders = countOfOrders;
	    }
	 
	 
	  public int getPrice() {
	        return price;
	    }
	 public void setPrice(int price) {
	        this.price = price;
	    }
	
	 
	  public String getPublishHouse() {
	        return publishHouse;
	    }
	 public void setPublishHouse(String publishHouse) {
	        this.publishHouse = publishHouse;
	    }
	 
	 
	/* public int getBooksType_id() {
	        return books_type_id;
	    }
	*/ 
	 

	 /*public String getType() {
	        try {
	            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
	            dbHelper.connectToDB();
	            ResultSet rs = dbHelper.getResultExecuteQuery(
	                    "select title from books_types " +
	                            "where id = " + getBooksType_id() + ";");
	            if (rs.next()) {
	                return rs.getString(1);
	            }
	            return null;
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }
	        return null;
	    }
	 
	 */

	/* public void setBooksType_id(int books_type_id) {
	        this.books_type_id = books_type_id;
	    }
*/
	 
	 public String toString(){
		 return title ;
	 }
	 
	 public int comperTo(Object obj)
	 {
		 Collator c = Collator.getInstance(new Locale("ru"));
		 c.setStrength(Collator.PRIMARY);
		 return c.compare(this.toString(), obj.toString());
	 }
}
