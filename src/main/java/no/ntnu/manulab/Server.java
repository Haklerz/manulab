package no.ntnu.manulab;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * 
 */
public class Server implements Runnable {

    private Map<String, Handler> handlersByHostname;
    private Map<Long, Job> jobsByJobNumber;
    private Queue<Job> jobQueue;

    private long currentJobNumber;

    public Server() {
        this.handlersByHostname = new HashMap<>();
        this.jobsByJobNumber = new HashMap<>();
        this.jobQueue = new LinkedList<>();
    }

    @Override
    public void run() {

        // Housekeeping
    }

    public long createJob(String path) throws FileNotFoundException {
        Job job = new Job(getJobNumber(), path);

        jobsByJobNumber.put(job.getJobNumber(), job);
        jobQueue.offer(job);

        return job.getJobNumber();
    }

    public synchronized Job requestJob() {
        return jobQueue.poll();
    }

    private long getJobNumber() {
        return currentJobNumber++;
    }

}
