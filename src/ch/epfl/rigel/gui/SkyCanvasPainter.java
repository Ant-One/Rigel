package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.Asterism;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;


public class SkyCanvasPainter {

    private final Canvas canvas;
    private final GraphicsContext ctx;
    private final ClosedInterval magnitudeInterval =ClosedInterval.of(-2,5);

    /**
     * Basic constructor for the painter
     * @param canvas the canvas
     */
    public SkyCanvasPainter(Canvas canvas) {
        this.canvas = canvas;
        ctx= canvas.getGraphicsContext2D();
    }

    /**
     * Clean the canvas
     */
    void clear(){
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }



    /**
     * Draw all the stars with appropriate colors on the canvas
     * @param sky the observed sky
     * @param projection the stereographic projection
     * @param planeToCanvas transformation from stereographic to canvas
     */
    void drawStars(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){

        double[] coord= new double[sky.starsPosition().length];
        planeToCanvas.transform2DPoints(sky.starsPosition(),0,coord,0,sky.starsPosition().length/2);

        ctx.setFill(Color.BLUE);
        for (Asterism asterism : sky.asterisms()) {

            ctx.beginPath();
            ctx.setLineWidth(1);
            int firstStarIndice=sky.asterismIndices(asterism).get(0);
            ctx.moveTo(coord[2*firstStarIndice],coord[2*firstStarIndice+1]);
            boolean isLastIn=canvas.getBoundsInLocal().contains(coord[2*firstStarIndice],coord[2*firstStarIndice+1]);

            for (int i : sky.asterismIndices(asterism)){

                boolean isActualIn=canvas.getBoundsInLocal().contains(coord[2*i],coord[2*i+1]);

                if(isActualIn || isLastIn) {
                    ctx.lineTo(coord[2 * i], coord[2 * i + 1]);
                }
                ctx.moveTo(coord[2*i],coord[2*i+1]);
                isLastIn=isActualIn;

            }

            ctx.stroke();

        }

        for (int i = 0; i <sky.starsPosition().length/2 ; i++) {



            Point2D size=size(sky.stars().get(i).magnitude(),planeToCanvas);

            ctx.setFill(BlackBodyColor.colorForTemperature(sky.stars().get(i).colorTemperature()));

            ctx.fillOval(coord[2*i]-size.getX()/2,coord[2*i+1]-size.getX()/2,size.getX(),size.getX());
        }


    }

    /**
     * Draw all the planets in gray on the canvas
     * @param sky the observed sky
     * @param projection the stereographic projection
     * @param planeToCanvas transformation from stereographic to canvas
     */
    void drawPlanets(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){

        double[] coord= new double[sky.planetsPosition().length];
        planeToCanvas.transform2DPoints(sky.planetsPosition(),0,coord,0,sky.planetsPosition().length/2);

        for (int i = 0; i <sky.planetsPosition().length/2 ; i++) {

            ctx.setFill(Color.LIGHTGRAY);
            Point2D size=size(sky.planets().get(i).magnitude(),planeToCanvas);
            ctx.fillOval(coord[2*i],coord[2*i+1],size.getX(),size.getY());

        }
    }

    /**
     * Compute size of object with 0 as angular size
     * @param magnitude magnitude of the objects
     * @param planeToCanvas the transform form the stereographicProjection to canvas
     * @return the size to draw
     */
    private Point2D size(double magnitude, Transform planeToCanvas){
        double m=magnitudeInterval.clip(magnitude);
        double f=(99.-17.*m)/140.;
        double d=f*2*Math.tan(Angle.ofDeg(0.5/4));
        return planeToCanvas.deltaTransform(d,d);
    }




}

