package com.silverfox.ale;

import com.silverfox.ale.calculators.calculator;
import com.silverfox.ale.calculators.scientificCalculator;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Main extends Application {

    //Objects
    aleChatClient client = new aleChatClient();
    aleTools tools = new aleTools();
    aleDB aleDB = new aleDB();
    login login = new login();

    // Database Variables
    Connection dbCon = null;
    PreparedStatement dbStm = null;
    ResultSet dbRs = null;
    Boolean estCon = false;

    String superUser = login.superUser;
    String chatRoom = "";

    // Get Screen Size

    /*The Graphics Device get screen size bu is commented because it is causing null pointers
    * The Screen size has just been set to a default of 1600 x 900*/
    //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    double width = screenSize.getWidth(); // gd.getDisplayMode().getWidth();
    double height = screenSize.getHeight(); // gd.getDisplayMode().getHeight();

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

    // Panes
    BorderPane rootPane;

    VBox leftPanel;

    HBox topPanel;
    HBox miscContainer;

    //Dashboard
    HBox dashBoardBase;
    ScrollPane dashboardPanel;
    GridPane dashboardGridPanel;
    Tooltip dashboardToolTip;

    //Chat Area
    VBox chatBox;
    TextField chatRoomField;
    TextArea messageArea;
    TextArea messageInputArea;

    //Profile
    ScrollPane profilePanel;
    GridPane profileGridPanel;
    Tooltip profileToolTip;
    Label userNameLbl;
    Label ageLbl;
    Label emailLbl;
    Label schoolLbl;
    Button profilePictureBtn;
    ResultSet profileContentRs;

    //Courses
    ScrollPane coursesPanel;
    GridPane coursesGridPanel;
    WebView courseView;
    WebEngine courseWebEngine;
    Tooltip courseToolTip;

    //Simulations
    ScrollPane simsPanel;
    GridPane simsGridPanel;
    Tooltip simsToolTip;

    //Text Editor
    ScrollPane textEditorPanel;
    Tooltip textEditorToolTip;
    HTMLEditor htmlEditor;
    Button saveDocBtn;

    //Wolfram Alpha
    ScrollPane wolframPanel;
    Tooltip wolframToolTip;

    //Wikipedia
    ScrollPane wikipediaPanel;
    Tooltip wikipediaToolTip;
    WebEngine wikipediaWebEngine;

    //Settings
    ScrollPane settingsPanel;
    GridPane settingsGridPanel;
    Tooltip settingsToolTip;

    //Buttons
    Button dashboardBtn;
    Button profileBtn;
    Button coursesBtn;
    Button simsBtn;
    Button textEditorBtn;
    Button wolframBtn;
    Button wikipediaBtn;
    Button settingsBtn;
    Button closeBtn;
    Button minimizeBtn;

    //Course Buttons
    Button watchVidBtn;

    Button chemistryBtn;
    Button physicsBtn;
    Button mathsBtn;
    Button bioBtn;

    //Simulation Btns
    Button physicsSims;

    Button balancingActBtn;
    Button forcesAndMotionBtn;

    Button balloonsAndStaticElectricityBtn;
    Button buildAnAtomBtn;
    Button colorVisionBtn;

    //Media Player
    MediaPlayer mediaPlayer;
    MediaView mediaView;

    //Other
    String calculatorName = "scientificCalculator";
    Button calcBtn;

    @Override
    public void start(Stage primaryStage) throws Exception{

        try{
            dbCon = DriverManager.getConnection("jdbc:mysql://192.168.1.6:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(Exception e){
            e.printStackTrace();
        }

//---------------------------------------------------------------------------------------------------> Login Pane Start

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
            systemClose();
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
            systemClose();
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
            if(connectToDB() == true){
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
                        System.out.println("<----- Sign Up Error Start ----->");
                        signupErr.printStackTrace();
                        System.out.println("<----- Sign Up Error End ----->");
                    }
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

//----------------------------------------------------------------------------------------------------> Top Panel Start

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

        miscContainer = new HBox();

        calcBtn = new Button();
        calcBtn.getStyleClass().addAll("calcBtn");
        calcBtn.setOnAction(e -> {
            calculator calculator = new calculator();
            scientificCalculator scientificCalculator = new scientificCalculator();
            calculator.start(calculatorName);
        });

        miscContainer.getChildren().add(calcBtn);

        topPanel = new HBox(1);
        topPanel.getStyleClass().add("topPanel");
        topPanel.setPrefWidth(width);
        topPanel.setAlignment(Pos.CENTER_RIGHT);
        topPanel.setPadding(new Insets(0,0,0,0));
        topPanel.getChildren().addAll(miscContainer, minimizeBtn, closeBtn);

//------------------------------------------------------------------------------------------------------> Top Panel End

//---------------------------------------------------------------------------------------------------> Left Panel Start

        Line initDivider = new Line();
        initDivider.setStartX(0.0f);
        initDivider.setEndX(205.0f);
        initDivider.setStroke(Color.GRAY);

        dashboardToolTip = new Tooltip();
        dashboardToolTip.setText("Dashboard");

        dashboardBtn = new Button("");
        dashboardBtn.getStyleClass().add("dashboardBtn");
        dashboardBtn.setTooltip(dashboardToolTip);
        dashboardBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(dashBoardBase);
        });

        profileToolTip = new Tooltip();
        profileToolTip.setText("Profile");

        profileBtn = new Button();
        profileBtn.getStyleClass().add("profileBtn");
        profileBtn.setTooltip(profileToolTip);
        profileBtn.setOnAction(e -> {
            resetBtns();
            //profileContentRs = aleDB.getUserInfo(superUser);
            //setProfileInfo();
            rootPane.setCenter(profilePanel);
        });

        courseToolTip = new Tooltip();
        courseToolTip.setText("Courses");

        coursesBtn = new Button("");
        coursesBtn.getStyleClass().add("coursesBtn");
        coursesBtn.setTooltip(courseToolTip);
        coursesBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(coursesPanel);
            miscContainer.getChildren().addAll(watchVidBtn);
            coursesPanel.setContent(coursesGridPanel);
        });

        Line mainDivider = new Line();
        mainDivider.setStartX(0.0f);
        mainDivider.setEndX(205.0f);
        mainDivider.setStroke(Color.GRAY);

        simsToolTip = new Tooltip();
        simsToolTip.setText("Simulations");

        simsBtn = new Button();
        simsBtn.getStyleClass().add("simsBtn");
        simsBtn.setTooltip(simsToolTip);
        simsBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(simsPanel);
            simsPanel.setContent(simsGridPanel);
        });

        textEditorToolTip = new Tooltip();
        textEditorToolTip.setText("Text Editor");

        textEditorBtn = new Button();
        textEditorBtn.getStyleClass().add("textEditorBtn");
        textEditorBtn.setTooltip(textEditorToolTip);
        textEditorBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(textEditorPanel);
            miscContainer.getChildren().addAll(saveDocBtn);
        });

        Line toolsDivider = new Line();
        toolsDivider.setStartX(0.0f);
        toolsDivider.setEndX(205.0f);
        toolsDivider.setStroke(Color.GRAY);

        wolframToolTip = new Tooltip();
        wolframToolTip.setText("Wolfram Alpha");

        wolframBtn = new Button();
        wolframBtn.getStyleClass().add("wolframBtn");
        wolframBtn.setTooltip(wolframToolTip);
        wolframBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(wolframPanel);
        });

        wikipediaToolTip = new Tooltip();
        wikipediaToolTip.setText("Wikipedia");

        wikipediaBtn = new Button();
        wikipediaBtn.getStyleClass().add("wikipediaBtn");
        wikipediaBtn.setTooltip(wikipediaToolTip);
        wikipediaBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(wikipediaPanel);
        });

        Line sitesDivider = new Line();
        sitesDivider.setStartX(0.0f);
        sitesDivider.setEndX(205.0f);
        sitesDivider.setStroke(Color.GRAY);

        settingsToolTip = new Tooltip("Settings");

        settingsBtn = new Button();
        settingsBtn.getStyleClass().add("settingsBtn");
        settingsBtn.setTooltip(settingsToolTip);
        settingsBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(settingsPanel);
        });


        leftPanel = new VBox(0);
        //leftPanel.setPrefWidth(1);
        leftPanel.getStyleClass().add("leftPane");
        leftPanel.getChildren().addAll(initDivider, dashboardBtn, profileBtn, coursesBtn, mainDivider, simsBtn,
                textEditorBtn, toolsDivider, wolframBtn, wikipediaBtn, sitesDivider, settingsBtn);

        topPanel = new HBox(1);
        topPanel.getStyleClass().add("topPanel");
        topPanel.setPrefWidth(width);
        topPanel.setAlignment(Pos.CENTER_RIGHT);
        topPanel.setPadding(new Insets(0,0,0,0));
        topPanel.getChildren().addAll(miscContainer, minimizeBtn, closeBtn);

