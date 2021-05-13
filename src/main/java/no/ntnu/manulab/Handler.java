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

/**
 * Ready: Request a job from server
 * 
 * Busy: Monitor job progress
 */
public class Handler implements Runnable {

    private final String HOSTNAME;
    private final String API_KEY;

    private CloseableHttpClient httpClient;
    private Job currentJob;

    public Handler(String hostname, String apiKey) {
        this.HOSTNAME = hostname;
        this.API_KEY = apiKey;
    }

    @Override
    public void run() {
        httpClient = HttpClients.createDefault();

        // state machine

        try {
            httpClient.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        httpClient = null;
    }

    private JsonObject callMethod(ClassicHttpRequest request, JsonObject json) throws IOException {
        request.addHeader("X-Api-Key", API_KEY);
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
            uri = new URI("http", HOSTNAME, path, null);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return uri;
    }

    private JsonObject httpGet(String path, JsonObject json) throws IOException {
        return callMethod(new HttpGet(getURI(path)), json);
    }

    private JsonObject httpPost(String path, JsonObject json) throws IOException {
        return callMethod(new HttpPost(getURI(path)), json);
    }

    /**
     * Returns the printers host adress.
     * 
     * @return the printers host adress
     */
    public String getHostname() {
        return this.HOSTNAME;
    }
    
}
