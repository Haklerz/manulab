package no.ntnu.manulab;

import java.util.HashSet;
import java.util.Set;

public class Super implements Runnable {

    private Set<Printer> printers;

    public Super() {
        this.printers = new HashSet<>();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub




    }

    public void addPrinter(Printer printer) {
        this.printers.add(printer);
    }

    public void removePrinter(Printer printer) {
        this.printers.remove(printer);
    }

    public void removePrinterByHost(String host) {
        this.printers.removeIf(printer -> host.equals(printer.getHost()));
    }
}
