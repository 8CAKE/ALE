package com.silverfox.ale.course.biology;

import com.silverfox.ale.Main;
import com.silverfox.ale.aleXML.aleXMLParser;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class biology extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    aleXMLParser xmlParser = new aleXMLParser();

    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();

    WebEngine biologyWebEngine;
    WebView biologyWebView;

    ScrollPane biologyPane;
    GridPane biologyGridPane;

    //Anatomy and Physiology Buttons
    Button anatomyBtn;
    Label systemsLbl;
    Button skeletalSystemBtn;
    Button digestiveSystemBtn;
    Button reproductiveSystemBtn;

    @Override
    public void start(Stage primaryStage) {

    }

    public ScrollPane biologyPane(){


        anatomyBtn = new Button();
        anatomyBtn.getStyleClass().add("anatomyBtn");
        anatomyBtn.setOnAction(e -> {
            setAnatomyPane();
        });

        biologyGridPane = new GridPane();
        biologyGridPane.getStyleClass().add("gridPane");
        biologyGridPane.setVgap(5);
        biologyGridPane.setHgap(5);
        biologyGridPane.setGridLinesVisible(false);
        biologyGridPane.setPrefWidth(width - 208);
        biologyGridPane.setPrefHeight(860);

        biologyGridPane.setRowIndex(anatomyBtn, 1);
        biologyGridPane.setColumnIndex(anatomyBtn, 1);

        biologyGridPane.getChildren().addAll(anatomyBtn);

        biologyPane = new ScrollPane();
        biologyPane.getStyleClass().add("scrollPane");
        biologyPane.setPrefWidth(width - 118);
        biologyPane.setPrefHeight(860);
        biologyPane.setContent(biologyGridPane);

        return biologyPane;
    }

    public void setAnatomyPane(){

        systemsLbl = new Label("Systems");
        systemsLbl.getStyleClass().add("lbl");

        skeletalSystemBtn = new Button();
        skeletalSystemBtn.getStyleClass().add("skeletalSystemBtn");
        skeletalSystemBtn.setOnAction(e -> {
            displayCourse("anatomy/theSkeletalSystem");
        });

        digestiveSystemBtn = new Button();
        digestiveSystemBtn.getStyleClass().add("digestiveSystemBtn");
        digestiveSystemBtn.setOnAction(e -> {
            displayCourse("anatomy/theDigestiveSystem");
        });

        reproductiveSystemBtn = new Button();
        reproductiveSystemBtn.getStyleClass().add("reproductiveSystemBtn");
        reproductiveSystemBtn.setOnAction(e -> {
            displayCourse("anatomy/theReproductiveSystem");
        });

        biologyGridPane.getChildren().removeAll(anatomyBtn);

        biologyGridPane.setRowIndex(systemsLbl, 0);
        biologyGridPane.setColumnIndex(systemsLbl, 1);
        biologyGridPane.setRowIndex(skeletalSystemBtn, 1);
        biologyGridPane.setColumnIndex(skeletalSystemBtn, 1);
        biologyGridPane.setRowIndex(digestiveSystemBtn, 1);
        biologyGridPane.setColumnIndex(digestiveSystemBtn, 2);
        biologyGridPane.setRowIndex(reproductiveSystemBtn, 1);
        biologyGridPane.setColumnIndex(reproductiveSystemBtn, 3);

        biologyGridPane.getChildren().addAll(systemsLbl, skeletalSystemBtn, digestiveSystemBtn, reproductiveSystemBtn);

    }

    public void displayCourse(String courseName){

        try {
            biologyWebView = new WebView();
            biologyWebEngine = biologyWebView.getEngine();
            biologyWebView.setPrefHeight(860);
            biologyWebView.setPrefWidth(width - 208);
        }catch(Exception excep){
            System.out.println("<----- Exception in Biology Web View ----->");
            excep.printStackTrace();
            System.out.println("<---------->\n");
        }

        try{
            biologyWebEngine.load("http://localhost:63342/ALE/com/silverfox/ale/course/biology/" + courseName + ".html");
            biologyPane.setContent(biologyWebView);
        }catch (Exception excep){
            System.out.println("<----- Exception in Loading Biology Course ----->");
            excep.printStackTrace();
            System.out.println("<---------->");
        }

    }


}
