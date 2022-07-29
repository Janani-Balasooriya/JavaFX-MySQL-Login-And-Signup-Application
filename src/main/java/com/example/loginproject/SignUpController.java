package com.example.loginproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {
    @FXML
    private Button btn_signup;

    @FXML
    private Button btn_login;

    @FXML
    private TextField txt_username;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_fname;

    @FXML
    private TextField txt_lname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txt_username.setFocusTraversable(false);
        txt_password.setFocusTraversable(false);

        btn_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!txt_username.getText().trim().isEmpty() && (!txt_password.getText().trim().isEmpty())
                        && (!txt_fname.getText().trim().isEmpty()) && (!txt_lname.getText().trim().isEmpty())){
                    DBUtils.signUpUser(event, txt_username.getText(), txt_password.getText(), txt_fname.getText(), txt_lname.getText());
                }else {
                    System.out.println("Please fill in all information");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please fill in all fields to sign up!");
                    alert.show();
                }
            }
        });

        btn_login.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBUtils.changeScene(event, "login-view.fxml", "Login!", null, null, null);
            }
        });
    }
}
