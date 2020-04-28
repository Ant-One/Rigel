package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;

/**
 * JavaFX Bean of the location of the observer
 * @author Adrien Rey (313388)
 */
public class ObserverLocationBean {

    private ObjectProperty<Double> lonDeg=null;
    private  ObjectProperty<Double> latDeg=null;;
    private ObjectProperty<GeographicCoordinates> coordinates=null;
    private ObservableObjectValue<GeographicCoordinates> coordinatesBindings;

    /**
     * Basic constructor of the bean
     */
    public ObserverLocationBean() {
        lonDeg=new SimpleObjectProperty<>();
        latDeg=new SimpleObjectProperty<>();
        coordinates=new SimpleObjectProperty<>();
        coordinatesBindings = Bindings.createObjectBinding(
                () ->GeographicCoordinates.ofDeg(lonDeg.get(),latDeg.get()),
                lonDeg,latDeg);
        coordinatesBindings.addListener(o -> coordinates.set(coordinatesBindings.get()));
    }

    /**
     * return the longitude property
     * @return the longitude property
     */
    public ObjectProperty<Double> lonDegProperty(){return lonDeg;}

    /**
     * return the longitude in degree
     * @return the longitude in degree
     */
    public double getLonDeg(){return lonDeg.get();}

    /**
     * change the longitude
     * @param lonDeg the new longitude in degree
     */
    public void setLonDeg(double lonDeg){this.lonDeg.set(lonDeg); }


    /**
     * return the latitude property
     * @return the latitude property
     */
    public ObjectProperty<Double> latDegProperty(){return latDeg;}
    /**
     * return the latitude in degree
     * @return the latitude in degree
     */
    public double getLatDeg(){return latDeg.get();}
    /**
     * change the latitude
     * @param latDeg the new latitude in degree
     */
    public void setLatDeg(double latDeg){this.latDeg.set(latDeg); }

    /**
     * return the geographic coordinates property
     * @return the geographic coordinates property
     */
    public ObjectProperty<GeographicCoordinates> coordinatesBindingsProperty(){return coordinates;}
    /**
     * return the geographic coordinates
     * @return the geographic coordinates
     */
    public GeographicCoordinates getCoordinates(){return coordinates.get();}

    /**
     * change the geographic coordinates
     * @param coordinates the new geographic coordinates
     */
    public void setCoordinates(GeographicCoordinates coordinates){this.coordinates.set(coordinates);}








}
