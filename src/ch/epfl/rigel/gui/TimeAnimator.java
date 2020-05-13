package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;

import java.time.ZonedDateTime;

/**
 * Class modelling a time animator using a time accelerator. Is used to compute the time with a factor of acceleration
 *
 * @author Antoine Moix (310052)
 */
public final class TimeAnimator extends AnimationTimer {

    private ObjectProperty<TimeAccelerator> accelerator;
    private BooleanProperty running;

    private ZonedDateTime initialStart;
    private long nanosSinceLaunch;
    private DateTimeBean dtb;

    private boolean isInitialRun;

    /**
     * Constructor of the TimeAnimator object
     *
     * @param dtb the moment from which the time will be accelerated
     */
    public TimeAnimator(DateTimeBean dtb) {
        this.dtb = dtb;
        accelerator = new SimpleObjectProperty<>(null);
        running = new SimpleBooleanProperty(false);
    }

    /**
     * Override of AnimationTimer.start()
     */
    @Override
    public void start() {
        setRunning(true);
        isInitialRun = true;
        initialStart = dtb.getZonedDateTime();
        super.start();
    }

    /**
     * Override of AnimationTimer.stop()
     */
    @Override
    public void stop() {
        setRunning(false);
        super.stop();
    }

    /**
     * Override of AnimationTimer.handle()
     * Computes the accelerated time
     *
     * @param l time in nanoseconds of the moment the method is called
     */
    @Override
    public void handle(long l) {
        if (isInitialRun) {
            isInitialRun = false;
            nanosSinceLaunch = l;
        }

        dtb.setZonedDateTime(getAccelerator().adjust(initialStart, l - nanosSinceLaunch));
    }

    /**
     * Returns the accelerator property
     *
     * @return the accelerator property
     */
    public ObjectProperty<TimeAccelerator> acceleratorProperty() {
        return accelerator;
    }

    /**
     * Returns the accelerator value
     *
     * @return the accelerator value
     */
    public TimeAccelerator getAccelerator() {
        return accelerator.get();
    }

    /**
     * Sets the accelerator property value
     *
     * @param accelerator the value to which the accelerator property value is to be set to
     */
    public void setAccelerator(TimeAccelerator accelerator) {
        this.accelerator.set(accelerator);
    }

    /**
     * Returns the BooleanProperty running, indicating if the timer is running. The property should be read-only
     *
     * @return the BooleanProperty running
     */
    public ReadOnlyBooleanProperty runningProperty() {
        return running;
    }

    /**
     * Returns the value of the BooleanProperty running
     *
     * @return the value of the BooleanProperty running
     */
    public boolean isRunning() {
        return running.get();
    }

    /**
     * Sets the value of the BooleanProperty running
     *
     * @param running the value to which the running property value is to be set to
     */
    public void setRunning(boolean running) {
        this.running.set(running);
    }
}
