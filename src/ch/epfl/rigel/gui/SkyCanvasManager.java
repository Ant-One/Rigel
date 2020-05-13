package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableDoubleValue;
import javafx.beans.value.ObservableObjectValue;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Transform;

/**
 * Used to manage the Canvas on which the sky is drew.
 * Monitors the changes in the simulation parameters (time, location, speed of simulation, ...)
 *
 * @author Antoine Moix (310052)
 */
public class SkyCanvasManager {

    public ObservableDoubleValue mouseAzDeg;
    public ObservableDoubleValue mouseAltDeg;
    public ObservableObjectValue<CelestialObject> objectUnderMouse;

    private Canvas canvas;
    private SkyCanvasPainter painter;

    private ObservableDoubleValue dilatationFactor;
    private ObservableObjectValue<StereographicProjection> projection;
    private ObservableObjectValue<Transform> planeToCanvas;
    private ObservableObjectValue<ObservedSky> observedSky;
    private ObjectProperty<CartesianCoordinates> mousePosition = new SimpleObjectProperty<>(CartesianCoordinates.of(1, 5));
    private ObservableObjectValue<HorizontalCoordinates> mouseHorizontalPosition;
    private ObservableObjectValue<CartesianCoordinates> mouseCartesianPosition;

    private StarCatalogue catalogue;
    private DateTimeBean timeBean;
    private ObserverLocationBean locationBean;
    private ViewingParametersBean viewBean;

    private static final RightOpenInterval AZIMUTH_INTERVAL = RightOpenInterval.of(0, Angle.TAU);
    private static final ClosedInterval ELEVATION_INTERVAL = ClosedInterval.of(Angle.ofDeg(5), Angle.TAU / 4);
    private static final ClosedInterval FIELD_OF_VIEW_INTERVAL = ClosedInterval.of(Angle.ofDeg(30), Angle.ofDeg(150));

    /**
     * Constructs an object that creates and manages a canvas on which the simulation is done.
     * Monitors the changes in the simulation parameters (time, location, speed of simulation, ...)
     *
     * @param catalogue    the stars and asterisms catalogue used
     * @param timeBean     a bean modelling a date and time to be used on the simulation
     * @param locationBean a bean modelling the location of the observer during the simulation
     * @param viewBean     a bean containing the parameters for the simulation (field of view and center of projection)
     */
    public SkyCanvasManager(StarCatalogue catalogue, DateTimeBean timeBean, ObserverLocationBean locationBean, ViewingParametersBean viewBean) {

        this.catalogue = catalogue;
        this.timeBean = timeBean;
        this.locationBean = locationBean;
        this.viewBean = viewBean;

        canvas = new Canvas(800, 600);
        painter = new SkyCanvasPainter(canvas);

        //Methods to tidy up the code a bit
        createBindings();
        createListeners();

        setKeyboardEvents();
        setMouseEvents();
    }

    /**
     * Used to create the required bindings between the beans and this class' attributes
     */
    private void createBindings() {
        projection = Bindings.createObjectBinding(() -> new StereographicProjection(viewBean.getCenter()), viewBean.getCenterProperty());

        dilatationFactor = Bindings.createDoubleBinding(() -> {
                    //Quick hack to not compute the dilatation factor if the width is equals to 0
                    if (canvas.getWidth() != 0) {
                        return canvas.getWidth() / (projection.get().applyToAngle(Angle.ofDeg(viewBean.getFieldOfViewDeg())));
                    }
                    return 1300d;
                },
                viewBean.getFieldOfViewDegProperty(), canvas.widthProperty());

        planeToCanvas = Bindings.createObjectBinding(() -> Transform.affine(dilatationFactor.get(), 0, 0, -dilatationFactor.get(),
                canvas.getWidth() / 2, canvas.getHeight() / 2), dilatationFactor, canvas.widthProperty(), canvas.heightProperty());


        observedSky = Bindings.createObjectBinding(() -> new ObservedSky(timeBean.getZonedDateTime(), locationBean.getCoordinates(), projection.get(), catalogue),
                locationBean.coordinatesBindingsProperty(), timeBean.timeProperty(), timeBean.dateProperty(), timeBean.zoneProperty(), projection);

        mouseCartesianPosition = Bindings.createObjectBinding(() -> {
                    Point2D mouseTransformedPosition = planeToCanvas.get().inverseTransform(mousePosition.get().x(), mousePosition.get().y());
                    return CartesianCoordinates.of(mouseTransformedPosition.getX(), mouseTransformedPosition.getY());
                },
                mousePosition, planeToCanvas);

        mouseHorizontalPosition = Bindings.createObjectBinding(() -> projection.get().inverseApply(mouseCartesianPosition.get()), mouseCartesianPosition, projection);

        mouseAzDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);

