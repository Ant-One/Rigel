package ch.epfl.rigel.coordinates;

import java.util.function.Function;

public class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {

    private final HorizontalCoordinates center;

    public StereographicProjection(HorizontalCoordinates center) {
        this.center = center;
    }

    public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor){

        return CartesianCoordinates.of(0,0);








    }

    double circleRadiusForParallel(HorizontalCoordinates parallel){
        return 0;
    }

    double applyToAngle(double rad){
        return 0;
    }

    public CartesianCoordinates apply(HorizontalCoordinates azAlt){
        return null;
    }


    public HorizontalCoordinates inverseApply(CartesianCoordinates xy){
        return null;
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







}
