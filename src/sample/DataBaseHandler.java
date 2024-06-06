package sample;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHandler extends DataBase {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
        return dbConnection;
    }

    public boolean signUpUser(String user_login, String user_password, String user_age, String user_email) {
        String insert = "INSERT INTO " +
                ConstUser.USER_TABLE + "(" +
                ConstUser.USER_LOGIN + "," +
                ConstUser.USER_PASSWORD + "," +
                ConstUser.USER_AGE + "," +
                ConstUser.USER_EMAIL + ") VALUES(?,?,?,?)";
        try (Connection dbConnection = getDbConnection();
             PreparedStatement prSt = dbConnection.prepareStatement(insert)) {
            prSt.setString(1, user_login);
            prSt.setString(2, user_password);
            prSt.setString(3, user_age);
            prSt.setString(4, user_email);
            prSt.executeUpdate();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ResultSet getUser(String login, String password) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM user WHERE user_login=? AND user_password=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getAllUsers() {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + ConstUser.USER_TABLE;
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getUserData() {
        ResultSet resSet = null;
        String select = "SELECT * FROM user";
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getAdmin(String login, String password) {
        ResultSet resSet = null;
        String select = "SELECT * FROM " + ConstAdmin.ADMIN_TABLE + " WHERE " +
                ConstAdmin.ADMIN_LOGIN + "=? AND " +
                ConstAdmin.ADMIN_PASSWORD + "=?";
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            prSt.setString(1, login);
            prSt.setString(2, password);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getAdminData() {
        ResultSet resSet = null;
        String select = "SELECT * FROM administrator";
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getAllProducts() {
        ResultSet resSet = null;
        String select = "SELECT * FROM products";
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getAllBasketProducts() {
        ResultSet resSet = null;
        String select = "SELECT * FROM basket";
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }

    public ResultSet getAllOrders() {
        ResultSet resultSet = null;
        String query = "SELECT * FROM orders";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void addProduct(String productName, double productPrice) {
        String query = "INSERT INTO products (product_name, product_price) VALUES (?, ?)";

        try (Connection connection = getDbConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, productName);
            preparedStatement.setDouble(2, productPrice);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String delete = "DELETE FROM products WHERE product_id = ?";
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(delete);
            prSt.setInt(1, productId);
            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addOrder(String orderName, double productAmount) throws SQLException {
        String insertOrder = "INSERT INTO orders (order_name, product_amount) VALUES (?, ?)";
        try (Connection dbConnection = getDbConnection();
             PreparedStatement preparedStatement = dbConnection.prepareStatement(insertOrder)) {
            preparedStatement.setString(1, orderName);
            preparedStatement.setDouble(2, productAmount);
            preparedStatement.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException("Failed to add order", e);
        }
    }


    public ResultSet getOrdersData() {
        ResultSet resSet = null;
        String select = "SELECT * FROM orders";
        try {
            Connection dbConnection = getDbConnection();
            PreparedStatement prSt = dbConnection.prepareStatement(select);
            resSet = prSt.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resSet;
    }
    public ResultSet getUserByLogin(String login) {
        ResultSet resultSet = null;
        String query = "SELECT * FROM user WHERE user_login = ?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}



    /*public boolean getUser(String login, String password) {
        String select = "SELECT * FROM " + ConstUser.USER_TABLE + " WHERE " +
                ConstUser.USER_LOGIN + "=? AND " +
                ConstUser.USER_PASSWORD + "=?";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, login);
            prSt.setString(2, password);
            ResultSet resultSet = prSt.executeQuery();
            return resultSet.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean getAdmin(String login, String password) {
        String select = "SELECT * FROM " + ConstAdmin.ADMIN_TABLE + " WHERE " +
                ConstAdmin.ADMIN_LOGIN + "=? AND " +
                ConstAdmin.ADMIN_PASSWORD + "=?";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, login);
            prSt.setString(2, password);
            ResultSet resultSet = prSt.executeQuery();
            return resultSet.next();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }/*

    /*public boolean signUpAdmin(String admin_login, String admin_password, String admin_age, String admin_email) {
        String insert = "INSERT INTO " +
                ConstAdmin.ADMIN_TABLE + "(" +
                ConstAdmin.ADMIN_LOGIN + "," +
                ConstAdmin.ADMIN_PASSWORD + "," +
                ConstAdmin.ADMIN_AGE + "," +
                ConstAdmin.ADMIN_EMAIL +  ") VALUES(?,?,?,?)";
        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, admin_login);
            prSt.setString(2, admin_password);
            prSt.setString(3, admin_age);
            prSt.setString(4, admin_email);
            prSt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }*/
