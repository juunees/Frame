package database.frame.models;

 //import database.frame.DbHelper;
	import java.text.Collator;
	import java.util.Date;
	import java.util.Locale;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	//import java.text.DateFormat;


	@SuppressWarnings("rawtypes")
	
	public class Readers implements Comparable {
		
		private int idReader;
		private String fullName;
		private String job;
		private String phoneNumber;
		private String address;
		private Date expirationDate;
		
		public Readers(){}
		
		 public Readers(ResultSet rs) throws SQLException {
		        setIdReader(rs.getInt(1));
		        setFullName(rs.getString(2));
		        setJob(rs.getString(3));
		        setPhoneNumber(rs.getString(4));
		        setAddress(rs.getString(5));
		        setExpirationDate(rs.getDate(6));
		    }

		
		
		public int getReaderId(){
			return idReader;
		}
		public void setIdReader(int idReader)
		{
			this.idReader = idReader;
		}
		
		
		public String getFullName(){
			return fullName;
		}
		public void setFullName(String fullName){
			this.fullName = fullName;
		}
		
		
		
		public String getJob(){
			return job;
		}
		public void setJob(String job){
			this.job = job;
		}
		
		
		public String getPhoneNumber(){
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber){
			this.phoneNumber = phoneNumber;
		}
		
		
		public String getAddress(){
			return address;
		}
		public void setAddress(String address){
			this.address = address;
		}
		
		
		public Date getExpirationDate(){
			return expirationDate;
		}
		public void setExpirationDate(Date expirationDate)
		{
			this.expirationDate = expirationDate;
		}
		
		
		
	
	   /* public int getCountBooks(final String type) {
	        try {
	            DbHelper dbHelper = new DbHelper("test", "dev", "dev");
	            dbHelper.connectToDB();
	            ResultSet rs = dbHelper.getResultExecuteQuery(
	                    "select count(distinct(publication_id)) from subscriptions " +
	                    "where subscriber_id = " + getSubscriberId() +
	                    " and publication_id in (select id from publications where publication_type_id = " +
	                            "(select id from publication_types where name = '" + type +"'));");
	            if (rs.next()) {
	                return rs.getInt(1);
	            }
	            return 0;
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }
	        return 0;
	    }
	    
	    */
	    public String toString() {
	        return fullName;
	    }

		
		public int compareTo(Object obj){
			
			Collator c = Collator.getInstance(new Locale("ru"));
			 c.setStrength(Collator.PRIMARY);
		        return c.compare(this.toString(), obj.toString());
		}
		
	}


