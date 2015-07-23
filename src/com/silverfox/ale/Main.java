package com.silverfox.ale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main extends Application {

    String superUser = "";

    // Database Variables
    Connection dbCon = null;
    PreparedStatement dbStm = null;
    ResultSet dbRs = null;

    // Get Screen Size
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();

    // Panes
    BorderPane rootPane;

    VBox leftPanel;
    HBox topPanel;

    BorderPane loginPane;
    VBox loginBox;
    HBox loginBtnBox;
    HBox loginTopPanel;
    TextField usernameField;
    PasswordField passwordField;
    Button loginCloseBtn;
    Button loginMinimizeBtn;
    Label loginLbl;
    Label errorLbl;
    Button loginBtn;
    Button signupBtn;


    //Buttons
    Button homeBtn;
    Button coursesBtn;
    Button closeBtn;
    Button minimizeBtn;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

//---------------------------------------------------------------------------------------------------> Login Pane Start

        loginLbl = new Label("Login");
        loginLbl.getStyleClass().add("lbl");

        errorLbl = new Label("");
        errorLbl.getStyleClass().add("errorLbl");
        errorLbl.setVisible(false);

        usernameField = new TextField();
        usernameField.getStyleClass().add("textField");
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.getStyleClass().add("textField");
        passwordField.setPromptText("Password");

        loginCloseBtn = new Button("");
        loginCloseBtn.getStyleClass().add("loginSystemBtn");
        loginCloseBtn.setOnAction(e -> {
            systemClose();
        });

        loginMinimizeBtn = new Button("");
        loginMinimizeBtn.getStyleClass().add("loginSystemBtn");
        loginMinimizeBtn.setOnAction(e -> {
            primaryStage.setIconified(true);
        });

        loginTopPanel = new HBox(1);
        loginTopPanel.getStyleClass().add("loginTopPanel");
        loginTopPanel.setAlignment(Pos.CENTER_RIGHT);
        loginTopPanel.setPadding(new Insets(0,0,0,0));
        loginTopPanel.getChildren().addAll(loginLbl, loginMinimizeBtn, loginCloseBtn);

        loginBtn = new Button("Login");
        loginBtn.getStyleClass().add("loginBtn");
        loginBtn.setOnAction(e -> {
            if(connectToDB() == true){
                if(login(usernameField.getText(), passwordField.getText()) == true){
                    superUser = usernameField.getText();
                    JOptionPane.showMessageDialog(null, "Login Successful");
                    //Scene mainScene = new Scene(rootPane, width, height);
                    //primaryStage.setScene(mainScene);
                }else {
                    errorLbl.setText("Incorrect username or password");
                    errorLbl.setVisible(true);
                }
            }else{
                errorLbl.setText("Could Not Connect To Database");
                errorLbl.setVisible(true);
            }
        });


        signupBtn = new Button("Sign Up");
        signupBtn.getStyleClass().add("signupBtn");

        loginBtnBox = new HBox();

        loginBtnBox.getChildren().addAll(loginBtn, signupBtn, errorLbl);


        loginBox = new VBox();
        loginBox.getChildren().addAll(usernameField, passwordField, loginBtnBox);

        loginPane = new BorderPane();
        loginPane.getStylesheets().add(Main.class.getResource("styleDark.css").toExternalForm());
        loginPane.getStyleClass().add("loginPane");
        loginPane.setTop(loginTopPanel);
        loginPane.setCenter(loginBox);


//-----------------------------------------------------------------------------------------------------> Login Pane End


//----------------------------------------------------------------------------------------------------> Home Pane Start
        homeBtn = new Button("");
        homeBtn.getStyleClass().add("homeBtn");

        coursesBtn = new Button("");
        coursesBtn.getStyleClass().add("coursesBtn");

        closeBtn = new Button("");
        closeBtn.getStyleClass().add("systemBtn");
        closeBtn.setOnAction(e -> {
            systemClose();
        });

        minimizeBtn = new Button("");
        minimizeBtn.getStyleClass().add("systemBtn");
        minimizeBtn.setOnAction(e -> {
            primaryStage.setIconified(true);
        });

        leftPanel = new VBox(0);
        leftPanel.setPrefWidth(1);
        leftPanel.getStyleClass().add("leftPane");
        leftPanel.getChildren().addAll(homeBtn, coursesBtn);

        topPanel = new HBox(1);
        topPanel.getStyleClass().add("topPanel");
        topPanel.setAlignment(Pos.CENTER_RIGHT);
        topPanel.setPadding(new Insets(0,0,0,0));
        topPanel.getChildren().addAll(minimizeBtn, closeBtn);

        rootPane = new BorderPane();
        rootPane.setLeft(leftPanel);
        rootPane.setPrefWidth(1);
        rootPane.setTop(topPanel);
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("styleDark.css").toExternalForm());
//------------------------------------------------------------------------------------------------------> Home Pane End


        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/btn/aleIcon.png")));
        primaryStage.setScene(new Scene(loginPane, 200, 100));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void systemClose(){
        System.exit(0);
    }

    public void systemMinimize(){

    }

    public boolean connectToDB(){
        boolean estCon = false;
        try{
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/sample", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return estCon;
    }

    public boolean login(String useraname, String password){
        boolean validUser = false;
        try {
            try {
                dbStm = dbCon.prepareStatement("SELECT * FROM userInfo");
                dbRs = dbStm.executeQuery();
            } catch (Exception e) {
                e.printStackTrace();
            }

            do {
                String un = "";
                String pw = "";
                try {
                    un = dbRs.getString("username");
                    pw = dbRs.getString("password");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if((useraname == un) && (password == pw)){
                    validUser = true;
                }

            } while (dbRs.next() && (validUser = false));

        }catch (Exception e){
            e.printStackTrace();
        }

        return validUser;
    }

}
