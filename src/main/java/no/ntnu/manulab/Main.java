package no.ntnu.manulab;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;

public class Main {

    public static void main(String[] args) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet get = new HttpGet("http://printer17.local" + "/api/login");
            get.addHeader("X-Api-Key", "055A6B6898E74D9D83A65FBA4D8C2655");
            

            try (CloseableHttpResponse response = client.execute(get)) {

                System.out.println(response.getCode() + " " + response.getReasonPhrase());
                HttpEntity entity = response.getEntity();
                BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));

                String line;
                while (((line = reader.readLine()) != null)) {
                    System.out.println(line);
                }
                // do something useful with the response body
                // and ensure it is fully consumed
                EntityUtils.consume(entity);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
