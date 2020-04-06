package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ObservedSkyTest {

    @Test
    void stars() {

        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        List<Star> stars = os.stars();

        assertEquals(star1.info(), stars.get(0).info());
        assertEquals(star2.info(), stars.get(1).info());
    }

    @Test
    void starsPosition() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        List<Double> stars = os.starsPosition();

        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(zdt, gc);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(zdt);
        double sinceJ2010 = Epoch.J2010.daysUntil(zdt);

        List<Double> starsPosition = new ArrayList<>();

        CartesianCoordinates coord = sp.apply(ETH.apply(star1.equatorialPos()));
        starsPosition.add(coord.x());
        starsPosition.add(coord.y());
        CartesianCoordinates coord2 = sp.apply(ETH.apply(star2.equatorialPos()));
        starsPosition.add(coord2.x());
        starsPosition.add(coord2.y());

        assertEquals(starsPosition.get(0), stars.get(0));
        assertEquals(starsPosition.get(1), stars.get(1));
        assertEquals(starsPosition.get(2), stars.get(2));
        assertEquals(starsPosition.get(3), stars.get(3));
    }

    @Test
    void sun() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(zdt, gc);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(zdt);
        double sinceJ2010 = Epoch.J2010.daysUntil(zdt);


        Sun sun = SunModel.SUN.at(sinceJ2010, ETE);
        CartesianCoordinates sunPosition = sp.apply(ETH.apply(ETE.apply(sun.eclipticPos())));

        assertEquals(sun.info(), os.sun().info());
    }

    @Test
    void sunPosition() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(zdt, gc);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(zdt);
        double sinceJ2010 = Epoch.J2010.daysUntil(zdt);


        Sun sun = SunModel.SUN.at(sinceJ2010, ETE);
        CartesianCoordinates sunPosition = sp.apply(ETH.apply(ETE.apply(sun.eclipticPos())));

        assertEquals(sunPosition.x(), os.sunPosition().x());
        assertEquals(sunPosition.y(), os.sunPosition().y());
    }

    @Test
    void moon() {

        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(zdt, gc);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(zdt);
        double sinceJ2010 = Epoch.J2010.daysUntil(zdt);


        Moon moon = MoonModel.MOON.at(sinceJ2010, ETE);

        assertEquals(moon.info(), os.moon().info());
    }

    @Test
    void moonPosition() {

        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(zdt, gc);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(zdt);
        double sinceJ2010 = Epoch.J2010.daysUntil(zdt);

        Moon moon = MoonModel.MOON.at(sinceJ2010, ETE);
        CartesianCoordinates moonPosition = sp.apply(ETH.apply(moon.equatorialPos()));

        assertEquals(moonPosition.x(), os.moonPosition().x());
        assertEquals(moonPosition.y(), os.moonPosition().y());
    }

    @Test
    void planets() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(zdt, gc);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(zdt);
        double sinceJ2010 = Epoch.J2010.daysUntil(zdt);

        ArrayList<Planet> planets = new ArrayList<>();

        for (PlanetModel planetModel : PlanetModel.ALL) {

            if (planetModel.ordinal() != PlanetModel.EARTH.ordinal()) {

                Planet planet = planetModel.at(sinceJ2010, ETE);
                planets.add(planet);
            }
        }

        for (int i = 0; i < planets.size(); i++) {
            assertEquals(planets.get(i).info(), os.planets().get(i).info());
        }
    }

    @Test
    void planetsPosition() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(zdt, gc);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(zdt);
        double sinceJ2010 = Epoch.J2010.daysUntil(zdt);

        ArrayList<Double> planetsPosition = new ArrayList<>();

        for (PlanetModel planetModel : PlanetModel.ALL) {

            if (planetModel.ordinal() != PlanetModel.EARTH.ordinal()) {

                Planet planet = planetModel.at(sinceJ2010, ETE);
                CartesianCoordinates coord = sp.apply(ETH.apply(planet.equatorialPos()));
                planetsPosition.add(coord.x());
                planetsPosition.add(coord.y());
            }

            for (int i = 0; i < planetsPosition.size(); i++) {
                assertEquals(planetsPosition.get(i), os.planetsPosition().get(i));
            }
        }
    }

    @Test
    void asterisms() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        List<Star> starsList = new ArrayList<>();

        builder.addStar(star1);
        builder.addStar(star2);

        Asterism asterism = new Asterism(List.of(star1));

        builder.addAsterism(asterism);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        Set<Asterism> asterisms = os.asterisms();

        assertTrue(asterisms.contains(asterism));
    }

    @Test
    void asterismIndices() {
        ZonedDateTime zdt = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.MIDNIGHT,
                ZoneOffset.UTC);

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12, 30);
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(4, 1.4));

        StarCatalogue.Builder builder = new StarCatalogue.Builder();
        Star star1 = new Star(1, "1", EquatorialCoordinates.of(1, 0.5), 1f, 1f);
        Star star2 = new Star(2, "2", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);
        Star star3 = new Star(3, "3", EquatorialCoordinates.of(0.6, 0.12), 3f, 2f);

        builder.addStar(star1);
        builder.addStar(star2);
        builder.addStar(star3);

        Asterism asterism = new Asterism(List.of(star1, star3));

        builder.addAsterism(asterism);

        StarCatalogue sc = builder.build();

        ObservedSky os = new ObservedSky(zdt, gc, sp, sc);

        List<Integer> asterismsIndices = os.asterismIndices(asterism);

        assertEquals(0, asterismsIndices.get(0));
        assertEquals(2, asterismsIndices.get(1));

        assertThrows(IllegalArgumentException.class, () -> {
            os.asterismIndices(new Asterism(List.of(star2)));
        });

    }

    @Test
    void objectClosestTo() throws IOException { //Volés sur le telegram
        String HYG_CATALOGUE_NAME = "/hygdata_v3.csv";
        String AST_CATALOGUE_NAME = "/asterisms.txt";
        StarCatalogue catalogue;
        ObservedSky sky;
        StereographicProjection stereo;
        GeographicCoordinates geoCoords;
        ZonedDateTime time;
        EquatorialToHorizontalConversion convEquToHor;
        EclipticToEquatorialConversion convEcltoEqu;
        StarCatalogue.Builder builder;
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
            builder = new StarCatalogue.Builder().loadFrom(hygStream, HygDatabaseLoader.INSTANCE);
        }
        try (InputStream astStream = getClass().getResourceAsStream(AST_CATALOGUE_NAME)) {
            catalogue = builder.loadFrom(astStream, AsterismLoader.INSTANCE).build();
        }
        time = ZonedDateTime.of(LocalDate.of(2020, Month.APRIL, 4), LocalTime.of(0, 0), ZoneOffset.UTC);
        geoCoords = GeographicCoordinates.ofDeg(30, 45);
        stereo = new StereographicProjection(HorizontalCoordinates.ofDeg(20, 22));
        convEquToHor = new EquatorialToHorizontalConversion(time, geoCoords);
        convEcltoEqu = new EclipticToEquatorialConversion(time);
        sky = new ObservedSky(time, geoCoords, stereo, catalogue);

        assertEquals("Tau Phe", sky.objectClosestTo(stereo.apply(new EquatorialToHorizontalConversion(time, geoCoords).apply(EquatorialCoordinates.of(0.004696959812148989, -0.861893035343076))), 0.1).info());


       assertNull(sky.objectClosestTo(stereo.apply(new EquatorialToHorizontalConversion(time, geoCoords).apply(EquatorialCoordinates.of(0.004696959812148989, -0.8618930353430763))), 0.001));
    }
}