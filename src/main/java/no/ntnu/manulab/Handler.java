package no.ntnu.manulab;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import no.ntnu.manulab.state.HandlerState;
import no.ntnu.manulab.state.PollJobState;

/**
 * Ready: Request a job from server
 * 
 * Busy: Monitor job progress
 */
public class Handler implements Runnable {

    private final Server server;
    private final String hostname;
    private final String apiKey;

    private CloseableHttpClient httpClient;
    private Job currentJob;
    private HandlerState currentState;

    /**
     * Creates a Handler.
     * 
     * @param server
     * @param hostname
     * @param apiKey
     */
    public Handler(Server server, String hostname, String apiKey) {
        this.server = server;
        this.hostname = hostname;
        this.apiKey = apiKey;
    }

    @Override
    public void run() {
        httpClient = HttpClients.createDefault();

        currentState = new PollJobState();

        while (currentState != null) {
            HandlerState nextState = currentState.handle(this);

            if (nextState != null)
                currentState = nextState;
        }

        closeHttpClient();
    }

    private void closeHttpClient() {
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        httpClient = null;
    }

    /**
     * Calls a method of the printer API passing in the given payload.
     * 
     * @param request The method request
     * @param json    The json payload
     * @return The response from the printer
     * @throws IOException if a IO-error occured
     */
    private JsonObject callMethod(ClassicHttpRequest request, JsonObject json) throws IOException {
        request.addHeader("X-Api-Key", apiKey);
        if (json != null)
            request.setEntity(new StringEntity(json.toJson(), ContentType.APPLICATION_JSON));

        JsonObject response = null;
        try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            HttpEntity entity = httpResponse.getEntity();
            String body = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);

            JsonObject defaultJson = new JsonObject().putChain("response_body", body);

            response = Jsoner.deserialize(body, defaultJson);
            EntityUtils.consume(entity);
        }
        return response;
    }

    private URI getURI(String path) {
        URI uri = null;
        try {
            uri = new URI("http", hostname, path, null);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return uri;
    }

    /**
     * Performs a http GET request and returns the result.
     * 
     * @param path the method path
     * @param json the json data
     * @return the result of the request
     * @throws IOException if an IO error occured
     */
    public JsonObject httpGet(String path, JsonObject json) throws IOException {
        return callMethod(new HttpGet(getURI(path)), json);
    }

    /**
     * Performs a http POST request and returns the result.
     * 
     * @param path the method path
     * @param json the json data
     * @return the result of the request
     * @throws IOException if an IO error occured
     */
    public JsonObject httpPost(String path, JsonObject json) throws IOException {
        return callMethod(new HttpPost(getURI(path)), json);
    }

    /**
     * Returns the hostname.
     * 
     * @return the hostname
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Returns the current job.
     * 
     * @return the current job
     */
    public Job getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(Job job) {
        this.currentJob = job;
    }

    public Server getServer() {
        return server;
    }

}
