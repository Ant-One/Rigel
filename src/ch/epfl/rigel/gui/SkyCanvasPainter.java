package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Transform;

/**
 * Class used to draw the sky and the objects on it
 *
 * @author Antoine Moix (310052) and Adrien Rey (313388)
 */
public class SkyCanvasPainter {

    private final Canvas canvas;
    private final GraphicsContext ctx;
    private final ClosedInterval magnitudeInterval = ClosedInterval.of(-2, 5);

    /**
     * Basic constructor for the painter
     *
     * @param canvas the canvas
     */
    public SkyCanvasPainter(Canvas canvas) {
        this.canvas = canvas;
        ctx = canvas.getGraphicsContext2D();
    }

    /**
     * Cleans the canvas
     */
    void clear() {
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    /**
     * Draws all the stars with appropriate colors on the canvas
     *
     * @param sky           the observed sky
     * @param projection    the stereographic projection
     * @param planeToCanvas transformation from stereographic to canvas
     */
    void drawStars(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {

        //TODO TransformDelta renvoie toujours des positifs pour x ? à vérifier car cela pourrait rendre Math.abs() superflu

        double[] coord = new double[sky.starsPosition().length];
        planeToCanvas.transform2DPoints(sky.starsPosition(), 0, coord, 0, sky.starsPosition().length / 2);

        ctx.setStroke(Color.BLUE);
        for (Asterism asterism : sky.asterisms()) {

            ctx.beginPath();
            ctx.setLineWidth(1);
            int firstStarIndices = sky.asterismIndices(asterism).get(0);
            ctx.moveTo(coord[2 * firstStarIndices], coord[2 * firstStarIndices + 1]);
            boolean isLastIn = canvas.getBoundsInLocal().contains(coord[2 * firstStarIndices], coord[2 * firstStarIndices + 1]);
            for (int i : sky.asterismIndices(asterism)) {

                boolean isActualIn = canvas.getBoundsInLocal().contains(coord[2 * i], coord[2 * i + 1]);
                if (isActualIn || isLastIn) {
                    ctx.lineTo(coord[2 * i], coord[2 * i + 1]);
                } else {
                    ctx.moveTo(coord[2 * i], coord[2 * i + 1]);
                }
                isLastIn = isActualIn;

            }
            ctx.stroke();


        }

        for (int i = 0; i < sky.starsPosition().length / 2; i++) {

            Point2D size = size(sky.stars().get(i).magnitude(), planeToCanvas, projection);
            double diameter = Math.abs(size.getX());

            ctx.setFill(BlackBodyColor.colorForTemperature(sky.stars().get(i).colorTemperature()));

            ctx.fillOval(coord[2 * i] - size.getX() / 2, coord[2 * i + 1] - size.getX() / 2, diameter, diameter);
        }
    }

    /**
     * Draws all the planets in gray on the canvas
     *
     * @param sky           the observed sky
     * @param projection    the stereographic projection
     * @param planeToCanvas transformation from stereographic to canvas
     */
    void drawPlanets(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {

        double[] coord = new double[sky.planetsPosition().length];
        planeToCanvas.transform2DPoints(sky.planetsPosition(), 0, coord, 0, sky.planetsPosition().length / 2);

        for (int i = 0; i < sky.planetsPosition().length / 2; i++) {

            ctx.setFill(Color.LIGHTGRAY);
            Point2D size = size(sky.planets().get(i).magnitude(), planeToCanvas, projection);
            double diameter = Math.abs(size.getX());
            ctx.fillOval(coord[2 * i] - diameter / 2, coord[2 * i + 1] - diameter / 2, diameter, diameter);

        }
    }

    /**
     * Draws the Moon
     *
     * @param sky           the observed sky
     * @param projection    the stereographic projection used
     * @param planeToCanvas transformation from stereographic plane to the plane used in the canvas
     */
    void drawMoon(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
        Point2D moonCenter = planeToCanvas.transform(sky.moonPosition().x(), sky.moonPosition().y());

        double cartesianDiameter = sky.moon().angularSize();
        Point2D diameterPoint = planeToCanvas.deltaTransform(cartesianDiameter, cartesianDiameter);
        double diameter = Math.abs(diameterPoint.getX());

        ctx.setFill(Color.WHITE);
        ctx.fillOval(moonCenter.getX() - diameter / 2, moonCenter.getY() - diameter / 2, diameter, diameter);
    }

    /**
     * Draws the Sun
     *
     * @param sky           the observed sky
     * @param projection    the stereographic projection used
     * @param planeToCanvas transformation from stereographic plane to the plane used in the canvas
     */
    void drawSun(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
        Point2D sunCenter = planeToCanvas.transform(sky.sunPosition().x(), sky.sunPosition().y());

        double cartesianDiameter = projection.applyToAngle(Angle.ofDeg(0.5));
        Point2D diameterPoint = planeToCanvas.deltaTransform(cartesianDiameter, cartesianDiameter);
        double diameter = Math.abs(diameterPoint.getX());

        ctx.setFill(Color.YELLOW.deriveColor(0, 1, 1, 0.25));
        ctx.fillOval(sunCenter.getX() - (diameter * 2.2 / 2), sunCenter.getY() - (diameter * 2.2 / 2), diameter * 2.2, diameter * 2.2);

        ctx.setFill(Color.YELLOW);
        ctx.fillOval(sunCenter.getX() - ((diameter + 2) / 2), sunCenter.getY() - ((diameter + 2) / 2), diameter + 2, diameter + 2);

        ctx.setFill(Color.WHITE);
        ctx.fillOval(sunCenter.getX() - diameter / 2, sunCenter.getY() - diameter / 2, diameter, diameter);

    }

    /**
     * Draws the horizon line and the octant names in French
     * @param sky           the observed sky
     * @param projection    the stereographic projection used
     * @param planeToCanvas transformation from stereographic plane to the plane used in the canvas
     */
    void drawHorizon(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas) {
        CartesianCoordinates horizonCartesian = projection.circleCenterForParallel(HorizontalCoordinates.of(1, 0));
        Point2D horizonCenter = planeToCanvas.transform(horizonCartesian.x(), horizonCartesian.y());

        double horizonRadiusCartesian = projection.circleRadiusForParallel(HorizontalCoordinates.of(1, 0));
        double horizonRadius = Math.abs(planeToCanvas.deltaTransform(horizonRadiusCartesian, horizonRadiusCartesian).getX());


        ctx.setStroke(Color.RED);
        ctx.setLineWidth(2);
        if(horizonRadius==Double.POSITIVE_INFINITY || horizonRadius==Double.NEGATIVE_INFINITY){
            ctx.strokeLine(0,canvas.getHeight()/2,canvas.getWidth(),canvas.getHeight()/2);
        }
        else {
            ctx.strokeOval(horizonCenter.getX() - horizonRadius, horizonCenter.getY() - horizonRadius, horizonRadius * 2, horizonRadius * 2);
        }
        ctx.setFill(Color.RED);
        ctx.setTextAlign(TextAlignment.CENTER);
        ctx.setTextBaseline(VPos.TOP);

        for (int i = 0; i < 8; i++) {
            HorizontalCoordinates annotationCoordinates = HorizontalCoordinates.ofDeg(45 * i, -0.5);

            String octantName = annotationCoordinates.azOctantName("N", "E", "S", "O");

            CartesianCoordinates annotationCartesian = projection.apply(annotationCoordinates);

            Point2D annotationCenter = planeToCanvas.transform(annotationCartesian.x(), annotationCartesian.y());
            ctx.fillText(octantName, annotationCenter.getX(), annotationCenter.getY());
        }
    }

    /**
     * Computes the size of object with 0 as angular size
     *
     * @param magnitude     magnitude of the objects
     * @param planeToCanvas the transform form the stereographicProjection to canvas
     * @return the size to draw
     */
    private Point2D size(double magnitude, Transform planeToCanvas, StereographicProjection projection) {
        double m = magnitudeInterval.clip(magnitude);
        double f = (99. - 17. * m) / 140.;
        double d = f * projection.applyToAngle(Angle.ofDeg(0.5));
        return planeToCanvas.deltaTransform(d, d);
    }

}

