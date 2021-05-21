package no.ntnu.manulab;

public class Timer {
    
    private long expireMillis;

    public void set(long millis) {
        expireMillis = System.currentTimeMillis() + millis;
    }
    
    public boolean isExpired() {
        return System.currentTimeMillis() >= expireMillis;
    }
}
