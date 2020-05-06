package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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

        //TimeAnimator

        TimeAnimator timeAnimator=new TimeAnimator(dateTimeBean);

        //Creation of the pane

        BorderPane root=new BorderPane();

        root.setTop(controlBar(observerLocationBean,dateTimeBean,timeAnimator));
        root.setCenter(sky());
        root.setBottom(infoBar());


        stage.setMinHeight(600);
        stage.setMinWidth(800);
        stage.setTitle("Rigel");

        stage.setScene(new Scene(root));
        stage.show();



    }


    /**
     * Compute all the part of the control Bar
     * @return the control Bar (Horizontal Box)-
     */
    private HBox controlBar(ObserverLocationBean location,DateTimeBean dateTimeBean,TimeAnimator timeAnimator){
        HBox controlBar=new HBox(observerPosition(location), observationTime(dateTimeBean,timeAnimator), timeAcceleration(timeAnimator,dateTimeBean));

        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }

    private HBox observerPosition(ObserverLocationBean locationBean){

        Label longitude=new Label("Longitude (°) :");
        Label latitude=new Label("Latitude (°) :");
        TextField longitudeTF= latLonTextField(true, locationBean);
        TextField latitudeTF= latLonTextField(false, locationBean);


        HBox observerPosition=new HBox(longitude,longitudeTF,latitude,latitudeTF);
        observerPosition.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");
        return observerPosition;
    }




    private  HBox observationTime(DateTimeBean dateTimeBean,TimeAnimator timeAnimator){
        Label date=new Label("Date :");
        Label heure=new Label("Heure :");

        //Date
        DatePicker datePicker=new DatePicker();
        datePicker.setStyle("-fx-pref-width: 120;");
        datePicker.setValue(LocalDate.now());
        dateTimeBean.dateProperty().bindBidirectional(datePicker.valueProperty());
        datePicker.disableProperty().bind(timeAnimator.runningProperty());

        //Heure
        DateTimeFormatter hmsFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeStringConverter stringConverter =
                new LocalTimeStringConverter(hmsFormatter, hmsFormatter);
        TextFormatter<LocalTime> timeFormatter =
                new TextFormatter<>(stringConverter);
        TextField heureTF= new TextField();
        timeFormatter.setValue(LocalTime.now());
        heureTF.setTextFormatter(timeFormatter);

        timeAnimator.runningProperty().addListener((v,o,n)-> System.out.print(n));

        heureTF.disableProperty().bind(timeAnimator.runningProperty());
        dateTimeBean.timeProperty().bindBidirectional(timeFormatter.valueProperty());
        //ZONE
        ComboBox<ZoneId> zoneIdComboBox=new ComboBox<>();
        ArrayList<String> zoneIds =new ArrayList<>(ZoneId.getAvailableZoneIds());
        Collections.sort(zoneIds);
        ArrayList<ZoneId> zoneID2=new ArrayList<>();
        for (String s: zoneIds) { zoneID2.add(ZoneId.of(s));
        }
        zoneIdComboBox.getItems().addAll(zoneID2);
        zoneIdComboBox.setValue(ZoneId.systemDefault());

        dateTimeBean.zoneProperty().bindBidirectional(zoneIdComboBox.valueProperty());
        HBox observationTime=new HBox(date,datePicker,heure,heureTF,zoneIdComboBox);
        observationTime.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left");


        return observationTime;
    }
    private HBox timeAcceleration(TimeAnimator timeAnimator, DateTimeBean dateTimeBean){

        ChoiceBox<NamedTimeAccelerator> timeAcceleratorChoiceBox=new ChoiceBox<>();
        List<NamedTimeAccelerator> acceleratorList =Arrays.asList(NamedTimeAccelerator.values());
        timeAcceleratorChoiceBox.setItems(FXCollections.observableList(acceleratorList));
        timeAcceleratorChoiceBox.setValue(NamedTimeAccelerator.TIMES_1);

        ObjectProperty<String> timeAcceleratorBinding= new SimpleObjectProperty<>();
        timeAcceleratorBinding.addListener((v,o,n)->timeAnimator.setAccelerator(NamedTimeAccelerator.ofString(n).getAccelerator()));
        timeAcceleratorBinding.bind(Bindings.select(timeAcceleratorChoiceBox.valueProperty(),"name"));

        Font fontAwesome = null;
        try (InputStream fontStream = getClass()
                .getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf")) {
                fontAwesome = Font.loadFont(fontStream, 15);
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        

        Button resetButton = new Button("\uf0e2");
        resetButton.setFont(fontAwesome);
        resetButton.setOnAction(e -> {
            dateTimeBean.setTime(LocalTime.now());
            dateTimeBean.setDate(LocalDate.now());
        });

        ToggleButton playButton =new ToggleButton("\uf04b");
        playButton.setFont(fontAwesome);
        playButton.setOnAction(e -> {

            playButton.setText(playButton.getText().equals("\uf04b") ? "\uf04c" : "\uf04b");
            if (timeAnimator.isRunning()) {
                timeAnimator.stop();
            }
            else{
                timeAnimator.start();
            }

        });

        timeAnimator.runningProperty().addListener((v,o,n)-> System.out.print(n));


        HBox timeAcceleration=new HBox(timeAcceleratorChoiceBox,resetButton,playButton);
        timeAcceleration.setStyle("-fx-spacing: inherit;\n");
        return timeAcceleration;
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
    private TextField latLonTextField(boolean islon,ObserverLocationBean location){

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


        if(islon) {
            ObservableObjectValue<Double> lonBindings = Bindings.createObjectBinding(() -> TextFormatter.valueProperty().getValue().doubleValue(), TextFormatter.valueProperty());
            lonBindings.addListener((o, oV, nV) -> location.setLonDeg(nV));
            TextFormatter.setValue(6.57);
        }
        else{
            ObservableObjectValue<Double> latBindings = Bindings.createObjectBinding(() -> TextFormatter.valueProperty().getValue().doubleValue(), TextFormatter.valueProperty());
            latBindings.addListener((o, oV, nV) -> location.setLatDeg(nV));
            TextFormatter.setValue(46.52);
        }
        TextField TextField =
                new TextField();
        TextField.setTextFormatter(TextFormatter);
        return TextField;
    }


}
