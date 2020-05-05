package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.time.ZonedDateTime;
import java.util.function.UnaryOperator;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {


        //all the Bean needed

        ZonedDateTime when =
                ZonedDateTime.now();
        DateTimeBean dateTimeBean = new DateTimeBean();
        dateTimeBean.setZonedDateTime(when);

        ObserverLocationBean observerLocationBean =
                new ObserverLocationBean();
        observerLocationBean.setCoordinates(
                GeographicCoordinates.ofDeg(6.57, 46.52));

        ViewingParametersBean viewingParametersBean =
                new ViewingParametersBean();
        viewingParametersBean.setCenter(
                HorizontalCoordinates.ofDeg(180.000000000001, 15));
        viewingParametersBean.setFieldOfViewDeg(70);

        //Creation of the pane

        BorderPane root=new BorderPane();

        root.setTop(controlBar(observerLocationBean));
        root.setCenter(sky());
        root.setBottom(infoBar());


        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("Rigel");

    }


    /**
     * Compute all the part of the control Bar
     * @return the control Bar (Horizontal Box)-
     */
    private HBox controlBar(ObserverLocationBean location){
        HBox controlBar=new HBox(observerPosition(location), observationTime(), timeAcceleration());

        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }

    private HBox observerPosition(ObserverLocationBean locationBean){

        Label longitude=new Label("Longitude (°) :");
        Label latitude=new Label("Latitude (°) :");
        TextField longitudeTF= latLonTextField(true);


        /*longitudeTF.getTextFormatter().valueProperty().addListener((o,v,n) -> locationBean.setLonDeg();

        locationBean.lonDegProperty()

        locationBean.lonDegProperty().bind(longitudeTF.getTextFormatter().valueProperty());
*/
        TextField latitudeTF= latLonTextField(false);


        HBox observerPosition=new HBox();
        observerPosition.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        return observerPosition;
    }




    private  HBox observationTime(){
        return null;
    }
    private HBox timeAcceleration(){
        return null;
    }




    private Pane sky(){
    return null;
    }

    private BorderPane infoBar(){
        return null;
    }










    /**
     * Compute text field witch only accept correct value for longitude or latitude
     * @param islon true if longitude /false if latitude
     * @return Textfield of the coordinate
     */
    private TextField latLonTextField(boolean islon){

        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00");

        UnaryOperator<TextFormatter.Change> Filter = (change -> {
            try {
                String newText =
                        change.getControlNewText();
                double newDeg =
                        stringConverter.fromString(newText).doubleValue();
                return (islon ? GeographicCoordinates.isValidLonDeg(newDeg) : GeographicCoordinates.isValidLatDeg(newDeg))
                        ? change
                        : null;
            } catch (Exception e) {
                return null;
            }
        });

        TextFormatter<Number> TextFormatter =
                new TextFormatter<>(stringConverter, 0, Filter);

        TextField TextField =
                new TextField();
        TextField.setTextFormatter(TextFormatter);
        return TextField;
    }


}
