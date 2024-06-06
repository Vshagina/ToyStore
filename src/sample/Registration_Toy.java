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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Registration_Toy {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Text text_reg;

    @FXML
    private Button exit_reg;

    @FXML
    private TextField reg_age;

    @FXML
    private Button reg_button;

    @FXML
    private TextField reg_email;

    @FXML
    private TextField reg_login;

    @FXML
    private TextField reg_password;

    @FXML
    void initialize() {
        reg_button.setOnAction(event -> {
            signUpNewUser();
        });

        exit_reg.setOnAction(event -> {
            exit_reg.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/authorization_Toy.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(new Scene(root));
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void signUpNewUser() {
        String user_login = reg_login.getText().trim();
        String user_password = reg_password.getText().trim();
        String user_age = reg_age.getText().trim();
        String user_email = reg_email.getText().trim();

        if (user_login.isEmpty() || user_password.isEmpty() || user_age.isEmpty() || user_email.isEmpty()) {
            showAlert("Ошибка", "Не все поля заполнены");
            return;
        }

        if (!isValidLogin(user_login)) {
            showAlert("Ошибка", "Логин должен содержать только буквы и цифры");
            return;
        }

        if (!isValidPassword(user_password)) {
            showAlert("Ошибка", "Пароль должен содержать только цифры");
            return;
        }

        if (!isValidAge(user_age)) {
            showAlert("Ошибка", "Возраст должен содержать только цифры");
            return;
        }

        if (!isValidEmail(user_email)) {
            showAlert("Ошибка", "Некорректный формат email");
            return;
        }

        DataBaseHandler dbHandler = new DataBaseHandler();
        User user = new User(user_login, user_password, user_age, user_email);
        dbHandler.signUpUser(user_login, user_password, user_age, user_email);

        openUserWindow(user_login, user_age, user_email);
    }

    private boolean isValidLogin(String login) {
        return login.matches("[a-zA-Z0-9]+");
    }

    private boolean isValidPassword(String password) {
        return password.matches("\\d+");
    }

    private boolean isValidAge(String age) {
        return age.matches("\\d+");
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void openUserWindow(String login, String age, String email) {
        reg_button.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/sample/userWindow.fxml"));
        try {
            Parent root = loader.load();
            UserWindow userWindowController = loader.getController();
            userWindowController.setUserData(login, age, email);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
