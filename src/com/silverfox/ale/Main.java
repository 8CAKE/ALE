package com.silverfox.ale;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.TouchPoint;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    String superUser = "";

    // Database Variables
    Connection dbCon = null;
    PreparedStatement dbStm = null;
    ResultSet dbRs = null;
    Boolean estCon = false;

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
    Button finalSignUpBtn;

    // Panes
    BorderPane rootPane;

    VBox leftPanel;

    HBox topPanel;
    HBox miscContainer;

    //Dashboard
    ScrollPane dashboardPanel;
    GridPane dashboardGridPanel;
    Tooltip dashboardToolTip;

    //Profile
    ScrollPane profilePanel;
    GridPane profileGridPanel;
    Tooltip profileToolTip;
    Label userNameLbl;

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
    HTMLEditor htmlEditor;
    Button saveDocBtn;

    //Wolfram Alpha
    ScrollPane wolframPanel;
    Tooltip wolframToolTip;

    //Buttons
    Button dashboardBtn;
    Button profileBtn;
    Button coursesBtn;
    Button simsBtn;
    Button textEditorBtn;
    Button wolframBtn;
    Button closeBtn;
    Button minimizeBtn;

    //Course Btns
    Button chemistryBtn;
    Button physicsBtn;
    Button mathsBtn;
    Button bioBtn;

    //Simulation Btns
    Button alphaDecayBtn;
    Button balancingActBtn;
    Button balloonsAndStaticElectricityBtn;
    Button buildAnAtomBtn;

    @Override
    public void start(Stage primaryStage) throws Exception{

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

        //For quick access
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
            Scene signUpScene = new Scene(signUpPane, 300, 200);
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

        finalSignUpBtn = new Button("Sign Up");
        finalSignUpBtn.getStyleClass().add("loginBtn");
        finalSignUpBtn.setOnAction(e -> {
            if(connectToDB() == true){
                if(validSignUp(usernameField.getText(), passwordField.getText(), repeatPasswordField.getText(),
                        emailField.getText(), ageField.getText(), schoolField.getText()) == null){
                    try{
                        PreparedStatement psSignUp = null;
                        ResultSet rsSignUp = null;
                        psSignUp = dbCon.prepareStatement("INSERT INTO userInfo(username, email, password, age, dob, " +
                                "school, country, city) VALUES(" + usernameField.getText() + "," + emailField.getText()
                                + "," + passwordField.getText() + "," + ageField.getText() + "," + "" + ","
                                + schoolField.getText() + "," + ")");
                        psSignUp.executeQuery();
                    }catch (Exception signuperr){
                        signuperr.printStackTrace();
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
                schoolField, signUpBtnBox);


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

        topPanel = new HBox(1);
        topPanel.getStyleClass().add("topPanel");
        topPanel.setPrefWidth(width);
        topPanel.setAlignment(Pos.CENTER_RIGHT);
        topPanel.setPadding(new Insets(0,0,0,0));
        topPanel.getChildren().addAll(miscContainer, minimizeBtn, closeBtn);

//------------------------------------------------------------------------------------------------------> Top Panel End

//---------------------------------------------------------------------------------------------------> Left Panel Start

        dashboardToolTip = new Tooltip();
        dashboardToolTip.setText("Dashboard");

        dashboardBtn = new Button("");
        dashboardBtn.getStyleClass().add("homeBtn");
        dashboardBtn.setTooltip(dashboardToolTip);
        dashboardBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(dashboardGridPanel);
        });

        profileToolTip = new Tooltip();
        profileToolTip.setText("Profile");

        profileBtn = new Button();
        profileBtn.getStyleClass().add("profileBtn");
        profileBtn.setTooltip(profileToolTip);
        profileBtn.setOnAction(e -> {
            resetBtns();
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
            coursesPanel.setContent(coursesGridPanel);
            //miscContainer.getChildren().addAll(saveDocBtn);
        });

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

        profileToolTip = new Tooltip();
        profileToolTip.setText("Profile");

        textEditorBtn = new Button();
        textEditorBtn.getStyleClass().add("textEditorBtn");
        //textEditorBtn.setTooltip(profileToolTip);
        textEditorBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(textEditorPanel);
            miscContainer.getChildren().addAll(saveDocBtn);
        });

        wolframToolTip = new Tooltip();
        wolframToolTip.setText("Wolfram Alpha");

        wolframBtn = new Button();
        wolframBtn.getStyleClass().add("wolframBtn");
        wolframBtn.setTooltip(wolframToolTip);
        wolframBtn.setOnAction(e -> {
            resetBtns();
            rootPane.setCenter(wolframPanel);
        });

        leftPanel = new VBox(0);
        //leftPanel.setPrefWidth(1);
        leftPanel.getStyleClass().add("leftPane");
        leftPanel.getChildren().addAll(dashboardBtn, profileBtn, coursesBtn, simsBtn, textEditorBtn, wolframBtn);

        miscContainer = new HBox();

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

        dashboardGridPanel = new GridPane();
        dashboardGridPanel.getStyleClass().add("gridPane");
        dashboardGridPanel.setVgap(5);
        dashboardGridPanel.setHgap(5);
        dashboardGridPanel.setGridLinesVisible(false);
        dashboardGridPanel.setPrefWidth(width - 208);
        dashboardGridPanel.setPrefHeight(860);

        dashboardGridPanel.setColumnIndex(lineChart, 0);
        dashboardGridPanel.setRowIndex(lineChart, 0);
        dashboardGridPanel.getChildren().addAll(lineChart);

        dashboardPanel = new ScrollPane();
        dashboardPanel.getStyleClass().add("centerPanel");
        dashboardPanel.setPrefWidth(width - 208);
        dashboardPanel.setPrefHeight(860);
        dashboardPanel.setContent(dashboardGridPanel);

//-------------------------------------------------------------------------------------------------> Dashboard Pane End

//-------------------------------------------------------------------------------------------------> Profile Pane Start

        userNameLbl = new Label(superUser);
        userNameLbl.getStyleClass().add("lbl");

        profileGridPanel = new GridPane();
        profileGridPanel.getStyleClass().add("gridPane");
        profileGridPanel.setVgap(5);
        profileGridPanel.setHgap(5);
        profileGridPanel.setGridLinesVisible(false);
        profileGridPanel.setPrefWidth(width - 208);
        profileGridPanel.setPrefHeight(860);

        profilePanel = new ScrollPane();
        profilePanel.getStyleClass().add("scrollPane");
        profilePanel.setContent(profileGridPanel);


//---------------------------------------------------------------------------------------------------> Profile Pane End

//-------------------------------------------------------------------------------------------------> Courses Pane Start

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

        buildAnAtomBtn = new Button();
        buildAnAtomBtn.getStyleClass().add("buildAnAtomBtn");
        buildAnAtomBtn.setOnAction(e -> {
            webEngine.load("https://phet.colorado.edu/sims/html/build-an-atom/latest/build-an-atom_en.html");
            simsPanel.setContent(browser);
        });

        simsGridPanel = new GridPane();
        simsGridPanel.getStyleClass().add("gridPane");
        simsGridPanel.setVgap(5);
        simsGridPanel.setHgap(5);
        simsGridPanel.setGridLinesVisible(false);
        simsGridPanel.setPrefWidth(width - 208);
        simsGridPanel.setPrefHeight(860);

        simsGridPanel.setRowIndex(physLbl, 0);
        simsGridPanel.setColumnIndex(physLbl, 0);
        simsGridPanel.setRowIndex(balancingActBtn, 1);
        simsGridPanel.setColumnIndex(balancingActBtn, 0);
        simsGridPanel.setRowIndex(balloonsAndStaticElectricityBtn, 1);
        simsGridPanel.setColumnIndex(balloonsAndStaticElectricityBtn, 1);
        simsGridPanel.setRowIndex(buildAnAtomBtn, 1);
        simsGridPanel.setColumnIndex(buildAnAtomBtn, 2);
        simsGridPanel.getChildren().addAll(physLbl, balancingActBtn, balloonsAndStaticElectricityBtn, buildAnAtomBtn);

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
        saveDocBtn.getStyleClass().add("saveDocBtn");
        saveDocBtn.setText("Save");
        saveDocBtn.setOnAction(e -> {
            String saveName = JOptionPane.showInputDialog("Enter File Name");
            tmpRun.setText(stripHTMLTags(htmlEditor.getHtmlText()));
            tmpRun.setFontSize(18);
            try {
                document.write(new FileOutputStream(new File("C:\\Users\\Josh\\Documents\\" + saveName + ".docx")));
            }catch(Exception q){ q.printStackTrace();}

        });

        textEditorPanel = new ScrollPane();
        textEditorPanel.getStyleClass().add("scrollPane");
        textEditorPanel.setContent(htmlEditor);

//-----------------------------------------------------------------------------------------------> Text Editor Pane End

//-------------------------------------------------------------------------------------------------> Wolfram Pane Start

        try {
            final WebView wolframWeb = new WebView();
            final WebEngine wolframWebEngine = wolframWeb.getEngine();
            wolframWeb.setPrefHeight(860);
            wolframWeb.setPrefWidth(width - 208);
            wolframWebEngine.load("http://www.wolframalpha.com/");
            wolframPanel = new ScrollPane();
            wolframPanel.setContent(wolframWeb);
        }catch(Exception e){
            e.printStackTrace();
        }

//---------------------------------------------------------------------------------------------------> Wolfram Pane End

        rootPane = new BorderPane();
        rootPane.setLeft(leftPanel);
        rootPane.setTop(topPanel);
        rootPane.setCenter(dashboardPanel);
        rootPane.getStyleClass().add("rootPane");
        rootPane.getStylesheets().add(Main.class.getResource("css/styleDark.css").toExternalForm());


        primaryStage.setTitle("ALE");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new javafx.scene.image.Image(Main.class
                .getResourceAsStream("img/btn/aleIcon.png")));
        primaryStage.setScene(new Scene(loginPane, 300, 120));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void systemClose(){
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

        miscContainer.getChildren().removeAll(saveDocBtn);
    }

    public void displayCourse(String courseName){
        File f = new File("C:\\Users\\Josh\\IdeaProjects\\ALE\\src\\com\\silverfox\\ale\\course\\"
                + courseName + ".html");

        try{
            courseWebEngine.load(f.toURI().toURL().toString());
            coursesPanel.setContent(courseView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private String stripHTMLTags(String htmlText) {

        Pattern pattern = Pattern.compile("<[^>]*>");
        Matcher matcher = pattern.matcher(htmlText);
        final StringBuffer sb = new StringBuffer(htmlText.length());
        while(matcher.find()) {
            matcher.appendReplacement(sb, " ");
        }
        matcher.appendTail(sb);
        return(sb.toString().trim());

    }


}
