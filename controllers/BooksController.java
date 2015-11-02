package database.frame.controllers;

/*
* класс контроллера модели кнги
* реализация методов добавления, изменения, удаления издания, получения списка изданий
*/

import database.frame.models.Books;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;



public class BooksController {

    private static String URL_DATABASE = "jdbc:postgresql://localhost:5432/";
    private static BooksController instance;
    private Connection connection;
    private static String DATABASE_NAME = "test";
    private static String DATABASE_USER = "dev";
    private static String DATBASE_PASSWORD = "dev";

    public BooksController() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL_DATABASE + DATABASE_NAME, DATABASE_USER, DATBASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized BooksController getInstance() throws Exception{
        if (instance == null) {
            instance = new BooksController();
        }
        return instance;
    }


   public void updateBook(Books book) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE books SET " +
                            "title=?, author=?, yearOfPublish=?, price=?, countOfOrders=?, publishHouse=? )"+ 
                            "WHERE id_book=?");
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYearOfPublish());
            stmt.setDouble(4, book.getPrice());
            stmt.setInt(5, book.getCountOfOrders());
            stmt.setString(6, book.getPublishHouse());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertBook(Books book) throws SQLException {
        PreparedStatement stmt = null;
        try {

            stmt = connection.prepareStatement(
                    "INSERT INTO books " +
                            "(title, author, yearOfPublish, price, countOfOrders, publishHouse)"+ 
                            "VALUES (?, ?, ?, ?, ?, ?)");
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setInt(3, book.getYearOfPublish());
            stmt.setDouble(4, book.getPrice());
            stmt.setInt(5, book.getCountOfOrders());
            stmt.setString(6, book.getPublishHouse());
       
            stmt.execute();
            if (stmt != null) {
                stmt.close();
            }
        } finally {
        	if (stmt != null) {
                stmt.close();
        	}
        }

    }

    public void deleteBook(Books book) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM books WHERE id_book=?");
            stmt.setInt(1, book.getIdBook());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
//получение списка книг
    
    public Collection<Books> getAllBooks() throws SQLException {
        Collection<Books> books = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM books;");
            while (rs.next()) {
                Books book = new Books(rs);
                books.add(book);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return books;
    }

}
 
