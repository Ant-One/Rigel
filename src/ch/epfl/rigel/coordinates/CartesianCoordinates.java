package ch.epfl.rigel.coordinates;

public final class CartesianCoordinates {

    private final double x,y;

    /**
     * private constructor of Cartesian coordinates
     * @param x x coordinate
     * @param y y coordinate
     */
    private CartesianCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Construct Cartesian coordinates
     * @param x x value
     * @param y y value
     * @return the constructed cartesian coordinates
     */
    static public CartesianCoordinates of(double x,double y){
        return new CartesianCoordinates(x,y);
    }

    /**
     * @return the x coordinates
     */
    public double x() {
        return x;
    }

    /**
     * @return the y coordinates
     */
    public double y() {
        return y;
    }

    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * @return the representation (x ; y)
     */

    @Override
    public String toString() {
        return "("+x +" ; "+y+")";
    }
    /**
     * @throws UnsupportedOperationException when used
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }


}
