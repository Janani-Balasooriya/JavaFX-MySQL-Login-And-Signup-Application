package com.example.loginproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btn_login;

    @FXML
    private Button btn_signup;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_username.setFocusTraversable(false);
        txt_password.setFocusTraversable(false);

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.logInUser(event, txt_username.getText(), txt_password.getText());
            }
        });

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "signup-view.fxml", "Sign up!", null, null, null);
            }
        });
    }
}
