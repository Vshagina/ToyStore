package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Authorization_Toy {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button auto_reg_button;

    @FXML
    private Button auto_button;

    @FXML
    private TextField auto_log;

    @FXML
    private TextField auto_passw;

    @FXML
    private Text auto_text;

    @FXML
    void initialize() {

        auto_button.setOnAction(event -> {
            String login = auto_log.getText().trim();
            String password = auto_passw.getText().trim();

            if (!login.equals("") && !password.equals("")) {
                if (isValidLogin(login) && isValidPassword(password)) {
                    Autoriz(login, password);
                } else {
                    showAlert("Ошибка", "Неправильный формат логина или пароля");
                }
            } else {
                showAlert("Ошибка", "Не заполненные поля");
            }
        });

        auto_reg_button.setOnAction(event -> {
            auto_reg_button.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/registration_Toy.fxml"));
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

    private boolean isValidLogin(String login) {
        return login.matches("[a-zA-Z0-9]+");
    }

    private boolean isValidPassword(String password) {
        return password.matches("\\d+");
    }

    private void openUserWindow(String login, String age, String email) {
        auto_button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/userWindow.fxml"));
        try {
            Parent root = loader.load();
            UserWindow userWindowController = loader.getController();
            userWindowController.setUserData(login, age, email);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void Autoriz(String login, String password) {
        DataBaseHandler dbHandler = new DataBaseHandler();

        ResultSet adminResult = dbHandler.getAdmin(login, password);
        try {
            if (adminResult != null && adminResult.next()) {
                openAdminWindow();
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (adminResult != null) adminResult.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        ResultSet userResult = dbHandler.getUser(login, password);
        try {
            if (userResult != null && userResult.next()) {
                String userAge = userResult.getString("user_age");
                String userEmail = userResult.getString("user_email");
                openUserWindow(login, userAge, userEmail);
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (userResult != null) userResult.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        showAlert("Ошибка", "Неправильный логин или пароль");
    }

    private void openAdminWindow() {
        auto_button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/adminWindow.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