//-----------------------------------------------------------------------------------------------------> Left Panel End

//-----------------------------------------------------------------------------------------------> Dashboard Pane Start

        final WebView webVid = new WebView();
        final WebEngine webVidEngine = webVid.getEngine();
        webVid.setPrefHeight(860);
        webVid.setPrefWidth(width - 118);
        webVidEngine.loadContent("");


        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Day");
        yAxis.setLabel("Score");
        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(
                xAxis, yAxis);

        lineChart.setTitle("Line Chart");
        XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
        series.setName("My Data");

        // populating the series with data
        series.getData().add(new XYChart.Data<Number, Number>(0.25, 36));
        series.getData().add(new XYChart.Data<Number, Number>(1, 23));
        series.getData().add(new XYChart.Data<Number, Number>(2, 114));
        series.getData().add(new XYChart.Data<Number, Number>(3, 15));
        series.getData().add(new XYChart.Data<Number, Number>(4, 124));
        lineChart.getData().add(series);
        lineChart.setPrefWidth(400);
        lineChart.setPrefHeight(300);
        lineChart.setLegendVisible(false);

        chatRoomField = new TextField();
        chatRoomField.getStyleClass().add("textField");
        chatRoomField.setPromptText("Enter Chat Room");
        chatRoomField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode() == KeyCode.ENTER){
                    chatRoom = chatRoomField.getText();
                    client.connect(messageArea, messageInputArea, superUser, chatRoom);
                }
            }
        });

        messageArea = new TextArea();
        messageArea.getStyleClass().add("textArea");
        messageArea.setWrapText(true);
        messageArea.setPrefHeight(740);
        messageArea.setEditable(false);

        messageInputArea = new TextArea();
        messageInputArea.getStyleClass().add("textArea");
        messageInputArea.setWrapText(true);
        messageInputArea.setPrefHeight(100);
        messageInputArea.setPromptText("Enter Message");
        messageInputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER){
                    client.send(messageArea, messageInputArea, superUser, chatRoom);
                    event.consume();
                }
            }
        });

        chatBox = new VBox();
        chatBox.setPrefWidth(250);
        chatBox.setMaxWidth(250);
        chatBox.getStyleClass().add("chatBox");
        chatBox.getChildren().addAll(chatRoomField,messageArea, messageInputArea);

        //client.test(messageArea, messageInputArea);

        dashboardGridPanel = new GridPane();
        dashboardGridPanel.getStyleClass().add("gridPane");
        dashboardGridPanel.setVgap(5);
        dashboardGridPanel.setHgap(5);
        dashboardGridPanel.setGridLinesVisible(false);
        dashboardGridPanel.setPrefWidth(width - 430);
        dashboardGridPanel.setPrefHeight(860);

        dashboardGridPanel.setColumnIndex(lineChart, 0);
        dashboardGridPanel.setRowIndex(lineChart, 0);
        dashboardGridPanel.getChildren().addAll(lineChart);

        dashboardPanel = new ScrollPane();
        dashboardPanel.getStyleClass().add("scrollPane");
        dashboardPanel.setPrefWidth(width - 400);
        dashboardPanel.setPrefHeight(860);
        dashboardPanel.setContent(dashboardGridPanel);

        dashBoardBase = new HBox();
        dashBoardBase.setPrefWidth(width - (leftPanel.getWidth() + chatBox.getWidth()));
        dashBoardBase.setPrefHeight(860);
        dashBoardBase.getChildren().addAll(dashboardPanel, chatBox);

