package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.time.ZonedDateTime;

public final class DrawSky extends Application {
    public static void main(String[] args) { launch(args); }

    private InputStream resourceStream(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        StarCatalogue.Builder builder;
        StarCatalogue catalogue;

        try (InputStream hygStream = getClass().getResourceAsStream("/hygdata_v3.csv")) {
            builder = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE);

            try (InputStream astStream = getClass().getResourceAsStream("/asterisms.txt")) {
                catalogue = builder.loadFrom(astStream, AsterismLoader.INSTANCE).build();

                for (int i = 0; i < 16; i++) {


                    ZonedDateTime when =
                            ZonedDateTime.parse("2020-02-17T12:15:00+01:00");
                    GeographicCoordinates where =
                            GeographicCoordinates.ofDeg(6.57, 46.52);
                    HorizontalCoordinates projCenter =
                            HorizontalCoordinates.ofDeg(0, -90 + 11.25 * i);
                    StereographicProjection projection =
                            new StereographicProjection(projCenter);
                    ObservedSky sky =
                            new ObservedSky(when, where, projection, catalogue);

                    Canvas canvas =
                            new Canvas(2000, 2000);
                    Transform planeToCanvas =
                            Transform.affine(1300, 0, 0, -1300, 1000, 1000);
                    SkyCanvasPainter painter =
                            new SkyCanvasPainter(canvas);

                    painter.clear();
                    painter.drawStars(sky, projection, planeToCanvas);
                    painter.drawPlanets(sky, projection, planeToCanvas);

                    painter.drawSun(sky, projection, planeToCanvas);
                    //painter.drawMoon(sky, projection, planeToCanvas);

                    painter.drawHorizon(projection, planeToCanvas);

                    WritableImage fxImage =
                            canvas.snapshot(null, null);
                    BufferedImage swingImage =
                            SwingFXUtils.fromFXImage(fxImage, null);
                    ImageIO.write(swingImage, "png", new File("sky" + i + ".png"));
                }
            }
        }
        Platform.exit();
    }
}