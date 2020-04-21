package ch.epfl.rigel.gui;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@FunctionalInterface
public interface TimeAccelerator {

    ZonedDateTime adjust(ZonedDateTime T0, long deltaTime);

    static TimeAccelerator continuous(ZonedDateTime T0, long deltaTime, int alpha){
        return ((T01, deltaTime1) -> T01.plus(alpha*deltaTime, ChronoUnit.NANOS));
    }


}
