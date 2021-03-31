package no.ntnu.manulab;

import java.io.Closeable;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.github.cliftonlabs.json_simple.JsonException;
import com.github.cliftonlabs.json_simple.JsonObject;

import org.apache.hc.core5.http.Method;

public class Supervisor implements Runnable, Closeable {

    private Map<String, Printer> printersByHost;

    public Supervisor() {
        this.printersByHost = new HashMap<>();
    }

    @Override
    public void run() {




    }

    public JsonObject callMethod(String host, Method method, String path, JsonObject json) throws IOException, JsonException, URISyntaxException {
        Printer printer = this.printersByHost.get(host);
        return printer.callMethod(method, path, json);
    }

    public void addPrinter(Printer printer) {
        this.printersByHost.put(printer.getHost(), printer);
    }

    public void removePrinterByHost(String host) throws IOException {
        this.printersByHost.remove(host);
    }

    @Override
    public void close() throws IOException {
        this.printersByHost.values().forEach(printer -> {
            try {
                printer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
        
    }


}