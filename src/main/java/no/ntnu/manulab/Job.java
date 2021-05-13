package no.ntnu.manulab;

import java.io.File;
import java.io.FileNotFoundException;

public class Job {

    private final long jobNumber;
    private final File file;

    private float completion;
    private int printTime;
    private int printTimeLeft;
    private String state;

    public Job(long jobNumber, String path) throws FileNotFoundException {
        this.jobNumber = jobNumber;
        this.file = new File(path);

        // TODO - Throw exception if file is not found
        if (!file.exists())
            throw new FileNotFoundException();
    }

    public Long getJobNumber() {
        return jobNumber;
    }

    public String getFilename() {
        return file.getName();
    }

    public float getCompletion() {
        return completion;
    }

    public int getPrintTime() {
        return printTime;
    }

    public int getPrintTimeLeft() {
        return printTimeLeft;
    }

    public String getState() {
        return state;
    }

}
