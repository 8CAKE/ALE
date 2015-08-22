package com.silverfox.ale;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class login extends Application {

    Main aleMain = new Main();
    aleDB aleDB = new aleDB();

    Connection dbCon = aleDB.dbCon;
    PreparedStatement dbStm;
    ResultSet dbRs;

    String superUser;

    //Login
    BorderPane loginPane;
    VBox loginBox;
    HBox loginBtnBox;
    HBox loginTopPanel;
    TextField usernameLoginField;
    PasswordField passwordLoginField;
    Button loginCloseBtn;
    Button loginMinimizeBtn;
    Label loginLbl;
    Label errorLbl;
    Button loginBtn;
    Button signupBtn;

    //Sign Up
    BorderPane signUpPane;
    VBox signUpBox;
    HBox signUpBtnBox;
    HBox signUpTopPanel;
    TextField usernameField;
    PasswordField passwordField;
    PasswordField repeatPasswordField;
    Button signUpCloseBtn;
    Button signUpMinimizeBtn;
    TextField emailField;
    TextField ageField;
    TextField schoolField;
    TextField cityField;
    TextField countryField;
    Button finalSignUpBtn;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        aleDB.connectToDB();

        loginLbl = new Label("Login");
        loginLbl.getStyleClass().add("lbl");

        errorLbl = new Label("");
        errorLbl.getStyleClass().add("errorLbl");
        errorLbl.setVisible(false);

        usernameLoginField = new TextField();
        usernameLoginField.getStyleClass().add("textField");
        usernameLoginField.setPromptText("Username");

        //For quick access
        usernameLoginField.setText("sample");

        passwordLoginField = new PasswordField();
        passwordLoginField.getStyleClass().add("textField");
        passwordLoginField.setPromptText("Password");

        //For quick access during development
        passwordLoginField.setText("1234");

        loginCloseBtn = new Button("");
        loginCloseBtn.getStyleClass().add("loginSystemBtn");
        loginCloseBtn.setOnAction(e -> {
            aleMain.systemClose();
        });

        loginMinimizeBtn = new Button("");
        loginMinimizeBtn.getStyleClass().add("loginSystemBtn");
        loginMinimizeBtn.setOnAction(e -> {
            primaryStage.setIconified(true);
        });

        loginTopPanel = new HBox();
        loginTopPanel.getStyleClass().add("loginTopPanel");
        loginTopPanel.setAlignment(Pos.CENTER_RIGHT);
        loginTopPanel.setPadding(new Insets(0,0,0,0));
        loginTopPanel.getChildren().addAll(loginLbl, loginMinimizeBtn, loginCloseBtn);

        loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("loginBtn");
        loginBtn.setOnAction(e -> {
            if(aleDB.connectToDB() == true){
                if(validLogin(usernameLoginField.getText(), passwordLoginField.getText()) == true){
                    superUser = usernameLoginField.getText();

                    Scene mainScene = new Scene(rootPane, width, height);
                    primaryStage.setScene(mainScene);
                    primaryStage.centerOnScreen();

                }else {
                    errorLbl.setText("Incorrect username or password");
                    errorLbl.setVisible(true);
                    passwordLoginField.clear();
                }
            }else{
                errorLbl.setText("Could Not Connect To Database");
                errorLbl.setVisible(true);
            }
        });

        signupBtn = new Button("Sign Up");
        signupBtn.getStyleClass().add("signupBtn");
        signupBtn.setOnAction(e -> {
            usernameLoginField.setText(null);
            passwordLoginField.setText(null);
            Scene signUpScene = new Scene(signUpPane, 300, 250);
            primaryStage.setScene(signUpScene);
        });

        loginBtnBox = new HBox();
        loginBtnBox.getChildren().addAll(loginBtn, signupBtn, errorLbl);


        loginBox = new VBox();
        loginBox.getChildren().addAll(usernameLoginField, passwordLoginField, loginBtnBox);

        loginPane = new BorderPane();
        loginPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());
        loginPane.getStyleClass().add("loginPane");
        loginPane.setTop(loginTopPanel);
        loginPane.setCenter(loginBox);

//-----------------------------------------------------------------------------------------------------> Login Pane End

