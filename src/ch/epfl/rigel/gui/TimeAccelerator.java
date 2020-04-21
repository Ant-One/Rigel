package ch.epfl.rigel.gui;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@FunctionalInterface
public interface TimeAccelerator {

    ZonedDateTime adjust(ZonedDateTime T0, long deltaTime);

    static TimeAccelerator continuous(int alpha){
        return ((T0, deltaTime) -> T0.plus(alpha*deltaTime, ChronoUnit.NANOS));
    }

    static  TimeAccelerator discrete(){
        return null;
    }


}