        objectUnderMouse = Bindings.createObjectBinding(() -> observedSky.get().objectClosestTo(mouseCartesianPosition.get(), 0.01), mouseCartesianPosition);

    }

    /**
     * Used to create the needed listeners for any change that requires a redraw of the canvas
     */
    private void createListeners() {
        observedSky.addListener((o, oV, nV) -> paint());

        planeToCanvas.addListener((o, oV, nV) -> paint());

        canvas.heightProperty().addListener((o, oV, nV) -> paint());
        //A change in the width triggers a recomputation of the dilatation factor and thus a paint()
    }

    /**
     * Adds mouse listeners and events to the canvas
     */
    private void setMouseEvents() {
        canvas.setOnMousePressed((event) -> {
            if (event.isPrimaryButtonDown()) {
                canvas.requestFocus();
            }
        });

        canvas.setOnScroll((event) -> {
            double currentFoV = viewBean.getFieldOfViewDeg();

            double newFoV = Angle.ofDeg(Math.abs(event.getDeltaX()) > Math.abs(event.getDeltaY())
                    ? event.getDeltaX() + currentFoV
                    : event.getDeltaY() + currentFoV);
            viewBean.setFieldOfViewDeg(Angle.toDeg(FIELD_OF_VIEW_INTERVAL.clip(newFoV)));
        });


        canvas.setOnMouseMoved((event) -> mousePosition.set(CartesianCoordinates.of(event.getX(), event.getY())));
    }

    /**
     * Adds keyboard listeners and event to the canvas
     */
    private void setKeyboardEvents() {
        canvas.setOnKeyPressed((event) -> {
            HorizontalCoordinates currentCenter = viewBean.getCenter();
            double next;
            switch (event.getCode()) {
                case LEFT:
                    next = AZIMUTH_INTERVAL.reduce(Angle.ofDeg(currentCenter.azDeg() - 10));
                    if (next == Angle.TAU) {
                        next = 0;
                    }
                    viewBean.setCenter(HorizontalCoordinates.ofDeg(Angle.toDeg(next), currentCenter.altDeg()));
                    event.consume();
                    break;
                case RIGHT:
                    next = AZIMUTH_INTERVAL.reduce(Angle.ofDeg(currentCenter.azDeg() + 10));
                    if (next == Angle.TAU) {
                        next = 0;
                    }
                    viewBean.setCenter(HorizontalCoordinates.ofDeg(Angle.toDeg(next), currentCenter.altDeg()));
                    event.consume();
                    break;
                case UP:
                    next = ELEVATION_INTERVAL.clip(Angle.ofDeg(currentCenter.altDeg() + 5));
                    viewBean.setCenter(HorizontalCoordinates.ofDeg(currentCenter.azDeg(), Angle.toDeg(next)));
                    event.consume();
                    break;
                case DOWN:
                    next = ELEVATION_INTERVAL.clip(Angle.ofDeg(currentCenter.altDeg() - 5));
                    viewBean.setCenter(HorizontalCoordinates.ofDeg(currentCenter.azDeg(), Angle.toDeg(next)));
                    event.consume();
                    break;
            }
        });
    }

    /**
     * Draws on the canvas
     */
    private void paint() {
        painter.clear();
        painter.drawStars(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawPlanets(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawMoon(observedSky.get(), planeToCanvas.get());
        painter.drawSun(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawHorizon(projection.get(), planeToCanvas.get());
    }

    /**
     * Returns the instance of CelestialObject closest to the the mouse cursor
     *
     * @return the instance of CelestialObject closest to the the mouse cursor
     */
    public ObservableObjectValue<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    /**
     * Returns the canvas used
     *
     * @return the canvas used
     */
    public Canvas canvas() {
        return canvas;
    }
}
