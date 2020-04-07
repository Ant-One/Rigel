package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;

public class SkyCanvasPainter {

    private final Canvas canvas;
    private final GraphicsContext ctx;


    public SkyCanvasPainter(Canvas canvas) {
        this.canvas = canvas;
        ctx= canvas.getGraphicsContext2D();
    }


    void clear(){
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0,0,canvas.getWidth(),canvas.getHeight());
    }

    void drawPlanets(ObservedSky sky, StereographicProjection projection, Transform planeToCanvas){

        planeToCanvas.transform(sky.sunPosition().x(),sky.sunPosition().y());

    }

}

