package sample;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class OrdersWindow {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text amount_orders;

    @FXML
    private Text date_orders;

    @FXML
    private Button exit_to_user_window;

    @FXML
    private Text name_order;

    @FXML
    private Button order_button;

    @FXML
    void initialize() {

        exit_to_user_window.setOnAction(event -> {
            Stage currentStage = (Stage) exit_to_user_window.getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/userWindow.fxml"));
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

        order_button.setOnAction(event -> {
            String orderName = name_order.getText();
            String amount = amount_orders.getText();
            double productAmount = Double.parseDouble(amount);

            DataBaseHandler dbHandler = new DataBaseHandler();
            try {
                dbHandler.addOrder(orderName, productAmount);

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Заказ оформлен");
                alert.setHeaderText(null);
                alert.setContentText("Ваш заказ был успешно оформлен!");
                alert.showAndWait();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public void setOrderDetails(Product product) {
        name_order.setText(product.getProduct_name());
        amount_orders.setText(String.valueOf(product.getProduct_price()));
    }
}
