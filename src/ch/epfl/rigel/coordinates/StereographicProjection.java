package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

import java.util.function.Function;

/**
 * Class used to create Stereographic projection of a horizontal coordinates
 *
 * @author Adrien Rey (313388)
 */
public class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {

    private final HorizontalCoordinates center;
    private final double sinPhi, cosPhi;

    /**
     * Basic constructor of a StereographicProjection
     *
     * @param center center of the StereoGraphicalProjection
     */
    public StereographicProjection(HorizontalCoordinates center) {
        this.center = center;
        sinPhi = Math.sin(center.alt());
        cosPhi = Math.cos(center.alt());

    }

    /**
     * Compute the center of the Projected parallel forming a circle
     *
     * @param hor One of the parallel coordinates
     * @return the center of the circle
     */
    public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor) {

        return CartesianCoordinates.of(0, cosPhi / (Math.sin(hor.alt()) + sinPhi));
    }

    /**
     * Compute the radius of the projected parallel forming a circle
     *
     * @param parallel One of the parallel coordinates
     * @return the radius of the circle
     */
    public double circleRadiusForParallel(HorizontalCoordinates parallel) {
        return (Math.cos(parallel.alt())) / (Math.sin(parallel.alt()) + sinPhi);
    }


    /**
     * Compute the diameter of an object projected on the stereographic
     *
     * @param rad angular size of the object
     * @return the diameter of the projected object
     */
    public double applyToAngle(double rad) {
        return 2 * Math.tan(rad / 4.0);
    }


    /**
     * Compute the Projection of the given point on the Stereographic Coordinates
     *
     * @param azAlt the point to project
     * @return the projection of the point
     */
    public CartesianCoordinates apply(HorizontalCoordinates azAlt) {
        double sinOtherPhi = Math.sin(azAlt.alt());
        double cosOtherPhi = Math.cos(azAlt.alt());
        double lambda = azAlt.az() - center.az();
        double sinLambda = Math.sin(lambda);
        double cosLambda = Math.cos(lambda);

        double d = 1 / (1 + (sinPhi * sinOtherPhi) + (cosOtherPhi * cosPhi * cosLambda));

        double x = d * cosOtherPhi * sinLambda;
        double y = d * (sinOtherPhi * cosPhi - cosOtherPhi * sinPhi * cosLambda);

        return CartesianCoordinates.of(x, y);
    }

    /**
     * Compute the projected point which xy is the image
     *
     * @param xy the projection on the Stereographic
     * @return the original point
     */
    public HorizontalCoordinates inverseApply(CartesianCoordinates xy) {

        double rho = Math.sqrt(xy.x() * xy.x() + xy.y() * xy.y());
        double sinC = 2 * rho / (rho * rho + 1);
        double cosC = (1 - rho * rho) / (rho * rho + 1);
        boolean origin= xy.y()==0 && xy.x()==0;

        double lambda =origin ? center.az() : Math.atan2(xy.x() * sinC, rho * cosPhi * cosC - xy.y() * sinPhi * sinC) + center.az();
        double phi =origin ? center.alt() : Math.asin(cosC * sinPhi + ((xy.y() * sinC * cosPhi) / rho));

        return HorizontalCoordinates.of(Angle.normalizePositive(lambda), phi);
    }

    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "StereographicProjection{" +
                "center of " + center +
                '}';
    }
}
