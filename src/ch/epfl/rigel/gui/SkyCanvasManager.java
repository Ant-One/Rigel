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
    private static final ClosedInterval ELEVATION_INTERVAL = ClosedInterval.of(Angle.ofDeg(5), Angle.TAU/4);
    private static final ClosedInterval FIELD_OF_VIEW_INTERVAL = ClosedInterval.of(Angle.ofDeg(30), Angle.ofDeg(150));

    public SkyCanvasManager(StarCatalogue catalogue, DateTimeBean timeBean, ObserverLocationBean locationBean, ViewingParametersBean viewBean) {

        this.catalogue = catalogue;
        this.timeBean = timeBean;
        this.locationBean = locationBean;
        this.viewBean = viewBean;

        canvas = new Canvas(800, 600);
        painter = new SkyCanvasPainter(canvas);

        createBindings();
        createListeners();

        setKeyboardEvents();
        setMouseEvents();
    }


    private void createBindings() {
        projection = Bindings.createObjectBinding(() -> new StereographicProjection(viewBean.getCenter()), viewBean.getCenterProperty());

            dilatationFactor = Bindings.createDoubleBinding(() -> {
                //Quick hack to not compute the dilatation factor if the width is equals to 0
                if(canvas.getWidth() != 0) {
                    return canvas.getWidth() / (projection.get().applyToAngle(Angle.ofDeg(viewBean.getFieldOfViewDeg())));
                }
                return 1300d;
                },
                    viewBean.getFieldOfViewDegProperty(), canvas.widthProperty());

        planeToCanvas = Bindings.createObjectBinding(() -> Transform.affine(dilatationFactor.get(), 0, 0, -dilatationFactor.get(),
                400, 300), dilatationFactor);


        observedSky = Bindings.createObjectBinding(() -> new ObservedSky(timeBean.getZonedDateTime(), locationBean.getCoordinates(), projection.get(), catalogue),
                locationBean.coordinatesBindingsProperty(), timeBean.timeProperty(), timeBean.dateProperty(), projection);

        mouseCartesianPosition = Bindings.createObjectBinding(() -> {
                    Point2D mouseTransformedPosition = planeToCanvas.get().inverseTransform(mousePosition.get().x(), mousePosition.get().y());
                    return CartesianCoordinates.of(mouseTransformedPosition.getX(), mouseTransformedPosition.getY()); },
                mousePosition, planeToCanvas);

       mouseHorizontalPosition = Bindings.createObjectBinding(() -> projection.get().inverseApply(mouseCartesianPosition.get()), mouseCartesianPosition, projection);

        mouseAzDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().azDeg(), mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(() -> mouseHorizontalPosition.get().altDeg(), mouseHorizontalPosition);

        objectUnderMouse = Bindings.createObjectBinding(() -> observedSky.get().objectClosestTo(mouseCartesianPosition.get(), 10), mouseCartesianPosition);

    }

    private void createListeners() {
        observedSky.addListener((o, oV, nV) -> paint());

        planeToCanvas.addListener((o, oV, nV) -> {
            paint();
        });

        canvas.heightProperty().addListener((o, oV, nV) -> paint());
        //A change in the width triggers a recomputation of the dilatation factor and thus a paint()
    }

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

    private void setKeyboardEvents() {
        canvas.setOnKeyPressed((event) -> {
            HorizontalCoordinates currentCenter = viewBean.getCenter();
            double next;
            switch (event.getCode()) {
                case LEFT:
                    next = AZIMUTH_INTERVAL.reduce(Angle.ofDeg(currentCenter.azDeg() - 10));
                    if(next == Angle.TAU){
                        next = 0;
                    }
                    viewBean.setCenter(HorizontalCoordinates.ofDeg(Angle.toDeg(next), currentCenter.altDeg()));
                    event.consume();
                    break;
                case RIGHT:
                    next = AZIMUTH_INTERVAL.reduce(Angle.ofDeg(currentCenter.azDeg() + 10));
                    if(next == Angle.TAU){
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

    private void paint() {
        painter.clear();
        painter.drawStars(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawPlanets(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawMoon(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawSun(observedSky.get(), projection.get(), planeToCanvas.get());
        painter.drawHorizon(observedSky.get(), projection.get(), planeToCanvas.get());
    }

    public ObservableObjectValue<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }

    public Canvas canvas() {
        return canvas;
    }
}
