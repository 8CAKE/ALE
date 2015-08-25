package com.silverfox.ale;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.io.IOException;
import java.sql.*;

public class login extends Application {

    aleDB aleDB = new aleDB();

    Connection dbCon;
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
    Label signUpErrorLbl;

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

        passwordLoginField = new PasswordField();
        passwordLoginField.getStyleClass().add("textField");
        passwordLoginField.setPromptText("Password");

        //Uncomment for debugging
        usernameLoginField.setText("sample");
        passwordLoginField.setText("1234");

        loginCloseBtn = new Button("");
        loginCloseBtn.getStyleClass().add("loginSystemBtn");
        loginCloseBtn.setOnAction(e -> {
            System.exit(0);
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
            if(connectToDB() == true){
                if(validLogin(usernameLoginField.getText(), passwordLoginField.getText()) == true){
                    superUser = usernameLoginField.getText();
                    try{

                        Main main = new Main();
                        Stage mainStage = new Stage();
                        main.start(mainStage);
                        ((Node)(e.getSource())).getScene().getWindow().hide();
                    }catch (IOException ioExcep){
                        //<----- IO Exception in Valid Login ----->
                        ioExcep.printStackTrace();
                        //<---------->
                    }catch (Exception excep){
                        //<----- Exception in Valid Login ----->
                        excep.printStackTrace();
                        //<---------->
                    }


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
            System.exit(0);
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

        signUpErrorLbl = new Label();
        signUpErrorLbl.getStyleClass().add("errorLbl");

        finalSignUpBtn = new Button("Sign Up");
        finalSignUpBtn.getStyleClass().add("loginBtn");
        finalSignUpBtn.setOnAction(e -> {
            if(connectToDB() == true){
                if(validSignUp(usernameField.getText(), passwordField.getText(), repeatPasswordField.getText(),
                        emailField.getText(), ageField.getText(), schoolField.getText()) == null){

                    String username = usernameField.getText();
                    String email = emailField.getText();
                    String password = passwordField.getText();
                    String age = ageField.getText();

                    if(age.equals("")){
                        age = "null";
                    }

                    String school = schoolField.getText();
                    String city = cityField.getText();
                    String country = countryField.getText();

                    try{
                        PreparedStatement psSignUp = null;
                        ResultSet rsSignUp = null;
                        psSignUp = dbCon.prepareStatement("INSERT INTO userInfo VALUES(" + "\'"
                                + username + "\'" + "," + "\'" + email + "\'" + "," + "\'" + password
                                + "\'" + "," + age + "," + null + "," + "\'" + school + "\'" + "," + "\'" + city
                                + "\'" + "," + "\'" + country + "\'" + "," +"\'" +  "\'" + ")" + ";");

                        System.out.println("INSERT INTO userInfo VALUES(" + "\'"
                                + username + "\'" + "," + "\'" + email + "\'" + "," + "\'" + password
                                + "\'" + "," + age + "," + null + "," + "\'" + school + "\'" + "," + "\'" + city
                                + "\'" + "," + "\'" + country + "\'" + "," +"\'" +  "\'" + ")" + ";");

                        psSignUp.executeUpdate();
                    }catch (SQLException sqlExcep){
                        JOptionPane.showMessageDialog(null, "Internal Sign Up Error");
                        System.out.println("<----- SQL Exception in Sign Up ----->");
                        sqlExcep.printStackTrace();
                        System.out.println("<---------->\n");
                    }
                }else {
                    signUpErrorLbl.setText(validSignUp(usernameField.getText(), passwordField.getText(),
                            repeatPasswordField.getText(), emailField.getText(), ageField.getText(),
                            schoolField.getText()));
                }
            }else {
                signUpErrorLbl.setText("Could Not Connect To Database");
            }
        });

        signUpBtnBox = new HBox();
        signUpBtnBox.getChildren().addAll(finalSignUpBtn, signUpErrorLbl);

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

        if(username.equals("")){
            error = "Enter username";
        }

        if(password.equals("")){
            error = "Enter password";
        }

        if(email.equals("")){
            error = "Enter email";
        }

        if(!age.equals("")){
            if(Integer.parseInt(age) > 0 || Integer.parseInt(age) < 100){
            }else{
                error = "Enter valid age";
            }
        }

        return error;
    }

    public boolean connectToDB(){
        boolean estCon = false;
        try{
            dbCon = DriverManager.getConnection("jdbc:mysql://192.168.1.6:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return estCon;
    }

    public login() {
    }
}
