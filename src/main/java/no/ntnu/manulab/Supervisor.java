package no.ntnu.manulab;

import java.io.Closeable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Supervisor implements Runnable {

    private Map<String, Printer> printersByHost;
    private Queue<Job> jobs;

    public Supervisor() {
        this.printersByHost = new HashMap<>();
        this.jobs = new LinkedList<>();
    }

    @Override
    public void run() {

        while (true) {
            Job job = dequeueJob();

            if (job != null) {
                
            }
            
        }


        //close();
    }

    public void enqueueJob(Job job) {
        this.jobs.offer(job);
    }

    private Job dequeueJob() {
        return this.jobs.poll();
    }

    private void addPrinter(Printer printer) {
        this.printersByHost.put(printer.getHostname(), printer);
    }

    private void removePrinter(String hostname) {
        this.printersByHost.remove(hostname);
    }
}
