package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

/**
 * Polynomial equation
 *
 * @author Adrien Rey (313388)
 */
public final class Polynomial {
    private final double[] polynomial;

    private Polynomial(double[] polynomial) {
        this.polynomial = polynomial;
    }

    /**
     * Construct a Polynomial
     *
     * @param coefficientN coefficient with higher degree
     * @param coefficients other coefficient
     * @return the constructed Polynomial
     */
    public static Polynomial of(double coefficientN, double... coefficients) {

        Preconditions.checkArgument(coefficientN != 0);

        double[] polynomial = new double[1 + coefficients.length];
        polynomial[0] = coefficientN;
        System.arraycopy(coefficients, 0, polynomial, 1, coefficients.length);
        return new Polynomial(polynomial);
    }

    /**
     * compute the polynomial equation at x
     *
     * @param x value to compute
     * @return the result of the polynomial compute at x
     */
    public double at(double x) {
        double horner = polynomial[0];
        for (int i = 1; i < polynomial.length; i++) {
            horner = horner * x + polynomial[i];
        }
        return horner;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder(polynomial.length * 3);

        for (int i = 0; i < polynomial.length - 1; i++) {
            if (polynomial[i] != 0) {
                if (Math.abs(polynomial[i]) != 1) {
                    str.append(polynomial[i]);
                } else if (polynomial[i] == -1) {
                    str.append('-');
                }
                str.append('x');

                if (polynomial.length - i > 2) {
                    str.append('^');
                    str.append(polynomial.length - i - 1);
                }
                if (polynomial[i + 1] > 0) {
                    str.append('+');
                }
            }
        }
        str.append(polynomial[polynomial.length - 1]);
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
