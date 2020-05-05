package ch.epfl.test.exemple;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.gui.ObserverLocationBean;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;

final class UseCreateBinding {
    public static void main(String[] args) {

        ObserverLocationBean OLB=new ObserverLocationBean();
        OLB.setLatDeg(30);
        OLB.setLonDeg(30);
        System.out.print(OLB.getCoordinates());
        OLB.setLonDeg(50);
        System.out.print(OLB.getCoordinates());




    }
}
