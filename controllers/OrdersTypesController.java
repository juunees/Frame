package database.frame.controllers;

import database.frame.models.OrdersType;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class OrdersTypesController {
    private static String URL_DATABASE = "jdbc:postgresql://localhost:5432/";
    private static OrdersTypesController instance;
    private Connection connection;
    private static String DATABASE_NAME = "test";
    private static String DATABASE_USER = "dev";
    private static String DATBASE_PASSWORD = "dev";

    
    public OrdersTypesController() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(URL_DATABASE + DATABASE_NAME, DATABASE_USER, DATBASE_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized OrdersTypesController getInstance() throws Exception{
        if (instance == null) {
            instance = new OrdersTypesController();
        }
        return instance;
    }

    
    public void insertOrdersType(OrdersType orders_type) throws SQLException {
        PreparedStatement stmt = null;
        try {

            stmt = connection.prepareStatement(
                    "INSERT INTO orders_types " +
                            "(name)" +
                            "VALUES (?)");
            stmt.setString(1,orders_type.getName());
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

    public void deleteOrdersType(OrdersType orders_type) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM orders_types WHERE id=?");
            stmt.setInt(1, orders_type.getOrdersType_id());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Collection<OrdersType> getAllOrdersTypes() throws SQLException{
        Collection<OrdersType> orders_types = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM orders_types;");
            while (rs.next()) {
            	OrdersType t = new OrdersType(rs);
            	orders_types.add(t);
                System.out.println(t.getOrdersType_id() + " " + t.getName());
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return orders_types;
    }

	

	
}