//-------------------------------------------------------------------------------------------------> Dashboard Pane End

//-------------------------------------------------------------------------------------------------> Profile Pane Start

        profilePictureBtn = new Button();
        profilePictureBtn.getStyleClass().addAll("profilePictureBtn");

        profileGridPanel = new GridPane();
        profileGridPanel.getStyleClass().add("gridPane");
        profileGridPanel.setVgap(5);
        profileGridPanel.setHgap(5);
        profileGridPanel.setGridLinesVisible(true);
        profileGridPanel.setPrefWidth(width - 208);
        profileGridPanel.setPrefHeight(860);
        profileGridPanel.setAlignment(Pos.TOP_CENTER);

        profileGridPanel.setRowIndex(profilePictureBtn, 0);
        profileGridPanel.setColumnIndex(profilePictureBtn, 0);

        profilePanel = new ScrollPane();
        profilePanel.getStyleClass().add("scrollPane");
        profilePanel.setContent(profileGridPanel);


//---------------------------------------------------------------------------------------------------> Profile Pane End

//-------------------------------------------------------------------------------------------------> Courses Pane Start

        String course = "";

        //Media media = new Media("media.mp4");

        //mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setAutoPlay(true);

       // mediaView = new MediaView(mediaPlayer);


        watchVidBtn = new Button("Watch Video");
        watchVidBtn.getStyleClass().add("btn");
        watchVidBtn.setOnAction(e -> {

           // coursesPanel.setContent(mediaView);
        });

        chemistryBtn = new Button();
        chemistryBtn.getStyleClass().add("chemistryBtn");
        chemistryBtn.setOnAction(e -> {
            displayCourse("chemistry");
        });

        physicsBtn = new Button();
        physicsBtn.getStyleClass().add("physicsBtn");
        physicsBtn.setOnAction(e -> {
            displayCourse("physics");
        });

        mathsBtn = new Button();
        mathsBtn.getStyleClass().add("mathsBtn");

        bioBtn = new Button();
        bioBtn.getStyleClass().add("bioBtn");
        bioBtn.setOnAction(e -> {
            displayCourse("biology");
        });

        // Course Web View
        try {
            courseView = new WebView();
            courseWebEngine = courseView.getEngine();
            courseView.setPrefHeight(860);
            courseView.setPrefWidth(width - 208);
        }catch(Exception e){
            e.printStackTrace();
        }

        coursesGridPanel = new GridPane();
        coursesGridPanel.getStyleClass().add("gridPane");
        coursesGridPanel.setVgap(5);
        coursesGridPanel.setHgap(5);
        coursesGridPanel.setGridLinesVisible(false);
        coursesGridPanel.setPrefWidth(width - 208);
        coursesGridPanel.setPrefHeight(860);

        coursesGridPanel.setRowIndex(chemistryBtn, 1);
        coursesGridPanel.setColumnIndex(chemistryBtn, 1);
        coursesGridPanel.setRowIndex(physicsBtn, 1);
        coursesGridPanel.setColumnIndex(physicsBtn, 2);
        coursesGridPanel.setRowIndex(mathsBtn, 1);
        coursesGridPanel.setColumnIndex(mathsBtn, 3);
        coursesGridPanel.setRowIndex(bioBtn, 1);
        coursesGridPanel.setColumnIndex(bioBtn, 4);
        coursesGridPanel.getChildren().addAll(chemistryBtn, physicsBtn, mathsBtn, bioBtn);

        coursesPanel = new ScrollPane();
        coursesPanel.getStyleClass().add("scrollPane");
        coursesPanel.setPrefWidth(width - 118);
        coursesPanel.setPrefHeight(860);
        coursesPanel.setContent(coursesGridPanel);

