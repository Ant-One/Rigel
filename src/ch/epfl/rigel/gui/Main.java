package ch.epfl.rigel.gui;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root=new BorderPane();


        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("Rigel");

    }
}
