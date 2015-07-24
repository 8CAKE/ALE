package com.silverfox.ale;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
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

    ScrollPane homePanel;
    ScrollPane coursesPanel;
    ScrollPane simsPanel;

    BorderPane loginPane;
    VBox loginBox;
    HBox loginBtnBox;
    HBox loginTopPanel;

    GridPane coursesGridPanel;
    GridPane simsGridPanel;

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
    Button simsBtn;
    Button closeBtn;
    Button minimizeBtn;

    //Course Btns
    Button chemistryBtn;
    Button physicsBtn;
    Button mathsBtn;

    //Simulation Btns
    Button alphaDecayBtn;
    Button balancingActBtn;
    Button balloonsAndStaticElectricityBtn;

    //Labels



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
                if(validLogin(usernameField.getText(), passwordField.getText()) == true){
                    superUser = usernameField.getText();

                    Scene mainScene = new Scene(rootPane, width, height);
                    primaryStage.setScene(mainScene);
                }else {
                    errorLbl.setText("Incorrect username or password");
                    errorLbl.setVisible(true);
                    passwordField.clear();
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
        homeBtn.setOnAction(e -> {
            rootPane.setCenter(homePanel);
        });

        coursesBtn = new Button("");
        coursesBtn.getStyleClass().add("coursesBtn");
        coursesBtn.setOnAction(e -> {
            rootPane.setCenter(coursesPanel);
        });

        simsBtn = new Button();
        simsBtn.getStyleClass().add("simsBtn");
        simsBtn.setOnAction(e -> {
            rootPane.setCenter(simsPanel);
            simsPanel.setContent(simsGridPanel);
        });

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
        leftPanel.getChildren().addAll(homeBtn, coursesBtn, simsBtn);

        topPanel = new HBox(1);
        topPanel.getStyleClass().add("topPanel");
        topPanel.setAlignment(Pos.CENTER_RIGHT);
        topPanel.setPadding(new Insets(0,0,0,0));
        topPanel.getChildren().addAll(minimizeBtn, closeBtn);


        homePanel = new ScrollPane();
        homePanel.getStyleClass().add("centerPanel");
        homePanel.setPrefWidth(600);
        homePanel.setPrefHeight(1000);
       // homePanel.setContent();




//------------------------------------------------------------------------------------------------------> Home Pane End

//-------------------------------------------------------------------------------------------------> Courses Pane Start

        chemistryBtn = new Button();
        chemistryBtn.getStyleClass().add("chemistryBtn");

        physicsBtn = new Button();
        physicsBtn.getStyleClass().add("physicsBtn");

        mathsBtn = new Button();
        mathsBtn.getStyleClass().add("mathsBtn");

        coursesGridPanel = new GridPane();
        coursesGridPanel.getStyleClass().add("gridPane");
        coursesGridPanel.setVgap(5);
        coursesGridPanel.setHgap(5);
        coursesGridPanel.setGridLinesVisible(false);
        coursesGridPanel.setPrefWidth(1482);
        coursesGridPanel.setPrefHeight(860);


        coursesGridPanel.setRowIndex(chemistryBtn, 1);
        coursesGridPanel.setColumnIndex(chemistryBtn, 1);
        coursesGridPanel.setRowIndex(physicsBtn, 1);
        coursesGridPanel.setColumnIndex(physicsBtn, 2);
        coursesGridPanel.setRowIndex(mathsBtn, 1);
        coursesGridPanel.setColumnIndex(mathsBtn, 3);
        coursesGridPanel.getChildren().addAll(chemistryBtn, physicsBtn, mathsBtn);


        coursesPanel = new ScrollPane();
        coursesPanel.getStyleClass().add("scrollPane");
        coursesPanel.setPrefWidth(1482);
        coursesPanel.setPrefHeight(860);
        coursesPanel.setContent(coursesGridPanel);

//---------------------------------------------------------------------------------------------------> Courses Pane End

//---------------------------------------------------------------------------------------------> Simulations Pane Start
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        browser.setPrefHeight(860);
        browser.setPrefWidth(1482);

        Label physLbl = new Label("Physics");
        physLbl.getStyleClass().add("lbl");



        balancingActBtn = new Button();
        balancingActBtn.getStyleClass().add("balancingActBtn");
        balancingActBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/balancing-act/latest/balancing-act_en.html");
            simsPanel.setContent(browser);
        });

        balloonsAndStaticElectricityBtn = new Button();
        balloonsAndStaticElectricityBtn.getStyleClass().add("balloonsAndStaticElectricityBtn");
        balloonsAndStaticElectricityBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/balloons-and-static-electricity/latest/" +
                    "balloons-and-static-electricity_en.html");
            simsPanel.setContent(browser);
        });



        simsGridPanel = new GridPane();
        simsGridPanel.getStyleClass().add("gridPane");
        simsGridPanel.setVgap(5);
        simsGridPanel.setHgap(5);
        simsGridPanel.setGridLinesVisible(false);
        simsGridPanel.setPrefWidth(1482);
        simsGridPanel.setPrefHeight(860);

        simsGridPanel.setRowIndex(physLbl, 0);
        simsGridPanel.setColumnIndex(physLbl, 0);
        simsGridPanel.setRowIndex(balancingActBtn, 1);
        simsGridPanel.setColumnIndex(balancingActBtn, 0);
        simsGridPanel.setRowIndex(balloonsAndStaticElectricityBtn, 1);
        simsGridPanel.setColumnIndex(balloonsAndStaticElectricityBtn, 1);
        simsGridPanel.getChildren().addAll(physLbl, balancingActBtn, balloonsAndStaticElectricityBtn);

        simsPanel = new ScrollPane();
        simsPanel.getStyleClass().add("scrollPane");
        simsPanel.setContent(simsGridPanel);

//-----------------------------------------------------------------------------------------------> Simulations Pane End

        rootPane = new BorderPane();
        rootPane.setLeft(leftPanel);
        rootPane.setTop(topPanel);
        rootPane.setCenter(homePanel);
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("styleDark.css").toExternalForm());


        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/btn/aleIcon.png")));
        primaryStage.setScene(new Scene(rootPane, width, height));
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
            dbCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(Exception e){
            e.printStackTrace();
        }

        return estCon;
    }

    public boolean validLogin(String username, String password){
        boolean validUser = false;
        try {

            dbStm = dbCon.prepareStatement("SELECT * FROM userInfo");
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


}