//---------------------------------------------------------------------------------------------------> Courses Pane End

//---------------------------------------------------------------------------------------------> Simulations Pane Start
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        browser.setPrefHeight(860);
        browser.setPrefWidth(width - 208);

        Label motionLbl = new Label("Motion");
        motionLbl.getStyleClass().add("lbl");

        forcesAndMotionBtn = new Button();
        forcesAndMotionBtn.getStyleClass().add("forcesAndMotionBtn");
        forcesAndMotionBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/balancing-act/latest/balancing-act_en.html");
            simsPanel.setContent(browser);
        });

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

        buildAnAtomBtn = new Button();
        buildAnAtomBtn.getStyleClass().add("buildAnAtomBtn");
        buildAnAtomBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/build-an-atom/latest/build-an-atom_en.html");
            simsPanel.setContent(browser);
        });

        colorVisionBtn = new Button();
        colorVisionBtn.getStyleClass().add("colorVisionBtn");
        colorVisionBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/color-vision/latest/color-vision_en.html");
            simsPanel.setContent(browser);
        });

        simsGridPanel = new GridPane();
        simsGridPanel.getStyleClass().add("gridPane");
        simsGridPanel.setVgap(5);
        simsGridPanel.setHgap(5);
        simsGridPanel.setGridLinesVisible(false);
        simsGridPanel.setPrefWidth(width - 208);
        simsGridPanel.setPrefHeight(860);

        simsGridPanel.setRowIndex(motionLbl, 0);
        simsGridPanel.setColumnIndex(motionLbl, 0);
        simsGridPanel.setRowIndex(forcesAndMotionBtn, 1);
        simsGridPanel.setColumnIndex(forcesAndMotionBtn, 0);
        simsGridPanel.setRowIndex(balancingActBtn, 1);
        simsGridPanel.setColumnIndex(balancingActBtn, 1);
        simsGridPanel.setRowIndex(buildAnAtomBtn, 1);
        simsGridPanel.setColumnIndex(buildAnAtomBtn, 2);
        simsGridPanel.setRowIndex(colorVisionBtn, 1);
        simsGridPanel.setColumnIndex(colorVisionBtn, 3);
        simsGridPanel.getChildren().addAll(motionLbl, forcesAndMotionBtn, balancingActBtn, buildAnAtomBtn,
                colorVisionBtn);

        simsPanel = new ScrollPane();
        simsPanel.getStyleClass().add("scrollPane");
        simsPanel.setContent(simsGridPanel);

