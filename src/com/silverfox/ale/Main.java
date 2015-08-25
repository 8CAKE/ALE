package com.silverfox.ale;

import com.silverfox.ale.aleXML.aleXMLParser;
import com.silverfox.ale.calculators.calculator;
import com.silverfox.ale.calculators.scientificCalculator;
import com.silverfox.ale.course.biology.biology;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;

public class Main extends Application {



    //Objects
    aleChatClient client = new aleChatClient();
    aleTools tools = new aleTools();
    aleXMLParser xmlParser = new aleXMLParser();

    biology biologyCourse = new biology();


    // Database Variables
    Connection dbCon = null;
    PreparedStatement dbStm = null;
    ResultSet dbRs = null;
    Boolean estCon = false;

    String superUser = "";
    String chatRoom = "";

    // Get Screen Size

    /*The Graphics Device get screen size bu is commented because it is causing null pointers
    * The Screen size has just been set to a default of 1600 x 900*/
    //GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();


    Rectangle2D screenSize;
    double width;
    double height;

    double programWidth;
    double programHeight;

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
    VBox simsBox;
    FlowPane motionSimsFlowPane;
    FlowPane soundAndWavesFlowPane;
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

    //Simulation Buttons
    Button physicsSims;

    Button balancingActBtn;
    Button forcesAndMotionBtn;
    Button energySkateParkBtn;

    Button wavesOnAStringBtn;

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

        try {
            screenSize = Screen.getPrimary().getVisualBounds();
            width = screenSize.getWidth(); // gd.getDisplayMode().getWidth();
            height = screenSize.getHeight(); // gd.getDisplayMode().getHeight();
        }catch(Exception excep){
            System.out.println("<----- Exception in  Get Screen Size ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
        }

        try{
            dbCon = DriverManager.getConnection("jdbc:mysql://192.168.1.6:3306/ale", "Root", "oqu#$XQgHFzDj@1MGg1G8");
            estCon = true;
        }catch(SQLException sqlExcep){
            System.out.println("<----- SQL Exception in Establishing Database Connection ----->");
            sqlExcep.printStackTrace();
            System.out.println("<---------->\n");
        }

        xmlParser.generateUserInfo();
        superUser = xmlParser.getSuperUser();



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

//----------------------------------------------------------------------------------------------> Navigation Panel Start

        Line initDivider = new Line();
        initDivider.setStartX(0.0f);
        initDivider.setEndX(205.0f);
        initDivider.setStroke(Color.GRAY);

        // <----- Dashboard ----->
        dashboardToolTip = new Tooltip("Dashboard");

        dashboardBtn = new Button("");
        dashboardBtn.getStyleClass().add("dashboardBtn");
        dashboardBtn.setTooltip(dashboardToolTip);
        dashboardBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(dashBoardBase);
        });

        // <----- Profile ----->
        profileToolTip = new Tooltip("Profile");

        profileBtn = new Button();
        profileBtn.getStyleClass().add("profileBtn");
        profileBtn.setTooltip(profileToolTip);
        profileBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(profilePanel);
        });

        // <----- Courses ----->
        courseToolTip = new Tooltip("Courses");

        coursesBtn = new Button("");
        coursesBtn.getStyleClass().add("coursesBtn");
        coursesBtn.setTooltip(courseToolTip);
        coursesBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(coursesPanel);
            //miscContainer.getChildren().addAll(watchVidBtn);
            coursesPanel.setContent(coursesGridPanel);
        });

        Line mainDivider = new Line();
        mainDivider.setStartX(0.0f);
        mainDivider.setEndX(205.0f);
        mainDivider.setStroke(Color.GRAY);

        // <----- Simulations ----->
        simsToolTip = new Tooltip("Simulations");

        simsBtn = new Button();
        simsBtn.getStyleClass().add("simsBtn");
        simsBtn.setTooltip(simsToolTip);
        simsBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(simsPanel);
            simsPanel.setContent(simsGridPanel);
        });

        // <----- Text Editor ----->
        textEditorToolTip = new Tooltip("Text Editor");

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

        // <----- Wolfram Alpha ----->
        wolframToolTip = new Tooltip("Wolfram Alpha");

        wolframBtn = new Button();
        wolframBtn.getStyleClass().add("wolframBtn");
        wolframBtn.setTooltip(wolframToolTip);
        wolframBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(wolframPanel);
        });


        // <----- Wikipedia ----->
        wikipediaToolTip = new Tooltip();

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

        // <----- Settings ----->
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

