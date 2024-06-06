package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.StageStyle;

public class AdminWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button add_products;

    @FXML
    private MenuButton add_products_button;

    @FXML
    private MenuButton admin_profile;

    @FXML
    private Text age_admin;

    @FXML
    private TextField amount_products;

    @FXML
    private Button delete_product;

    @FXML
    private Text email_admin;

    @FXML
    private Text login_admin;

    @FXML
    private TextField name_products;

    @FXML
    private Tab orders_admin;

    @FXML
    private Tab products_admin;

    @FXML
    private TableView<User> tab_clients;

    @FXML
    private TableView<Product> tab_products;

    @FXML
    private TableView<Order> tab_orders;

    @FXML
    private Tab viewing_clients_admin;

    @FXML
    private Button viewing_products;

    @FXML
    private Button exit_admin;

    @FXML
    private void initialize() {

        populateClientsTable();
        populateProductsTable();
        populateOrdersTable();
        populateAdminProfile();

        delete_product.setOnAction(event -> deleteSelectedProduct());
        viewing_products.setOnAction(event -> populateProductsTable());
        add_products.setOnAction(event -> addProduct());

        exit_admin.setOnAction(event -> {
            Stage currentStage = (Stage) exit_admin.getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/authorization_Toy.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void populateClientsTable() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getAllUsers();

        ObservableList<User> usersList = FXCollections.observableArrayList();

        try {
            while (resultSet.next()) {
                int id_user = resultSet.getInt("id_user");
                String user_login = resultSet.getString("user_login");
                String user_password = resultSet.getString("user_password");
                String user_age = resultSet.getString("user_age");
                String user_email = resultSet.getString("user_email");

                User user = new User(user_login, user_password, user_age, user_email);
                user.setId_user(id_user);
                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn<User, Integer> idColumn = new TableColumn<>("№");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_user"));

        TableColumn<User, String> loginColumn = new TableColumn<>("Логин");
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("user_login"));

        TableColumn<User, String> passwordColumn = new TableColumn<>("Пароль");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("user_password"));

        TableColumn<User, String> ageColumn = new TableColumn<>("Возраст");
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("user_age"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("user_email"));

        tab_clients.getColumns().setAll(idColumn, loginColumn, passwordColumn, ageColumn, emailColumn);
        tab_clients.setItems(usersList);
    }

    private void populateProductsTable() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getAllProducts();

        ObservableList<Product> productsList = FXCollections.observableArrayList();
        tab_products.getItems().clear();

        try {
            while (resultSet.next()) {
                int product_id = resultSet.getInt("product_id");
                String product_name = resultSet.getString("product_name");
                double product_price = resultSet.getDouble("product_price");

                Product product = new Product(product_id, product_name, product_price);
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn<Product, Integer> idColumn = new TableColumn<>("№");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Цена");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("product_price"));

        tab_products.getColumns().setAll(idColumn, nameColumn, priceColumn);
        tab_products.setItems(productsList);
    }

    private void populateOrdersTable() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getAllOrders();

        ObservableList<Order> ordersList = FXCollections.observableArrayList();
        tab_orders.getItems().clear();

        try {
            while (resultSet.next()) {
                int order_id = resultSet.getInt("order_id");
                String order_name = resultSet.getString("order_name");
                int product_amount = resultSet.getInt("product_amount");

                Order order = new Order(order_id, order_name, product_amount);
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        TableColumn<Order, Integer> idColumn = new TableColumn<>("№");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("order_id"));

        TableColumn<Order, String> nameColumn = new TableColumn<>("Название");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("order_name"));

        TableColumn<Order, Integer> amountColumn = new TableColumn<>("Цена");
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("product_amount"));

        tab_orders.getColumns().setAll(idColumn, nameColumn, amountColumn);
        tab_orders.setItems(ordersList);
    }

    private void populateAdminProfile() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getAdminData();

        try {
            if (resultSet.next()) {
                String admin_login = resultSet.getString("admin_login");
                String admin_age = resultSet.getString("admin_age");
                String admin_email = resultSet.getString("admin_email");

                login_admin.setText(admin_login);
                age_admin.setText(admin_age);
                email_admin.setText(admin_email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addProduct() {
        String productName = name_products.getText();
        String productPriceText = amount_products.getText();

        if (!productName.matches("[a-zA-Zа-яА-Я]+")) {
            showAlert("Недопустимый ввод", "Название товара должно содержать только буквы");
            return;
        }

        double productPrice;
        try {
            productPrice = Double.parseDouble(productPriceText);
        } catch (NumberFormatException e) {
            showAlert("Недопустимый ввод", "Цена товара должна быть числом");
            return;
        }

        DataBaseHandler dbHandler = new DataBaseHandler();
        dbHandler.addProduct(productName, productPrice);

        name_products.clear();
        amount_products.clear();

        populateProductsTable();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void deleteSelectedProduct() {
        Product selectedProduct = tab_products.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.deleteProduct(selectedProduct.getProduct_id());
            tab_products.getItems().remove(selectedProduct);
        }
    }

}
