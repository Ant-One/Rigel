package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * JavaFX Bean of viewing parameters (Zoom and direction)
 *
 * @author Adrien Rey (313388)
 */
public class ViewingParametersBean {

    private ObjectProperty<Double> fieldOfViewDeg;
    private ObjectProperty<HorizontalCoordinates> center;

    /**
     * Basic constructor of the bean
     */
    public ViewingParametersBean() {
        fieldOfViewDeg = new SimpleObjectProperty<>(null);
        center = new SimpleObjectProperty<>(null);
    }

    /**
     * return the field of view property
     *
     * @return the field of view property
     */
    public ObjectProperty<Double> getFieldOfViewDegProperty() {
        return fieldOfViewDeg;
    }

    /**
     * return the field of view
     *
     * @return the field of view
     */
    public double getFieldOfViewDeg() {
        return fieldOfViewDeg.get();
    }

    /**
     * set the field of view
     *
     * @param fieldOfView the new field of view
     */
    public void setFieldOfViewDeg(double fieldOfView) {
        this.fieldOfViewDeg.set(fieldOfView);
    }

    /**
     * return the center property
     *
     * @return the center property
     */
    public ObjectProperty<HorizontalCoordinates> getCenterProperty() {
        return center;
    }

    /**
     * return the center in horizontal coordinates
     *
     * @return the center in horizontal coordinates
     */
    public HorizontalCoordinates getCenter() {
        return center.get();
    }

    /**
     * set a new center
     *
     * @param center the new center in horizontal coordinates
     */
    public void setCenter(HorizontalCoordinates center) {
        this.center.set(center);
    }


}