//------------------------------------------------------------------------------------------------> Navigation Panel End

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

        String profileUserName = xmlParser.getSuperUser();

        String profileEmail = xmlParser.getEmail();
        String profileAge = xmlParser.getAge();
        String profileSchool = xmlParser.getSchool();
        String profileCountry = "";
        String profileCity = "";

        userNameLbl = new Label(profileUserName);
        userNameLbl.getStyleClass().add("profileLbl");
        userNameLbl.setAlignment(Pos.CENTER);

        emailLbl = new Label(profileEmail);
        emailLbl.getStyleClass().add("profileLbl");

        ageLbl = new Label(profileAge);
        ageLbl.getStyleClass().add("profileLbl");

        schoolLbl = new Label(profileSchool);
        schoolLbl.getStyleClass().add("profileLbl");

        profileGridPanel = new GridPane();
        profileGridPanel.getStyleClass().add("gridPane");
        profileGridPanel.setVgap(5);
        profileGridPanel.setHgap(5);
        profileGridPanel.setGridLinesVisible(false);
        profileGridPanel.setPrefWidth(width - 208);
        profileGridPanel.setPrefHeight(860);
        profileGridPanel.setAlignment(Pos.TOP_CENTER);

        profileGridPanel.setRowIndex(profilePictureBtn, 0);
        profileGridPanel.setColumnIndex(profilePictureBtn, 0);
        profileGridPanel.setRowIndex(userNameLbl, 1);
        profileGridPanel.setColumnIndex(userNameLbl, 0);
        profileGridPanel.setRowIndex(emailLbl, 2);
        profileGridPanel.setColumnIndex(emailLbl, 0);
        profileGridPanel.setRowIndex(ageLbl, 3);
        profileGridPanel.setColumnIndex(ageLbl, 0);
        profileGridPanel.setRowIndex(schoolLbl, 4);
        profileGridPanel.setColumnIndex(schoolLbl, 0);
        profileGridPanel.getChildren().addAll(profilePictureBtn, userNameLbl, emailLbl, ageLbl, schoolLbl);

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
            rootPane.setCenter(biologyCourse.biologyPane());
        });

        // Course Web View
        try {
            courseView = new WebView();
            courseWebEngine = courseView.getEngine();
            courseView.setPrefHeight(860);
            courseView.setPrefWidth(width - 208);
        }catch(Exception excep){
            System.out.println("<----- Exception in Course Web ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
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

        /*
        File phetImageFile = new File("img/styleDark/poweredByPHET.png");
        String phetImageURL = phetImageFile.toURI().toURL().toString();
        Image phetImage = new Image(phetImageURL, false);
        */

        final ImageView phetImageView = new ImageView();
        final Image phetImage = new Image(Main.class.getResourceAsStream("img/styleDark/poweredByPHET.png"));
        phetImageView.setImage(phetImage);

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

        energySkateParkBtn = new Button();
        energySkateParkBtn.getStyleClass().add("energySkateParkBtn");
        energySkateParkBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/energy-skate-park-basics/latest/" +
                    "energy-skate-park-basics_en.html");
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

        Label soundAndWavesLbl = new Label("Sound and Waves");
        soundAndWavesLbl.getStyleClass().add("lbl");

        wavesOnAStringBtn = new Button();
        wavesOnAStringBtn.getStyleClass().add("wavesOnAStringBtn");
        wavesOnAStringBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/wave-on-a-string/latest/wave-on-a-string_en.html");
            simsPanel.setContent(browser);
        });

        /*
        motionSimsFlowPane = new FlowPane();
        motionSimsFlowPane.getStyleClass().add("flowPane");
        motionSimsFlowPane.setVgap(5);
        motionSimsFlowPane.setHgap(5);
        motionSimsFlowPane.setAlignment(Pos.TOP_LEFT);
        motionSimsFlowPane.getChildren().addAll(forcesAndMotionBtn, balancingActBtn, energySkateParkBtn,
                buildAnAtomBtn, colorVisionBtn, wavesOnAStringBtn);


        soundAndWavesFlowPane = new FlowPane();
        soundAndWavesFlowPane.getStyleClass().add("flowPane");
        soundAndWavesFlowPane.setVgap(5);
        soundAndWavesFlowPane.setHgap(5);
        soundAndWavesFlowPane.setAlignment(Pos.TOP_LEFT);
        soundAndWavesFlowPane.getChildren().addAll(wavesOnAStringBtn);


        simsBox = new VBox();
        simsBox.getStyleClass().add("vbox");
        simsBox.setPrefHeight(height);
        simsBox.setPrefWidth(width);
        simsBox.getChildren().addAll(motionLbl, motionSimsFlowPane, soundAndWavesLbl, soundAndWavesFlowPane);
        */


        simsGridPanel = new GridPane();
        simsGridPanel.getStyleClass().add("gridPane");
        simsGridPanel.setVgap(5);
        simsGridPanel.setHgap(5);
        simsGridPanel.setGridLinesVisible(false);
        simsGridPanel.setPrefWidth(width - 208);
        simsGridPanel.setPrefHeight(860);

        simsGridPanel.setRowIndex(phetImageView, 0);
        simsGridPanel.setColumnIndex(phetImageView, 4);

        simsGridPanel.setRowIndex(motionLbl, 0);
        simsGridPanel.setColumnIndex(motionLbl, 0);
        simsGridPanel.setRowIndex(forcesAndMotionBtn, 1);
        simsGridPanel.setColumnIndex(forcesAndMotionBtn, 0);
        simsGridPanel.setRowIndex(balancingActBtn, 1);
        simsGridPanel.setColumnIndex(balancingActBtn, 1);
        simsGridPanel.setRowIndex(energySkateParkBtn, 1);
        simsGridPanel.setColumnIndex(energySkateParkBtn, 2);
        simsGridPanel.setRowIndex(buildAnAtomBtn, 1);
        simsGridPanel.setColumnIndex(buildAnAtomBtn, 3);
        simsGridPanel.setRowIndex(colorVisionBtn, 1);
        simsGridPanel.setColumnIndex(colorVisionBtn, 4);

        simsGridPanel.setRowIndex(soundAndWavesLbl, 2);
        simsGridPanel.setColumnIndex(soundAndWavesLbl, 0);
        simsGridPanel.setColumnSpan(soundAndWavesLbl, 4);
        simsGridPanel.setRowIndex(wavesOnAStringBtn, 3);
        simsGridPanel.setColumnIndex(wavesOnAStringBtn, 0);

        simsGridPanel.getChildren().addAll(phetImageView, motionLbl, forcesAndMotionBtn, balancingActBtn, energySkateParkBtn,
                buildAnAtomBtn, colorVisionBtn, soundAndWavesLbl, wavesOnAStringBtn);


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
            tmpRun.setText(tools.stripHTMLTags(htmlEditor.getHtmlText()));
            tmpRun.setFontSize(12);
            saveDocument(document, primaryStage);

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
        }catch(Exception excep){
            System.out.println("<----- Exception in Wolfram Alpha Web ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
        }

//---------------------------------------------------------------------------------------------------> Wolfram Pane End

//------------------------------------------------------------------------------------------------> Wikipedia Pane Start

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
        settingsGridPanel.getStyleClass().add("gridPane");
        settingsGridPanel.setPrefWidth(width - 208);
        settingsGridPanel.setPrefHeight(height);
        settingsGridPanel.setVgap(5);
        settingsGridPanel.setHgap(5);

        settingsPanel = new ScrollPane();
        settingsPanel.getStyleClass().add("scrollPane");
        settingsPanel.setContent(settingsGridPanel);

//---------------------------------------------------------------------------------------------------> Settings Pane End
        rootPane = new BorderPane();
        rootPane.setLeft(leftPanel);
        rootPane.setTop(topPanel);
        rootPane.setCenter(dashBoardBase);
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());

        programWidth = primaryStage.getWidth();
        programHeight = primaryStage.getHeight();

        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/aleIcon.png")));
        primaryStage.setScene(new Scene(rootPane, width, height));
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

    public void saveDocument(XWPFDocument document, Stage stage){
        try {
            FileChooser saveDocument = new FileChooser();
            saveDocument.setTitle("Save Document");
            saveDocument.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Document(*.docx)", "*.docx"));
            File file = saveDocument.showSaveDialog(stage);
            document.write(new FileOutputStream(file));

        }catch(IOException ioExcep){
            System.out.println("<----- IO Exception in Save Document ----->");
            ioExcep.printStackTrace();
            System.out.println("<---------->\n");
        }
    }

    public Main() {
    }

    public double getWidth() {
        return width;
    }
}