//-----------------------------------------------------------------------------------------------> Simulations Pane End

//---------------------------------------------------------------------------------------------> Text Editor Pane Start

        htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(860);
        htmlEditor.setPrefWidth(width - 208);

        //Prevents Scroll on Space Pressed
        htmlEditor.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getEventType() == KeyEvent.KEY_PRESSED){
                    if ( event.getCode() == KeyCode.SPACE ){
                        event.consume();
                    }
                }
            }
        });

        XWPFDocument document = new XWPFDocument();
        XWPFParagraph tmpParagraph = document.createParagraph();
        XWPFRun tmpRun = tmpParagraph.createRun();

        saveDocBtn = new Button();
        saveDocBtn.getStyleClass().add("btn");
        saveDocBtn.setText("Save");
        saveDocBtn.setOnAction(e -> {
            String saveName = JOptionPane.showInputDialog("Enter File Name");
            tmpRun.setText(tools.stripHTMLTags(htmlEditor.getHtmlText()));
            tmpRun.setFontSize(12);
            try {
                document.write(new FileOutputStream(new File("C:\\Users\\Josh\\Documents\\" + saveName + ".docx")));
            }catch(Exception q){ q.printStackTrace();}

        });

        textEditorPanel = new ScrollPane();
        textEditorPanel.getStyleClass().add("scrollPane");
        textEditorPanel.setContent(htmlEditor);

//-----------------------------------------------------------------------------------------------> Text Editor Pane End

//-------------------------------------------------------------------------------------------------> Wolfram Pane Start

        Boolean wolframActive = false;
        try {
            final WebView wolframWeb = new WebView();
            wolframWeb.getStyleClass().add("webView");
            final WebEngine wolframWebEngine = wolframWeb.getEngine();
            wolframWeb.setPrefHeight(860);
            wolframWeb.setPrefWidth(width - 208);
            if (wolframActive == false){
                wolframWebEngine.load("http://www.wolframalpha.com/");
                wolframActive = true;
            }
            wolframPanel = new ScrollPane();
            wolframPanel.setContent(wolframWeb);
        }catch(Exception e){
            e.printStackTrace();
        }

//---------------------------------------------------------------------------------------------------> Wolfram Pane End

