package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * JavaFX bean modelling a ZonedDateTime
 *
 * @author Antoine Moix (310052)
 */
public final class DateTimeBean {

    private ObjectProperty<LocalDate> date;
    private ObjectProperty<LocalTime> time;
    private ObjectProperty<ZoneId> zone;

    /**
     * Basic constructor of the bean
     */
    public DateTimeBean() {
        date = new SimpleObjectProperty<>(null);
        time = new SimpleObjectProperty<>(null);
        zone = new SimpleObjectProperty<>(null);
    }

    /**
     * returns the date property
     *
     * @return the date property
     */
    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    /**
     * returns the date value
     *
     * @return the date value
     */
    public LocalDate getDate() {
        return date.get();
    }

    /**
     * Sets the value of date according to the LocalDate object in parameter
     *
     * @param date the date object to which the value of date property will be set
     */
    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    /**
     * returns the time property
     *
     * @return the time property
     */
    public ObjectProperty<LocalTime> timeProperty() {
        return time;
    }

    /**
     * returns the time value
     *
     * @return the time value
     */
    public LocalTime getTime() {
        return time.get();
    }

    /**
     * Sets the value of time according to the LocalTime object in parameter
     *
     * @param time the time object to which the value of date property will be set
     */
    public void setTime(LocalTime time) {
        this.time.set(time);
    }

    /**
     * returns the zone property
     *
     * @return the zone property
     */
    public ObjectProperty<ZoneId> zoneProperty() {
        return zone;
    }

    /**
     * returns the zone value
     *
     * @return the zone value
     */
    public ZoneId getZone() {
        return zone.get();
    }

    /**
     * Sets the value of zone according to the ZoneId object in parameter
     *
     * @param zone the time object to which the value of date property will be set
     */
    public void setZone(ZoneId zone) {
        this.zone.set(zone);
    }

    /**
     * gets the value of the bean in the form of a ZonedDateTime object
     *
     * @return the value of the bean in the form of a ZonedDateTime object
     */
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.of(getDate(), getTime(), getZone());
    }

    /**
     * Sets the value of the bean according to a ZonedDateTime object passed in argument
     *
     * @param zdt a ZonedDateTime object to which set the bean's value to
     */
    public void setZonedDateTime(ZonedDateTime zdt) {
        setDate(zdt.toLocalDate());
        setTime(zdt.toLocalTime());
        setZone(zdt.getZone());
    }
}
