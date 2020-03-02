package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

public abstract class CelestialObject {

    private final String name;
    private final EquatorialCoordinates equatorialPos;
    private final float angularSize, magnitude;

    CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude){

        //TODO Tests avec angularSize négative et name, equatorialPos non-définis
        Preconditions.checkArgument(angularSize >= 0);
        if(name == null || equatorialPos == null){
            throw new NullPointerException();
        }

        this.name = name;
        this.equatorialPos = equatorialPos;
        this.angularSize = angularSize;
        this.magnitude = magnitude;
    }

    public String name(){
        return name;
    }

    public double angularSize(){
        return angularSize;
    }

    public double magnitude(){
        return magnitude;
    }

    public EquatorialCoordinates equatorialPos(){
        return equatorialPos;
    }
}
