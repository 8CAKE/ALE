package com.silverfox.ale.calculators;

import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class scientificCalculator  {

    public static void main(String[] args) {}

    public void start(String calc) {

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        try{
            webEngine.load("http://localhost:63342/ALE/com/silverfox/ale/calculators/" + calc + ".html");
        }catch (Exception e){
            e.printStackTrace();
        }

        Stage primaryStage = new Stage();
        primaryStage.setResizable(false);
        //primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setTitle("Scientific Calculator");
        primaryStage.getIcons().add(new javafx.scene.image.Image(scientificCalculator.class
                .getResourceAsStream("../img/btn/aleIcon.png")));
        primaryStage.setScene(new Scene(browser, 300, 490));
        primaryStage.show();
    }
}
