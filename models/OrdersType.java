package database.frame.models;
 
//import database.frame.DbHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Collator;
import java.util.Locale;


public class OrdersType implements Comparable<Object>  {



    private int orders_type_id;
    private String name;

    public OrdersType() {}
    public OrdersType(ResultSet rs) throws SQLException {
    	
        setOrdersType_id(rs.getInt(1));
        setName(rs.getString(2));
    }

        
	public void setOrdersType_id(int orders_type_id) {
        this.orders_type_id = orders_type_id;
    }

    public int getOrdersType_id() {
        return orders_type_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }

    public int compareTo(Object obj) {
        Collator c = Collator.getInstance(new Locale("ru"));
        c.setStrength(Collator.PRIMARY);
        return c.compare(this.toString(), obj.toString());
    }

	
}
