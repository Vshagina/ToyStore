package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("authorization_Toy.fxml"));
        VBox vbox = new VBox();
        vbox.getChildren().add(root);
        Scene scene = new Scene(vbox, 290, 400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.widthProperty().addListener((obs, oldVal, newVal) -> {
            vbox.setPrefWidth(newVal.doubleValue());
        });
        primaryStage.heightProperty().addListener((obs, oldVal, newVal) -> {
            vbox.setPrefHeight(newVal.doubleValue());
        });
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}