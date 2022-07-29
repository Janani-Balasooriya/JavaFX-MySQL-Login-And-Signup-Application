package com.example.loginproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.sql.*;

public class DBUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String userName, String firstName, String lastName) {
        Parent root = null;

        if ((userName != null) && (firstName != null)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = fxmlLoader.load();
                LoggedInController loggedInController = fxmlLoader.getController();
                loggedInController.setUserInfoForWelcome(firstName, lastName);
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }else {
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = fxmlLoader.load();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String password, String firstName, String lastName){
        Connection connection = null;
        PreparedStatement psinsert = null;
        PreparedStatement pscheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_project_db", "root", "123");
            pscheckUserExists = connection.prepareStatement("SELECT * FROM userdetail WHERE username = ?");
            pscheckUserExists.setString(1, username);
            resultSet = pscheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()){
                System.out.println("Username already taken.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("This username is taken!");
                alert.show();
            }else {
                psinsert = connection.prepareStatement("INSERT INTO userdetail (username, password, fName, lName) VALUES (?, ?, ?, ?)");
                psinsert.setString(1, username);
                psinsert.setString(2, password);
                psinsert.setString(3, firstName);
                psinsert.setString(4, lastName);
                psinsert.executeUpdate();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User Registration Successful!");
                alert.show();

                changeScene(event, "loggedin-view.fxml", "Welcome!", username, firstName, lastName);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (pscheckUserExists != null){
                try {
                    pscheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psinsert != null){
                try {
                    psinsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login_project_db", "root", "123");
            preparedStatement = connection.prepareStatement("SELECT password, fName, lName FROM userdetail WHERE username = ?");
            preparedStatement.setString(1,username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()){
                System.out.println("User not found in the database!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            }else {
                while (resultSet.next()){
                    String retrievedPassword = resultSet.getString("password");
                    String retrievedFname = resultSet.getString("fName");
                    String retrievedLname = resultSet.getString("lName");
                    if (retrievedPassword.equals(password)){
                        changeScene(event, "loggedin-view.fxml", "Welcome!", username, retrievedFname, retrievedLname);
                    }else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if (connection != null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
