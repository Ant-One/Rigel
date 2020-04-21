package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

@FunctionalInterface
public interface TimeAccelerator {
    /** abstract fonction of an accelerator
     * @param T0 Initial time
     * @param deltaTime spent time since the last tick
     * @return the adjusted time
     */
    ZonedDateTime adjust(ZonedDateTime T0, long deltaTime);

    /**
     * Create a continuous accelerator
     * @param alpha the acceleration factor
     * @return the accelerator
     */
    static TimeAccelerator continuous(int alpha){
        return ((T0, deltaTime) -> T0.plus(alpha*deltaTime, ChronoUnit.NANOS));
    }

    /**
     * Create a discrete accelerator
     * @param v the advancement frequencies
     * @param S the step
     * @return the accelerator
     */
    static  TimeAccelerator discrete(int v, Duration S){
        return((T0,deltaTime) -> T0.plus((long)Math.floor(v*deltaTime)*S.toNanos(),ChronoUnit.NANOS));
    }

}
