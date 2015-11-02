package database.frame.controllers;
/*
* класс контроллера модели заказов
* реализация методов добавления, изменения, удаления подписки, получения списка подписок
*/

import database.frame.models.Orders;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class OrdersController {

    private static String URL_DATABASE = "jdbc:postgresql://localhost:5432/";
    private static OrdersController instance;
    private Connection connection;
    private static String DATABASE_NAME = "test";
    private static String DATABASE_USER = "dev";
    private static String DATBASE_PASSWORD = "dev";

    public OrdersController() throws Exception {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println(" ");
            connection = DriverManager.getConnection(URL_DATABASE + DATABASE_NAME, DATABASE_USER, DATBASE_PASSWORD);
            System.out.println(" ");
        } catch (ClassNotFoundException e) {
            throw new Exception(e);
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }

    public static synchronized OrdersController getInstance() throws Exception{
        if (instance == null) {
            instance = new OrdersController();
        }
        return instance;
    }


    public void updateOrder(Orders order) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "UPDATE orders SET " +
                            "book_id=?, reader_id=?, type_id=?, dataOfOrder=?" +
                            "WHERE id=?");
            stmt.setInt(1, order.getIdBook());
            stmt.setInt(2, order.getIdReader());
            stmt.setInt(3, order.getOrdersType_id());
            stmt.setDate(4, new Date(order.getDataOfOrder().getTime()));
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public void insertOrder(Orders order) throws SQLException {
        PreparedStatement stmt = null;
        try {

            stmt = connection.prepareStatement(
                    "INSERT INTO orders" +
                            "(book_id, reader_id, order_type_id, dataOfOrder" +
                            "VALUES (?, ?, ?, ?)");
            stmt.setInt(1, order.getIdBook());
            stmt.setInt(2, order.getIdReader());
            stmt.setInt(3, order.getOrdersType_id());
            System.out.println(order.getOrdersType_id());
            stmt.setDate(4, new Date(order.getDataOfOrder().getTime()));
            stmt.execute();
            if (stmt != null) {
                stmt.close();
            }
        } finally {

        }

    }

    public void deleteOrder(Orders order) throws SQLException {
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(
                    "DELETE FROM orders WHERE id=?");
            System.out.println("delete " + order.getIdOrder());
            stmt.setInt(1, order.getIdOrder());
            stmt.execute();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public Collection<Orders> getAllOrders() throws SQLException {
        Collection<Orders> orders = new ArrayList<>();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM orders;");
            while (rs.next()) {
            	Orders sc = new Orders(rs);
            	orders.add(sc);
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return orders;
    }

}
