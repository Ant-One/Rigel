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

    private List<Star> stars;

    public Asterism(List<Star> stars) {
        Preconditions.checkArgument(!stars.isEmpty());
        this.stars.addAll(stars);
    }
    //TODO check if imuable


    public List<Star> stars() {
        List<Star> list = new ArrayList<>(stars);
        return list;
    }
}
