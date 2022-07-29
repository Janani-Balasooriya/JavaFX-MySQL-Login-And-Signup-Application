package com.example.loginproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource("login-view.fxml"));
        Parent root = fxmlLoader.load();
        stage.setTitle("Login");
        try{
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icon16.png")));
        }catch (Exception e){
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}