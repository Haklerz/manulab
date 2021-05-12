package no.ntnu.manulab;

import java.util.HashMap;
import java.util.Map;

public class PrinterPool {
    private Map<String, PrinterHandler> printersByHostname;

    public PrinterPool() {
        this.printersByHostname = new HashMap<>();
    }

    public void addPrinter(PrinterHandler printer) {
        if (printer == null)
            throw new NullPointerException("Printer can not be null");

        this.printersByHostname.put(printer.getHostname(), printer);
    }

    public PrinterHandler getPrinter(String hostname) {
        return this.printersByHostname.get(hostname);
    }

    public PrinterHandler removePrinter(String hostname) {
        return this.printersByHostname.remove(hostname);
    }
}
