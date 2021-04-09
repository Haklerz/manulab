package no.ntnu.manulab;

import java.util.HashMap;
import java.util.Map;

public class PrinterPool {
    private Map<String, Printer> printersByHostname;

    public PrinterPool() {
        this.printersByHostname = new HashMap<>();
    }

    public void addPrinter(Printer printer) {
        if (printer == null)
            throw new NullPointerException("Printer can not be null");

        this.printersByHostname.put(printer.getHostname(), printer);
    }

    public Printer getPrinter(String hostname) {
        return this.printersByHostname.get(hostname);
    }

    public Printer removePrinter(String hostname) {
        return this.printersByHostname.remove(hostname);
    }
}
