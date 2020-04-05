package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * The observed sky at a moment
 *
 * @author Adrien Rey (313388)
 */
public class ObservedSky {

    private final StarCatalogue catalogue;
    private final HashMap<CelestialObject, CartesianCoordinates> objects = new HashMap<>();

    private final List<Star> stars;
    private final List<Double> starsPosition = new ArrayList<>();

    private final Sun sun;
    private final CartesianCoordinates sunPosition;

    private final Moon moon;
    private final CartesianCoordinates moonPosition;

    private final List<Planet> planets = new ArrayList<>();
    private final List<Double> planetsPosition = new ArrayList<>();


    /**
     * Constructor of the observed sky
     *
     * @param when       ZonedDateTime when the sky is observed
     * @param where      GeographicCoordinates where the sky is observed
     * @param projection proper Stereographic Projection
     * @param catalogue  catalogue of stars
     */
    public ObservedSky(ZonedDateTime when, GeographicCoordinates where, StereographicProjection projection, StarCatalogue catalogue) {


        this.catalogue = catalogue;

        //Tools
        EquatorialToHorizontalConversion ETH = new EquatorialToHorizontalConversion(when, where);
        EclipticToEquatorialConversion ETE = new EclipticToEquatorialConversion(when);
        double sinceJ2010 = Epoch.J2010.daysUntil(when);


        //Construct Star
        stars = new ArrayList<>(catalogue.stars());

        for (Star s : catalogue.stars()) {

            CartesianCoordinates coord = projection.apply(ETH.apply(s.equatorialPos()));
            starsPosition.add(coord.x());
            starsPosition.add(coord.y());

            objects.put(s, coord);
        }

        //Construct Sun

        sun = SunModel.SUN.at(sinceJ2010, ETE);
        sunPosition = projection.apply(ETH.apply(ETE.apply(sun.eclipticPos())));
        objects.put(sun, sunPosition);

        //Construct Moon

        moon = MoonModel.MOON.at(sinceJ2010, ETE);
        moonPosition = projection.apply(ETH.apply(moon.equatorialPos()));
        objects.put(moon, moonPosition);

        //Construct Planet

        for (PlanetModel planetModel : PlanetModel.ALL) {

            if (planetModel.ordinal() != PlanetModel.EARTH.ordinal()) {

                Planet planet = planetModel.at(sinceJ2010, ETE);
                planets.add(planet);
                CartesianCoordinates coord = projection.apply(ETH.apply(planet.equatorialPos()));
                planetsPosition.add(coord.x());
                planetsPosition.add(coord.y());
                objects.put(planet, coord);

            }
        }

    }

    /**
     * The list of the stars
     *
     * @return the list of the stars
     */
    public List<Star> stars() {
        return List.copyOf(stars);
    }

    /**
     * Stars position
     *
     * @return double list with x,y cartesian coordinates
     */
    public List<Double> starsPosition() {
        return List.copyOf(starsPosition);
    }

    /**
     * Sun
     *
     * @return the sun
     */
    public Sun sun() {
        return sun;
    }

    /**
     * Sun position
     *
     * @return sun cartesian coordinates
     */
    public CartesianCoordinates sunPosition() {
        return sunPosition;
    }

    /**
     * Moon
     *
     * @return the moon
     */
    public Moon moon() {
        return moon;
    }

    /**
     * Moon position
     *
     * @return moon cartesian coordinates
     */
    public CartesianCoordinates moonPosition() {
        return moonPosition;
    }

    /**
     * 7 Planets
     *
     * @return List of the seven Planets
     */
    public List<Planet> planets() {
        return List.copyOf(planets);
    }

    /**
     * Planets position
     *
     * @return double list with x,y cartesian coordinates
     */
    public List<Double> planetsPosition() {
        return List.copyOf(planetsPosition);
    }

    /**
     * Returns a set of all the asterisms used in the catalogue
     *
     * @return Returns a set of all the asterisms used in the catalogue
     */
    public Set<Asterism> asterisms() {
        return catalogue.asterisms();
    }

    /**
     * Returns a list of the stars used by a given asterism
     *
     * @param asterism a astersim object
     * @return all the stars used by the given asterism object
     * @throws IllegalArgumentException if the given asterism is not in the catalogue
     */
    public List<Integer> asterismIndices(Asterism asterism) {
        return catalogue.asterismIndices(asterism);
    }

    /**
     * Compute the closest object to a point under a maximum distance
     * @param point the point
     * @param maxDistance maximum distance of the object to the point
     * @return the closest object or null if not an object was not found
     */
    public CelestialObject objectClosestTo(CartesianCoordinates point, double maxDistance) {
        CelestialObject object = null;
        double actualDistance = Double.MAX_VALUE;

        for (CartesianCoordinates coord : objects.values()) {
            double distance = Math.hypot(coord.x() - point.x(), coord.y() - point.y());

            if (distance < maxDistance) {
                if (object == null || distance < actualDistance) {
                    actualDistance = distance;
                    //object = objectsReversed.get(coord);
                }
            }
        }

        return object;
    }

}