package database.frame;


import java.sql.*;


public class DbHelper {

    private static String URL_DATABASE = "jdbc:postgresql://localhost:5432/";
    private String db_name;           //название базы данных
    private String db_user;           //логин пользователя бд
    private String db_password;       //пароль пользователя бд
    private Connection connection;

    public DbHelper(String db_name, String db_user, String db_password) {
        this.db_name = db_name;
        this.db_user = db_user;
        this.db_password = db_password;
        this.connection = null;
    }

    /*
     * Подключение к бд
     */
    
    public void connectToDB() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(URL_DATABASE + this.db_name, this.db_user, this.db_password);
            System.out.println(" connect to DB");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void closeConnectionToDB() {

        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    
    public ResultSet getResultExecuteQuery(String query) {
        try {
            Statement statement = this.connection.createStatement();
            return statement.executeQuery(query);
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
            return null;
        }
    }

    public void createTable(String query) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Таблица добавлена");
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        }

    }

    public void createTrigger(String query) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(query);
            System.out.println("Тригер добавлен");
        } catch (Exception ex) {
        	System.out.println(ex.getMessage());
        }
        }
    

        public static void main(String[] args) {

            DbHelper db_helper = new DbHelper("test", "dev", "dev");

            db_helper.connectToDB();

            /*
             * create table of Readers
             */
            db_helper.createTable(
                    "CREATE TABLE IF NOT EXISTS readers ( " +
                            "id_reader               bigserial     UNIQUE PRIMARY KEY,"     +
                            "full_name        character varying     NOT NULL," +
                            "job              character varying     NOT NULL,"        +
                            "phoneNumber      character varying     NOT NULL,"         +
                            "address          character varying     NOT NULL,"         +
                            "expiration_Date  date                  NOT NULL"         +
                            ")");

            
           
            
            
            /*
             * create table of Books
             */
            db_helper.createTable(
                    "CREATE TABLE IF NOT EXISTS books ( " +
                            "id_book                bigserial           UNIQUE PRIMARY KEY," +
                            "title             character varying   UNIQUE NOT NULL," +
                            "author             character varying   NOT NULL," +
                            "yearOfPublish     integer             NOT NULL DEFAULT 0," +
                            "price             real                NOT NULL," +
                            "countOfOrders     integer             NOT NULL," +
                            "publishHouse      character varying   NOT NULL" +
                            ")");

            /**
            * create table of Orders
            */
            db_helper.createTable(
                    "CREATE TABLE IF NOT EXISTS orders ( "  +
                            "id_order                   bigserial                       UNIQUE PRIMARY KEY,"         +
                            "reader_id            integer REFERENCES readers(id_reader)      NOT NULL,"              +
                            "book_id              integer REFERENCES books(id_book)        NOT NULL,"                +
                            "order_type_id              integer REFERENCES orders_types,"                                 +
                             "dataOfOrder         date                                     NOT NULL"                 +
                    ")");
            
            db_helper.createTable("" +
                    "CREATE TABLE IF NOT EXISTS orders_types (" +
                    "id        bigserial             PRIMARY KEY, " +
                    "name        character varying      NOT NULL)");
            
            
            /*
             * Создание триггера при добавлении записи в таблицу подписок (увеличивает число читателей в таблице заказов)  
            */
            
            db_helper.createTrigger(
                    "CREATE FUNCTION increase_countOfOrders() RETURNS trigger AS " +
                    "$update_countOfOrders$ " +
                    "BEGIN " +
                    "UPDATE books SET countOfOrders = countOfOrders + 1 WHERE id = NEW.book_id; " +
                    "RETURN NULL; " +
                    "END;" +
                    "$update_countOfOrders$" +
                    "language plpgsql;" +
                    "CREATE TRIGGER after_new_orders " +
                    "AFTER INSERT ON orders " +
                    "FOR EACH ROW EXECUTE PROCEDURE increase_countOfOrders();"
            );

            /*
             * Создание триггера при удалении записи в таблицу подписок (уменьшает число подписчиков в таблице изданий)
             */ 
            

            db_helper.createTrigger(
                    "CREATE FUNCTION decrease_countOfOrders() RETURNS trigger AS " +
                    "$decrease_countOfOrders$ " +
                    "BEGIN " +
                    "UPDATE orders SET countOfOrders = countOfOrders - 1 where id = OLD.book_id; " +
                    "RETURN NULL; " +
                    "END;" +
                    "$decrease_countOfOrders$ " +
                    "language plpgsql;" +
                    "CREATE TRIGGER before_delete_orders " +
                    "AFTER DELETE ON orders " +
                    "FOR EACH ROW EXECUTE PROCEDURE decrease_countOfOrders();"
            );



            db_helper.closeConnectionToDB();

        }
}
