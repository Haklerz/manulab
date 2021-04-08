package no.ntnu.manulab;

public class Timer {
    private long recordedTime;

    public Timer() {
        this.recordedTime = System.nanoTime();
    }

    /**
     * Records a point in time relative to now.
     * 
     * @param time the relative time in seconds
     */
    public void set(double time) {
        this.recordedTime = System.nanoTime() + (long) (time * 1e9);
    }

    /**
     * Records the current time.
     */
    public void set() {
        set(0);
    }

    /**
     * Returns the relative time between now and the recorded time.
     * 
     * @return the relative time in seconds
     */
    public double get() {
        return (System.currentTimeMillis() - this.recordedTime) * 1e-9;
    }

    /**
     * Returns weather the recorded time has expired.
     * 
     * @return weather the time has expired
     */
    public boolean isExpired() {
        return get() >= 0;
    }
}