//-------------------------------------------------------------------------------------------------> Sign Up Pane Start

        signUpCloseBtn = new Button();
        signUpCloseBtn.getStyleClass().add("loginSystemBtn");
        signUpCloseBtn.setOnAction(e -> {
            aleMain.systemClose();
        });

        signUpMinimizeBtn = new Button();
        signUpMinimizeBtn.getStyleClass().add("loginSystemBtn");
        signUpMinimizeBtn.setOnAction(e -> {
            primaryStage.setIconified(true);
        });

        signUpTopPanel = new HBox();
        signUpTopPanel.getStyleClass().add("loginTopPanel");
        signUpTopPanel.setAlignment(Pos.CENTER_RIGHT);
        signUpTopPanel.setPadding(new Insets(0, 0, 0, 0));
        signUpTopPanel.getChildren().addAll(signUpMinimizeBtn, signUpCloseBtn);

        usernameField = new TextField();
        usernameField.getStyleClass().add("textField");
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.getStyleClass().add("textField");
        passwordField.setPromptText("Password");

        repeatPasswordField = new PasswordField();
        repeatPasswordField.getStyleClass().add("textField");
        repeatPasswordField.setPromptText("Repeat Password");

        emailField = new TextField();
        emailField.getStyleClass().add("textField");
        emailField.setPromptText("Email");

        ageField = new TextField();
        ageField.getStyleClass().add("textField");
        ageField.setPromptText("Age (Optional)");

        schoolField = new TextField();
        schoolField.getStyleClass().add("textField");
        schoolField.setPromptText("School (Optional)");

        cityField = new TextField();
        cityField.getStyleClass().add("textField");
        cityField.setPromptText("City (Optional)");

        countryField = new TextField();
        countryField.getStyleClass().add("textField");
        countryField.setPromptText("Country (Optional)");

        finalSignUpBtn = new Button("Sign Up");
        finalSignUpBtn.getStyleClass().add("loginBtn");
        finalSignUpBtn.setOnAction(e -> {
            if(aleDB.connectToDB() == true){
                if(validSignUp(usernameField.getText(), passwordField.getText(), repeatPasswordField.getText(),
                        emailField.getText(), ageField.getText(), schoolField.getText()) == null){
                    try{
                        PreparedStatement psSignUp = null;
                        ResultSet rsSignUp = null;
                        psSignUp = dbCon.prepareStatement("INSERT INTO userInfo VALUES(" + "\'"
                                + usernameField.getText() + "\'" + "," + "\'" + emailField.getText()
                                + "\'" + "," + "\'" + passwordField.getText() + "\'" + "," + ageField.getText() + ","
                                + null + "," + "\'" +schoolField.getText() + "\'" + "," + "\'" + cityField.getText()
                                + "\'" + "," + "\'" + countryField.getText() + "\'" + ")" + ";");

                        System.out.println("INSERT INTO userInfo VALUES(" + "\'"
                                + usernameField.getText() + "\'" + "," + "\'" + emailField.getText()
                                + "\'" + "," + "\'" + passwordField.getText() + "\'" + "," + ageField.getText() + ","
                                + null + "," + "\'" +schoolField.getText() + "\'" + "," + "\'" + cityField.getText()
                                + "\'" + "," + "\'" + countryField.getText() + "\'" + ")" + ";");

                        psSignUp.executeUpdate();
                    }catch (Exception signupErr){
                        JOptionPane.showMessageDialog(null, "Internal Sign Up Error");
                        System.out.println("<----- Sign Up Error Start ----->");
                        signupErr.printStackTrace();
                        System.out.println("<----- Sign Up Error End ----->");
                    }
                }else {
                    errorLbl.setText(validSignUp(usernameField.getText(), passwordField.getText(),
                            repeatPasswordField.getText(), emailField.getText(), ageField.getText(),
                            schoolField.getText()));
                }
            }else {
                errorLbl.setText("Could Not Connect To Database");
            }
        });

        signUpBtnBox = new HBox();
        signUpBtnBox.getChildren().addAll(finalSignUpBtn, errorLbl);

        signUpBox = new VBox();
        signUpBox.setPadding(new Insets(0, 0, 0, 0));
        signUpBox.getChildren().addAll(usernameField, passwordField, repeatPasswordField, emailField, ageField,
                schoolField, cityField, countryField,signUpBtnBox);

        signUpPane = new BorderPane();
        signUpPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());
        signUpPane.getStyleClass().add("loginPane");
        signUpPane.setTop(signUpTopPanel);
        signUpPane.setCenter(signUpBox);

//---------------------------------------------------------------------------------------------------> Sign Up Pane End

        loginPane.getStyleClass().add("rootPane");
        loginPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());

        signUpPane.getStyleClass().add("rootPane");
        signUpPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());

        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setScene(new Scene(loginPane, 300, 123));
        primaryStage.show();

    }

    public boolean validLogin(String username, String password){
        boolean validUser = false;
        try {

            dbStm = dbCon.prepareStatement("SELECT * FROM userInfo WHERE username = " + "\"" + username + "\""
                    + ";" );

            dbRs = dbStm.executeQuery();

            while (dbRs.next()){
                String un = "";
                String pw = "";

                un = dbRs.getString("username");
                System.out.println(un + " " + username);
                pw = dbRs.getString("password");
                System.out.println(pw + " " + password);

                if(((username.equals(un) == true) && ((password.equals(pw) == true)))){
                    validUser = true;
                    System.out.println("Valid User");

                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }


        return validUser;
    }

    public String validSignUp(String username, String password, String repeatPassword, String email, String age,
                              String school){
        String error = null;

        if(password.equals(repeatPassword) == false){
            error = "Passwords do not match";
        }

        return error;
    }
}
