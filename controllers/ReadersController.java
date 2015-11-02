package database.frame.controllers;

/*
* класс контроллера модели читател€
* реализаци€ методов добавлени€, изменени€, удалени€ подписчика, получени€ списка подписчиков
*/

import java.sql.*;
import java.util.*;

import database.frame.models.Readers;

import java.sql.Date;

public class ReadersController {

    private static String URL_DATABASE = "jdbc:postgresql://localhost:5432/";
    private static ReadersController instance;
    private Connection connection;
    private static String DATABASE_NAME = "test";
    private static String DATABASE_USER = "dev";
    private static String DATBASE_PASSWORD = "dev";

    public ReadersController() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            
            connection = DriverManager.getConnection(URL_DATABASE + DATABASE_NAME, DATABASE_USER, DATBASE_PASSWORD);
            System.out.println(" ");
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized ReadersController getInstance() throws Exception {
        if (instance == null) {
            instance = new ReadersController();
        }
        return instance;
    }


    public void updateReader(Readers reader) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE readers SET " +
                            "full_name=?, job=?, phoneNumber=? , address=?, expirationDate=? " +
                            "WHERE id_reader=?");
            stmt.setString(1, reader.getFullName());
            stmt.setString(2, reader.getJob());
            stmt.setString(3, reader.getPhoneNumber());
            stmt.setString(4, reader.getAddress());
            stmt.setDate(5, new Date(reader.getExpirationDate().getTime()));
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertReader(Readers reader) throws SQLException {
        PreparedStatement stmt = null;
        try {

            stmt = connection.prepareStatement(
                    "INSERT INTO readers " +
                            "(full_name,job,phoneNumber,address,expiration_Date)" +
                            "VALUES (?, ?, ?, ?)");
            stmt.setString(1, reader.getFullName());
            stmt.setString(2, reader.getJob());
            stmt.setString(3, reader.getPhoneNumber());
            stmt.setString(4, reader.getAddress());
            stmt.setDate(5, new Date(reader.getExpirationDate().getTime()));
            stmt.execute();
            if (stmt != null) {
                stmt.close();
            }
        } finally {

        }

    }

    public void deleteReader(Readers reader) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM readers WHERE id_reader=?");
            stmt.setInt(1, reader.getReaderId());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Collection<Readers> getAllReaders() throws SQLException {
        Collection<Readers> readers = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM readers;");
            while (rs.next()) {
            	Readers sc = new Readers(rs);
            	readers.add(sc);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return readers;
    }
    
    public Collection<Readers> getReadersByQuery(String query) throws SQLException {
        Collection<Readers> readers = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);
            while (rs.next()) {
            	Readers sc = new Readers(rs);
                readers.add(sc);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return readers;
    }


}
