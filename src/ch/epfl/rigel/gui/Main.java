package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.UnaryOperator;


/**
 * Main class of Rigel program
 *
 * @author Adrien Rey (313388), Antoine Moix (310052)
 */
public class Main extends Application {

    private final double INIT_LOCATION_LONG = 6.57;
    private final double INIT_LOCATION_LAT = 46.52;

    private final double INIT_VIEW_AZ = 180.000000000001;
    private final double INIT_VIEW_ALT = 15;
    private final double INIT_VIEW_FIELD = 100;

    private final double MIN_HEIGHT = 600;
    private final double MIN_WIDTH = 800;

    private final double INIT_HEIGHT = 720;
    private final double INIT_WIDTH = 1280;

    /**
     * Main Application
     *
     * @param args provided arguments at launch
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the application and call all functions needed to construct the visual
     */
    @Override
    public void start(Stage stage) {


        //all the Beans needed

        ZonedDateTime when =
                ZonedDateTime.now();
        DateTimeBean dateTimeBean = new DateTimeBean();
        dateTimeBean.setZonedDateTime(when);

        ObserverLocationBean observerLocationBean =
                new ObserverLocationBean();
        observerLocationBean.setCoordinates(
                GeographicCoordinates.ofDeg(INIT_LOCATION_LONG, INIT_LOCATION_LAT));

        ViewingParametersBean viewingParametersBean =
                new ViewingParametersBean();
        viewingParametersBean.setCenter(
                HorizontalCoordinates.ofDeg(INIT_VIEW_AZ, INIT_VIEW_ALT));
        viewingParametersBean.setFieldOfViewDeg(INIT_VIEW_FIELD);

        //Loading the stars and asterisms from files, and creating the catalogue

        StarCatalogue starCatalogue = null;
        try {
            starCatalogue = loadStarsAndAsterims();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Creation of the SkyCanvasManager
        SkyCanvasManager skyManager = new SkyCanvasManager(starCatalogue, dateTimeBean, observerLocationBean, viewingParametersBean);
        Canvas sky = skyManager.canvas();
        Pane skyPane = new Pane(sky);

        //TimeAnimator

        TimeAnimator timeAnimator = new TimeAnimator(dateTimeBean);

        //Creation of the pane

        BorderPane root = new BorderPane();

        root.setTop(controlBar(observerLocationBean, dateTimeBean, timeAnimator, stage, sky));
        root.setCenter(skyPane);
        root.setBottom(infoBar(skyManager, viewingParametersBean));
        root.setRight(checkBoxes(skyManager));


        stage.setMinHeight(MIN_HEIGHT);
        stage.setMinWidth(MIN_WIDTH);
        stage.setTitle("Rigel");

        stage.setHeight(INIT_HEIGHT);
        stage.setWidth(INIT_WIDTH);

        stage.setScene(new Scene(root));
        stage.show();

        sky.widthProperty().bind(skyPane.widthProperty());
        sky.heightProperty().bind(skyPane.heightProperty());
        sky.requestFocus();
    }


    /**
     * loads stars and asterisms for resources
     *
     * @return the StarCatalogue
     * @throws IOException if the data files are not found
     */
    private StarCatalogue loadStarsAndAsterims() throws IOException {
        try (InputStream hygData = resourceStream("/hygdata_v3.csv")) {
            try (InputStream asterismsData = resourceStream("/asterisms.txt")) {

                return new StarCatalogue.Builder()
                        .loadFrom(hygData, HygDatabaseLoader.INSTANCE).loadFrom(asterismsData, AsterismLoader.INSTANCE)
                        .build();
            }
        }
    }

    /**
     * return the Input Stream of a named file
     *
     * @param resourceName the name of the file
     * @return the Input stream
     */
    private InputStream resourceStream(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    /**
     * ( * Computes the controlBar
     *
     * @param location     ObserverLocationBean
     * @param dateTimeBean DateTimeBean
     * @param timeAnimator Time Animator
     * @return the controlBar (Horizontal bar)
     */
    private HBox controlBar(ObserverLocationBean location, DateTimeBean dateTimeBean, TimeAnimator timeAnimator, Stage stage, Canvas sky) {

        String save = "\uf0c7";
        Font fontAwesome = null;
        try (InputStream fontStream = getClass()
                .getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf")) {
            fontAwesome = Font.loadFont(fontStream, 15);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Button saveButton = new Button(save);
        saveButton.setFont(fontAwesome);
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image", "*.png"));
            fileChooser.setInitialFileName("sky.png");
            fileChooser.setInitialDirectory(FileSystemView.getFileSystemView().getDefaultDirectory());
            File savedFile = fileChooser.showSaveDialog(stage);

            WritableImage fxImage =
                    sky.snapshot(null, null);
            BufferedImage swingImage =
                    SwingFXUtils.fromFXImage(fxImage, null);
            try {
                ImageIO.write(swingImage, "png", savedFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });


        HBox controlBar = new HBox(observerPosition(location), observationTime(dateTimeBean, timeAnimator), timeAcceleration(timeAnimator, dateTimeBean), saveButton);

        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }

    /**
     * Compute the part of the controlBar about the observer position
     *
     * @param locationBean ObserverLocationBean
     * @return Hbox of the observer position parameters
     */
    private HBox observerPosition(ObserverLocationBean locationBean) {

        Label longitude = new Label("Longitude (°) :");
        Label latitude = new Label("Latitude (°) :");
        TextField longitudeTF = latLonTextField(true, locationBean);
        TextField latitudeTF = latLonTextField(false, locationBean);


        HBox observerPosition = new HBox(longitude, longitudeTF, latitude, latitudeTF);
        observerPosition.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left;");

        return observerPosition;
    }


    /**
     * Compute the part of the controlBar about the observation time
     *
     * @param dateTimeBean dateTimeBean
     * @param timeAnimator TimeAnimator
     * @return Hbox of the time parameters
     */
    private HBox observationTime(DateTimeBean dateTimeBean, TimeAnimator timeAnimator) {
        Label date = new Label("Date :");
        Label heure = new Label("Heure :");

        //Date
        DatePicker datePicker = new DatePicker();
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
        TextField heureTF = new TextField();
        timeFormatter.setValue(LocalTime.now());
        heureTF.setTextFormatter(timeFormatter);


        heureTF.disableProperty().bind(timeAnimator.runningProperty());
        dateTimeBean.timeProperty().bindBidirectional(timeFormatter.valueProperty());

        //ZONE
        ComboBox<ZoneId> zoneIdComboBox = new ComboBox<>();
        ArrayList<String> zoneIds = new ArrayList<>(ZoneId.getAvailableZoneIds());
        Collections.sort(zoneIds);
        ArrayList<ZoneId> zoneID2 = new ArrayList<>();
        for (String s : zoneIds) {
            zoneID2.add(ZoneId.of(s));
        }
        zoneIdComboBox.getItems().addAll(zoneID2);
        zoneIdComboBox.setValue(ZoneId.systemDefault());
        zoneIdComboBox.disableProperty().bind(timeAnimator.runningProperty());

        dateTimeBean.zoneProperty().bindBidirectional(zoneIdComboBox.valueProperty());
        HBox observationTime = new HBox(date, datePicker, heure, heureTF, zoneIdComboBox);
        observationTime.setStyle("-fx-spacing: inherit; -fx-alignment: baseline-left");


        return observationTime;
    }

    /**
     * Compute the part of the control bar about the time Accelerator
     *
     * @param timeAnimator TimeAnimator
     * @param dateTimeBean DateTimeBean
     * @return the Time Animator parameters (HBox)
     */
    private HBox timeAcceleration(TimeAnimator timeAnimator, DateTimeBean dateTimeBean) {

        ChoiceBox<NamedTimeAccelerator> timeAcceleratorChoiceBox = new ChoiceBox<>();
        List<NamedTimeAccelerator> acceleratorList = Arrays.asList(NamedTimeAccelerator.values());
        timeAcceleratorChoiceBox.setItems(FXCollections.observableList(acceleratorList));

        timeAcceleratorChoiceBox.valueProperty().addListener((o, v, n) -> timeAnimator.setAccelerator(NamedTimeAccelerator.ofString(n.getName()).getAccelerator()));
        timeAcceleratorChoiceBox.setValue(NamedTimeAccelerator.TIMES_1);

        timeAcceleratorChoiceBox.disableProperty().bind(timeAnimator.runningProperty());


        Font fontAwesome = null;
        try (InputStream fontStream = getClass()
                .getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf")) {
            fontAwesome = Font.loadFont(fontStream, 15);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String back = "\uf0e2", play = "\uf04b", pause = "\uf04c";


        Button resetButton = new Button(back);
        resetButton.setFont(fontAwesome);
        resetButton.setOnAction(e -> {
            dateTimeBean.setTime(LocalTime.now());
            dateTimeBean.setDate(LocalDate.now());
        });
        resetButton.disableProperty().bind(timeAnimator.runningProperty());

        ToggleButton playButton = new ToggleButton(play);
        playButton.setFont(fontAwesome);
        playButton.setOnAction((e) -> {

            playButton.setText(playButton.getText().equals(play) ? pause : play);
            if (timeAnimator.isRunning()) {
                timeAnimator.stop();
            } else {
                timeAnimator.start();
            }

        });


        HBox timeAcceleration = new HBox(timeAcceleratorChoiceBox, resetButton, playButton);
        timeAcceleration.setStyle("-fx-spacing: inherit;\n");
        return timeAcceleration;
    }


    /**
     * Creates the checkboxes for drawing or not the different elements of the canvas
     * @return a VBox object containing the checkboxes
     */
    private VBox checkBoxes(SkyCanvasManager skymanager) {
        CheckBox checkStars = new CheckBox("Afficher les étoiles");
        CheckBox checkPlanets = new CheckBox("Afficher les planètes");
        CheckBox checkMoon = new CheckBox("Afficher la Lune");
        CheckBox checkSun = new CheckBox("Afficher le Soleil");
        CheckBox checkHorizon = new CheckBox("Afficher l'horizon");

        checkStars.setSelected(true);
        checkPlanets.setSelected(true);
        checkMoon.setSelected(true);
        checkSun.setSelected(true);
        checkHorizon.setSelected(true);

        checkStars.setStyle("-fx-padding: 5");
        checkPlanets.setStyle("-fx-padding: 5");
        checkMoon.setStyle("-fx-padding: 5");
        checkSun.setStyle("-fx-padding: 5");
        checkHorizon.setStyle("-fx-padding: 5");

        checkStars.selectedProperty().addListener((o, oV, nV) -> skymanager.setDrawStars(checkStars.isSelected()));
        checkPlanets.selectedProperty().addListener((o, oV, nV) -> skymanager.setDrawPlanets(checkPlanets.isSelected()));
        checkMoon.selectedProperty().addListener((o, oV, nV) -> skymanager.setDrawMoon(checkMoon.isSelected()));
        checkSun.selectedProperty().addListener((o, oV, nV) -> skymanager.setDrawSun(checkSun.isSelected()));
        checkHorizon.selectedProperty().addListener((o, oV, nV) -> skymanager.setDrawHorizon(checkHorizon.isSelected()));

        VBox checkBoxes = new VBox(checkStars, checkPlanets, checkMoon, checkSun, checkHorizon);
        checkBoxes.setStyle("-fx-padding: 10");

        return checkBoxes;
    }


    /**
     * Method used to create the infoBar at the bottom containing the FoV settings, the closest object under the mouse
     * and the coordinates of the mouse pointer
     *
     * @param skyCanvasManager      the SkyCanvasManager object
     * @param viewingParametersBean the ViewingParameterBean containing the viewing settings
     * @return a BorderPane object containing the data aforementioned
     */
    private BorderPane infoBar(SkyCanvasManager skyCanvasManager, ViewingParametersBean viewingParametersBean) {
        BorderPane infoPane = new BorderPane();
        infoPane.setStyle("-fx-padding: 4; -fx-background-color: white;");

        Text fovText = new Text("Champ de vue : " + viewingParametersBean.getFieldOfViewDeg() + "°");
        Text closestObjectText = new Text();
        Text azAltText = new Text();

        fovText.textProperty().bind(Bindings.format("Champ de vue : %.1f°", viewingParametersBean.getFieldOfViewDegProperty()));

        skyCanvasManager.objectUnderMouseProperty().addListener((o, oV, nV) -> {
            if (nV != null) {
                closestObjectText.setText(nV.info());
            } else {
                closestObjectText.setText("");
            }
        });

        azAltText.textProperty().bind(Bindings.format("Azimuth : %.1f°, hauteur : %.1f°", skyCanvasManager.mouseAzDeg, skyCanvasManager.mouseAltDeg));

        infoPane.setLeft(fovText);
        infoPane.setCenter(closestObjectText);
        infoPane.setRight(azAltText);

        return infoPane;
    }


    /**
     * Compute text field witch only accept correct value for longitude or latitude
     *
     * @param islon true if longitude /false if latitude
     * @return Textfield of the coordinate
     */
    private TextField latLonTextField(boolean islon, ObserverLocationBean location) {

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

        if (islon) {
            TextFormatter.valueProperty().bindBidirectional(location.lonDegProperty());
            TextFormatter.setValue(INIT_LOCATION_LONG);
        } else {
            TextFormatter.valueProperty().bindBidirectional(location.latDegProperty());
            TextFormatter.setValue(INIT_LOCATION_LAT);
        }
        TextField TextField =
                new TextField();
        TextField.setTextFormatter(TextFormatter);
        return TextField;
    }


}
