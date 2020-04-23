package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TimeAcceleratorTest {



    @Test
    void continiousWork(){

        TimeAccelerator accelerator=TimeAccelerator.continuous(300);

        ZonedDateTime date=accelerator.adjust(ZonedDateTime.parse("2020-04-20T21:00:00+01:00"),(long)2.34e+9);

        System.out.print(date);

    }

    @Test
    void discreteWork(){
        TimeAccelerator accelerator=TimeAccelerator.discrete(10, Duration.ofSeconds(23*3600+56*60+4));

        ZonedDateTime date=accelerator.adjust(ZonedDateTime.parse("2020-04-20T21:00:00+01:00"),(long)2.34e+9);

        System.out.print(date);
    }

}