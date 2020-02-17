package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Arrays;

public final class Polynomial {
    private final double[] polynom;

    private Polynomial(double[] polynom) {
        this.polynom = polynom;
    }

    /**
     * Construct a Polynomial
     * @param coefficientN coefficient with higher degree
     * @param coefficients other coefficient
     * @return the constructed Polynomial
     */
    public static Polynomial of(double coefficientN, double... coefficients){

        Preconditions.checkArgument(coefficientN!=0);

        double[] polynom =new double[1+coefficients.length];
        polynom[0]=coefficientN;
        System.arraycopy(coefficients, 0, polynom, 1, coefficients.length);
        return new Polynomial(polynom);
    }

    /**
     * compute the polynomial equation at x
     * @param x value to compute
     * @return the result of the polynomial compute at x
     */
    public double at(double x){
        double horner=polynom[0];
        for (int i = 1; i <polynom.length ; i++) {
            horner=horner*x+polynom[i];
        }
        return horner;
    }

    @Override
    public String toString() {
        StringBuilder str=new StringBuilder(polynom.length*3);
        for (int i = 0; i <polynom.length ; i++) {
            str.append(polynom[0]);
            str.append(", ");
        }
        return str.toString();
    }

    @Override
    final public int hashCode() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }

}
