package no.ntnu.manulab.state;

import java.io.IOException;

import com.github.cliftonlabs.json_simple.JsonObject;

import no.ntnu.manulab.Handler;
import no.ntnu.manulab.Job;
import no.ntnu.manulab.Timer;

public class MonitorJobState implements HandlerState {

    public static final long UPDATE_PERIOD_MILLIS = 500;

    private Timer timer;

    @Override
    public HandlerState handle(Handler handler) {
        
        Job job = handler.getCurrentJob();

        if (timer.isExpired()) {

            try {
                JsonObject response = handler.httpGet("/api/job", null);

                JsonObject progress = (JsonObject) response.get("progress");

                float completion;

            } catch (IOException e) {
                // Handle connection error
            }

            timer.set(UPDATE_PERIOD_MILLIS);
        }

        return null;
    }

}
