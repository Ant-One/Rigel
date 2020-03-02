package ch.epfl.rigel.coordinates;

import java.util.function.Function;

public class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {

    private final HorizontalCoordinates center;

    public StereographicProjection(HorizontalCoordinates center) {
        this.center = center;
    }
}
