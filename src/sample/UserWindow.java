package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class UserWindow {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private MenuButton admin_profile;

    @FXML
    private Menu toys_user_button;

    @FXML
    private Button exit_user;

    @FXML
    private Button place_an_order;

    @FXML
    private Text age_user;

    @FXML
    private Text email_user;

    @FXML
    private Text login_user;

    @FXML
    private TableView<Product> toys_tab_user;

    @FXML
    private TableColumn<Product, Integer> num_toy_user;

    @FXML
    private TableColumn<Product, String> name_toy_user;

    @FXML
    private TableColumn<Product, Double> price_toy_user;

    @FXML
    void initialize() {
        populateUserProfile();
        populateProductsTable();

        exit_user.setOnAction(event -> {
            Stage currentStage = (Stage) exit_user.getScene().getWindow();
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
        place_an_order.setOnAction(event -> {
            Product selectedProduct = toys_tab_user.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                try {
                    Stage currentStage = (Stage) place_an_order.getScene().getWindow();
                    currentStage.close();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/sample/ordersWindow.fxml"));
                    Parent root = loader.load();

                    OrdersWindow ordersWindowController = loader.getController();
                    ordersWindowController.setOrderDetails(selectedProduct);

                    Stage stage = new Stage();
                    stage.initStyle(StageStyle.UNDECORATED);
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText(null);
                alert.setContentText("Выберите товар!");
                alert.showAndWait();
            }
        });
    }
    public void setUserData(String login, String age, String email) {
        login_user.setText(login);
        age_user.setText(age);
        email_user.setText(email);
    }
    private void populateUserProfile() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getUserData();

        try {
            if (resultSet.next()) {
                String user_login = resultSet.getString("user_login");
                String user_age = resultSet.getString("user_age");
                String user_email = resultSet.getString("user_email");

                login_user.setText(user_login);
                age_user.setText(user_age);
                email_user.setText(user_email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateProductsTable() {
        DataBaseHandler dbHandler = new DataBaseHandler();
        List<Product> products = new ArrayList<>();
        ResultSet resultSet = dbHandler.getAllProducts();

        try {
            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                double productPrice = resultSet.getDouble("product_price");
                products.add(new Product(productId, productName, productPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        num_toy_user.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getProduct_id()).asObject());
        name_toy_user.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProduct_name()));
        price_toy_user.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProduct_price()).asObject());

        toys_tab_user.getItems().setAll(products);
    }
}
