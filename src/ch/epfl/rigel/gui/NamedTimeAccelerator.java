package ch.epfl.rigel.gui;

import java.time.Duration;
import java.util.HashMap;
import java.util.prefs.Preferences;

/**
 * Models a named time accelerator
 * @author Adrien Rey (313388)
 */
public enum NamedTimeAccelerator {
    TIMES_1("1x",TimeAccelerator.continuous(1)),
    TIMES_30("30x",TimeAccelerator.continuous(30)),
    TIMES_300("300x",TimeAccelerator.continuous(300)),
    TIMES_3000("3000x",TimeAccelerator.continuous(3000)),
    DAY("jour",TimeAccelerator.discrete(60, Duration.ofHours(24))),
    SIDEREAL_DAY("jour sidéral",TimeAccelerator.discrete(60,Duration.ofSeconds((23*60+56)*60+4)));


    private final String name;
    private final TimeAccelerator accelerator;

    /**
     * Construct a Named Accelerator
     * @param name the name
     * @param accelerator the accelerator
     */
    NamedTimeAccelerator(String name, TimeAccelerator accelerator) {
        this.name = name;
        this.accelerator = accelerator;

    }



    /**
     * the name
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * the TimeAccelerator
     * @return the accelerator
     */
    public TimeAccelerator getAccelerator() {
        return accelerator;
    }

    static public NamedTimeAccelerator ofString(String s){
        NamedTimeAccelerator acc=null;
        for (NamedTimeAccelerator accelerator: NamedTimeAccelerator.values()) {
            if(s.equals(accelerator.name)){
                acc=accelerator;
            }
        }
        return acc;
    }

    /**
     * Method toString
     * @return the name of the accelerator
     */
    @Override
    public String toString() {
        return name;
    }
}
