package ch.epfl.rigel.astronomy;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Catalogue of stars
 *
 * @author Antoine Moix (310052)
 */
public final class StarCatalogue {

    private final Map<Asterism, List<Integer>> catalogueMap;
    private final List<Star> stars;

    /**
     * Constructs a Star Catalogue with keys as asterism objects and values as lists of the corresponding star's indexes
     * in the stars list
     *
     * @param stars     list of stars
     * @param asterisms list of asterisms
     * @throws IllegalArgumentException if a star used in an asterism is not in the provided list of stars
     */
    public StarCatalogue(List<Star> stars, List<Asterism> asterisms) {
        this.stars = new ArrayList<>(stars);
        ArrayList<Asterism> workAsterisms = new ArrayList<>(asterisms);

        catalogueMap = new HashMap<>();

        for (Asterism aste : workAsterisms) {
            List<Integer> indexes = new ArrayList<>();

            for (Star star : aste.stars()) {
                int index = stars.indexOf(star);
                if (index >= 0) {
                    indexes.add(index);
                } else {
                    throw new IllegalArgumentException();
                }
            }
            catalogueMap.put(aste, indexes);
        }
    }

    /**
     * Returns the list of stars
     *
     * @return an unmodifiable list of the stars used in the catalogue
     */
    public List<Star> stars() {
        return Collections.unmodifiableList(stars);
    }

    /**
     * Returns a set of all the asterisms used in the catalogue
     *
     * @return Returns an unmodifiable set of all the asterisms used in the catalogue
     */
    public Set<Asterism> asterisms() {
        return Collections.unmodifiableSet(catalogueMap.keySet());
    }

    /**
     * Returns a list of the stars used by a given asterism
     *
     * @param asterism a astersim object
     * @return all the stars used by the given asterism object
     * @throws IllegalArgumentException if the given asterism is not in the catalogue
     */
    public List<Integer> asterismIndices(Asterism asterism) {
        if (catalogueMap.containsKey(asterism)) {
            return Collections.unmodifiableList(catalogueMap.get(asterism));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public final static class Builder {
        final ArrayList<Star> builderStars;
        final ArrayList<Asterism> builderAsterisms;

        /**
         * Construct a builder for the StarCatalogue object
         */
        public Builder() {
            builderStars = new ArrayList<>();
            builderAsterisms = new ArrayList<>();
        }

        /**
         * Add a star to the star list of the builder
         *
         * @param star the star to be added to the list of the builder
         * @return the current builder object
         */
        public Builder addStar(Star star) {
            builderStars.add(star);
            return this;
        }

        /**
         * Returns an unmodifiable view on the list of the stars of the builder
         *
         * @return an unmodifiable view on the list of the stars of the builder
         */
        public List<Star> stars() {
            return Collections.unmodifiableList(builderStars);
        }

        public Builder addAsterism(Asterism asterism) {
            builderAsterisms.add(asterism);
            return this;
        }

        /**
         * Returns an unmodifiable view on the list of the asterisms of the builder
         *
         * @return an unmodifiable view on the list of the asterisms of the builder
         */
        public List<Asterism> asterisms() {
            return Collections.unmodifiableList(builderAsterisms);
        }

        /**
         * Load from an inputStream and a loader object
         *
         * @param inputStream the inputStream from which to load stars or asterisms
         * @param loader      the loader used to load asterisms or stars
         * @return the builder object
         * @throws IOException if an IO exception happens
         */
        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException {
            loader.load(inputStream, this);
            return this;
        }

        /**
         * Returns the built StarCatalogue object
         *
         * @return the built StarCatalogue object
         */
        public StarCatalogue build() {
            return new StarCatalogue(builderStars, builderAsterisms);
        }
    }

    public interface Loader {

        /**
         * Loads stars and/or asterisms from the input stream and add them to the builder's lists
         *
         * @param inputStream the input stream of data for the stars and asterisms
         * @param builder     the builder to whose catalogue add the read data
         * @throws IOException if an error with the streams happens
         */
        void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
