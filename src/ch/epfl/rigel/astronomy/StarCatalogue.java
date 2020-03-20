package ch.epfl.rigel.astronomy;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Catalogue of stars
 *
 * @author Antoine Moix (310052)
 */
public final class StarCatalogue {

    private Map<Asterism, List<Integer>> catalogueMap;
    private List<Star> stars;

    /**
     * Construct a Star Catalogue with keys as asterism objects and values as lists of the corresponding star's indexes
     * in the stars list
     * @param stars list of stars
     * @param asterisms list of asterisms
     * @throws IllegalArgumentException if a star used in an asterism is not in the provided list of stars
     */
    StarCatalogue(List<Star> stars, List<Asterism> asterisms){
        this.stars = new ArrayList<>(stars);
        ArrayList<Asterism> workAsterisms = new ArrayList<>(asterisms);

        catalogueMap = new HashMap<>();

        for (Asterism aste : workAsterisms) {
            List<Integer> indexes = new ArrayList<>();

            for(Star star : aste.stars()){
                int index = stars.indexOf(star);
                if(index >= 0){
                    indexes.add(index);
                }
                else{
                    throw new IllegalArgumentException();
                }
            }
            catalogueMap.put(aste, indexes);
        }
    }

    /**
     * Returns the list of stars
     * @return the list of the stars used in the catalogue
     */
    List<Star> stars(){
        return stars;
    }

    /**
     * Returns a set of all the asterisms used in the catalogue
     * @return Returns a set of all the asterisms used in the catalogue
     */
    Set<Asterism> asterisms(){
        return catalogueMap.keySet();
    }

    /**
     * Returns a list of the stars used by a given asterism
     * @param asterism a astersim object
     * @return all the stars used by the given asterism object
     * @throws IllegalArgumentException if the given asterism is not in the catalogue
     */
    List<Integer> asterismIndices(Asterism asterism){
        if (catalogueMap.containsKey(asterism)) {
            return catalogueMap.get(asterism);
        }
        else{
            throw new IllegalArgumentException();
        }
    }
}
