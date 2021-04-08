package no.ntnu.manulab;

public class Ticker {

    private long interval;
    private long previous;

    public Ticker(long interval) {
        this.interval = interval;
        this.previous = 0;
    }

    public boolean tick() {
        long current = System.currentTimeMillis();
        if (current - previous >= interval) {
            previous = current;
            return true;
        }
        return false;
    }
}