//-------------------------------------------------------------------------------------------------> Wikipedia Pane Start

        Boolean wikipediaActive = false;
        try {
            final WebView wikipediaWeb = new WebView();
            wikipediaWeb.getStyleClass().add("scrollPane");
            wikipediaWebEngine = wikipediaWeb.getEngine();
            wikipediaWeb.setPrefHeight(860);
            wikipediaWeb.setPrefWidth(width - 208);
            if(wikipediaActive == false){
                wikipediaWebEngine.load("https://en.wikipedia.org/wiki/Main_Page");
                wikipediaActive = true;
            }
            wikipediaPanel = new ScrollPane();
            wikipediaPanel.setContent(wikipediaWeb);
        }catch(Exception e){
            e.printStackTrace();
        }

//--------------------------------------------------------------------------------------------------> Wikipedia Pane End

//-------------------------------------------------------------------------------------------------> Settings Pane Start

        settingsGridPanel = new GridPane();
        settingsGridPanel.getStyleClass().add("gridPanel");

        settingsPanel = new ScrollPane();
        settingsPanel.getStyleClass().add("scrollPane");

//---------------------------------------------------------------------------------------------------> Settings Pane End
        rootPane = new BorderPane();
        rootPane.setLeft(leftPanel);
        rootPane.setTop(topPanel);
        rootPane.setCenter(dashBoardBase);
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());


        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setScene(new Scene(loginPane, 300, 123));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void systemClose(){

        if(client.isConnected == true){
            client.disconnect(messageArea, messageInputArea, superUser);
        }

        try{
            if (dbCon != null){
                dbCon.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void systemMinimize(){

    }

    public boolean connectToDB(){
        boolean estCon = false;
        try{
            dbCon = DriverManager.getConnection("jdbc:mysql://192.168.1.6:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Could not connect to db");
        }

        return estCon;
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

    public void resetBtns(){
        //dashboardBtn.getStyleClass().addAll("homeBtn");
        //coursesBtn.getStyleClass().add("coursesBtn");

        miscContainer.getChildren().removeAll(saveDocBtn, watchVidBtn);
    }

    public void displayCourse(String courseName){
        //File f = new File("C:\\Users\\Josh\\IdeaProjects\\ALE\\src\\com\\silverfox\\ale\\course\\"
                //+ courseName + ".html");
        //f.toURI().toURL().toString()

        try{
            courseWebEngine.load("http://localhost:63342/ALE/com/silverfox/ale/course/" + courseName + ".html");

            coursesPanel.setContent(courseView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void setProfileInfo(){
        String profileUserName = "";
        String profileEmail = "";
        String profileAge = "";
        String profileSchool = "";
        String profileCountry = "";
        String profileCity = "";

        try {
            while (profileContentRs.next()){

                profileUserName = profileContentRs.getString("username");
                System.out.println(profileUserName);

                profileEmail = profileContentRs.getString("email");
                System.out.println(profileEmail);

                profileAge = profileContentRs.getString("age");
                System.out.println(profileAge);

                profileSchool = profileContentRs.getString("school");
                System.out.println(profileSchool);

                profileCountry = profileContentRs.getString("country");
                System.out.println(profileCountry);

                profileCity = profileContentRs.getString("city");
                System.out.println(profileCity);

            }
        }catch (Exception profileSetContentException){
            System.out.println("profileSetContentException: ");
            profileSetContentException.printStackTrace();
        }

        userNameLbl.setText(profileUserName);
        emailLbl.setText(profileEmail);
        ageLbl.setText(profileAge);
        schoolLbl.setText(profileSchool);

        profileGridPanel.setRowIndex(userNameLbl, 1);
        profileGridPanel.setColumnIndex(userNameLbl, 0);
        profileGridPanel.setRowIndex(emailLbl, 2);
        profileGridPanel.setColumnIndex(emailLbl, 0);
        profileGridPanel.setRowIndex(ageLbl, 3);
        profileGridPanel.setColumnIndex(ageLbl, 0);
        profileGridPanel.setRowIndex(schoolLbl, 4);
        profileGridPanel.setColumnIndex(schoolLbl, 0);
        profileGridPanel.getChildren().addAll(profilePictureBtn, userNameLbl, emailLbl, ageLbl, schoolLbl);
    }

}
