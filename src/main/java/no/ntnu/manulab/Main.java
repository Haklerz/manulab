package no.ntnu.manulab;

import java.io.IOException;
import java.net.URISyntaxException;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;

import org.apache.hc.core5.http.Method;

public class Main {

    public static void main(String[] args) throws IOException, JsonException, URISyntaxException {
        Supervisor supervisor = new Supervisor();
        supervisor.addPrinter(new Printer("printer17.local", "8F5BA45DD57648809F06B04AFF8A0096"));
        supervisor.addPrinter(new Printer("printer18.local", "ACC65C0B35154E3CB8CF5DD691FF6EEC"));
        supervisor.addPrinter(new Printer("printer19.local", "2D513C05D4374E72839BE7A6AB45648D"));

        System.out.println();

        {
            JsonObject json = new JsonObject();
            json.put("passive", true);

            supervisor.callMethod("printer17.local", Method.POST, "/api/login", json);
            supervisor.callMethod("printer18.local", Method.POST, "/api/login", json);
            supervisor.callMethod("printer19.local", Method.POST, "/api/login", json);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        {
            JsonObject json = new JsonObject();
            json.put("command", "home");
            json.put("axes", new JsonArray().addChain("x").addChain("y").addChain("z"));

            supervisor.callMethod("printer17.local", Method.POST, "/api/printer/printhead", json);
            supervisor.callMethod("printer18.local", Method.POST, "/api/printer/printhead", json);
            supervisor.callMethod("printer19.local", Method.POST, "/api/printer/printhead", json);
        }

        supervisor.close();
    }
}
