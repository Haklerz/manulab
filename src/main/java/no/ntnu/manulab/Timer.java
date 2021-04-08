package no.ntnu.manulab;

public class Timer {
    private long setTime;

    public Timer() {
        this.setTime = System.nanoTime();
    }

    /**
     * Sets a point in time relative to now.
     * 
     * @param time the relative time in seconds
     */
    public void set(double time) {
        this.setTime = System.nanoTime() + (long) (time * 1e9);
    }

    /**
     * Gets the relative time between now and the set time.
     * 
     * @return the relative time in seconds
     */
    public double get() {
        return (System.currentTimeMillis() - this.setTime) * 1e-9;
    }
}
