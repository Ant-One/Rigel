package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.util.ArrayList;
import java.util.List;

/**
 * Asterism (Group of stars)
 *
 * @author Adrien Rey (313388)
 */

public class Asterism {

    private final List<Star> stars;

    /**
     * Construct an asterism with a list of stars
     *
     * @param stars the list of stars to be included in the asterism
     * @throws IllegalArgumentException if the list is empty
     */
    public Asterism(List<Star> stars) {
        Preconditions.checkArgument(!stars.isEmpty());
        this.stars=List.copyOf(stars);
    }

    /**
     * Returns the list of stars in the asterism
     *
     * @return the list of stars of the asterism
     */
    public List<Star> stars() {
        return new ArrayList<>(stars);
    }
}
