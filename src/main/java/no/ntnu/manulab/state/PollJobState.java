package no.ntnu.manulab.state;

import no.ntnu.manulab.Handler;
import no.ntnu.manulab.Job;
import no.ntnu.manulab.Server;
import no.ntnu.manulab.Timer;

public class PollJobState implements HandlerState {

    public static final long POLLING_PERIOD_MILLIS = 2000;

    private Timer timer;

    @Override
    public HandlerState handle(Handler handler) {

        Server server = handler.getServer();

        if (timer.isExpired()) {

            Job job = server.requestJob();

            if (job != null) {
                handler.setCurrentJob(job);

                return new MonitorJobState();
            }

            timer.set(POLLING_PERIOD_MILLIS);
        }

        return null;
    }

}
