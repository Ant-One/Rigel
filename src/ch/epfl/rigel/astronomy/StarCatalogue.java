package ch.epfl.rigel.astronomy;

import java.io.IOException;
import java.io.InputStream;
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
    public StarCatalogue(List<Star> stars, List<Asterism> asterisms){
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
    public List<Star> stars(){
        return stars;
    }

    /**
     * Returns a set of all the asterisms used in the catalogue
     * @return Returns a set of all the asterisms used in the catalogue
     */
    public Set<Asterism> asterisms(){
        return catalogueMap.keySet();
    }

    /**
     * Returns a list of the stars used by a given asterism
     * @param asterism a astersim object
     * @return all the stars used by the given asterism object
     * @throws IllegalArgumentException if the given asterism is not in the catalogue
     */
    public List<Integer> asterismIndices(Asterism asterism){
        if (catalogueMap.containsKey(asterism)) {
            return catalogueMap.get(asterism);
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    public final static class Builder{
//TODO COMMENTER
        ArrayList<Star> builderStars;
        ArrayList<Asterism> builderAsterisms;

        public Builder(){
            builderStars = new ArrayList<>();
            builderAsterisms = new ArrayList<>();
        }

        public Builder addStar(Star star){
            builderStars.add(star);
            return this;
        }

        public List<Star> stars(){
            return Collections.unmodifiableList(builderStars);
        }

        public Builder addAsterism(Asterism asterism){
            builderAsterisms.add(asterism);
            return this;
        }

        public List<Asterism> asterisms(){
            return Collections.unmodifiableList(builderAsterisms);
        }

        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException {
            loader.load(inputStream, this);
            return this;
        }

        public StarCatalogue build(){
            return new StarCatalogue(builderStars, builderAsterisms);
        }
    }

    public interface Loader{

        /**
         * Loads stars and/or asterisms from the input stream and add them to the builder's lists
         * @param inputStream the input stream of data for the stars and asterisms
         * @param builder the builder to whose catalogue add the read data
         * @throws IOException if an error with the streams happens
         */
        void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
