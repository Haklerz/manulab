package no.ntnu.manulab;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPatch;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.HttpPut;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.Method;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

/**
 * Represents a 3D-printer
 */
public class Printer implements Closeable {
    public final String host, apiKey;
    private final CloseableHttpClient httpClient;

    public Printer(String host, String apiKey) {
        this.host = host;
        this.apiKey = apiKey;
        this.httpClient = HttpClients.createDefault();
    }

    public JsonObject callMethod(Method method, String path, JsonObject json) throws IOException, URISyntaxException {
        URI uri = new URI("http", this.host, path, null);

        ClassicHttpRequest request = createRequest(method, uri);
        if (request == null) {
            throw new IllegalArgumentException("Given method type is not valid");
        }

        request.addHeader("X-Api-Key", this.apiKey);
        request.setEntity(new StringEntity(json.toJson(), ContentType.APPLICATION_JSON));

        JsonObject response = null;
        try (CloseableHttpResponse httpResponse = this.httpClient.execute(request)) {
            HttpEntity entity = httpResponse.getEntity();
            String body = IOUtils.toString(entity.getContent(), StandardCharsets.UTF_8);

            JsonObject defaultValue = new JsonObject().putChain("error", body);

            response = Jsoner.deserialize(body, defaultValue);

            EntityUtils.consume(entity);
        }

        return response;
    }

    /**
     * Returns an instance of the corresponding request given a method type.
     * 
     * @param method
     * @param uri
     * @return
     */
    private ClassicHttpRequest createRequest(Method method, URI uri) {
        switch (method) {
        case DELETE:
            return new HttpDelete(uri);
        case GET:
            return new HttpGet(uri);
        case PATCH:
            return new HttpPatch(uri);
        case POST:
            return new HttpPost(uri);
        case PUT:
            return new HttpPut(uri);
        default:
            return null;
        }
    }

    /**
     * Returns the printers host adress.
     * 
     * @return the printers host adress
     */
    public String getHost() {
        return this.host;
    }

    @Override
    public void close() throws IOException {
        this.httpClient.close();
    }
}
