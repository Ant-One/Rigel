package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Asterism (Group of stars)
 * @author Adrien Rey (313388)
 */

public class Asterism {

    private List<Star> stars = new ArrayList<>();

    public Asterism(List<Star> stars) {
        Preconditions.checkArgument(!stars.isEmpty());
        this.stars.addAll(stars);
    }
    //TODO Comment

    public List<Star> stars() {
        return new ArrayList<>(stars);
    }
}
