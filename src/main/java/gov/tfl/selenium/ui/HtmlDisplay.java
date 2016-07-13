package gov.tfl.selenium.ui;


import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;


public class HtmlDisplay extends Application {
    private Scene scene;

    @Override public void start(Stage stage) {
        // create the scene
        stage.setTitle("Web View");
        Browser brows = new Browser();
        scene = new Scene(brows,750,500, Color.web("#666970"));
        stage.setScene(scene);
        //scene.getStylesheets().add("webviewsample/BrowserToolbar.css");
        stage.show();

        try {
            Thread.sleep(2000l);
            this.printScreen();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void printScreen() throws Exception{
        WritableImage snapshotImage = new WritableImage(500,500);
        scene.snapshot(snapshotImage);
        File outputfile = new File("/home/dev/anandpimple123");
        ImageIO.write(SwingFXUtils.fromFXImage(snapshotImage, null),"png",outputfile);
    }

    public static void main(String[] args){
        launch(args);
    }
}
class Browser extends Region {

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();

    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        webEngine.load("http://localhost:8080/oyster");
        //add the web view to the scene
        getChildren().add(browser);
        //browser.

    }

    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }

    @Override protected double computePrefWidth(double height) {
        return 750;
    }

    @Override protected double computePrefHeight(double width) {
        return 500;
    }
}