package ch.epfl.rigel.gui;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root=new BorderPane();

        root.setTop(controlBarre());
        root.setCenter(sky());
        root.setBottom(infoBarre());


        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("Rigel");

    }

    private HBox controlBarre(){

        return null;
    }

    private Pane sky(){
    return null;
    }

    private BorderPane infoBarre(){
        return null;
    }


}
