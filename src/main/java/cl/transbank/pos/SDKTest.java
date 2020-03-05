package cl.transbank.pos;

import cl.transbank.pos.exceptions.TransbankException;
import cl.transbank.pos.responses.CloseResponse;
import cl.transbank.pos.responses.DetailResponse;
import cl.transbank.pos.responses.RefundResponse;
import cl.transbank.pos.responses.KeysResponse;
import cl.transbank.pos.responses.SaleResponse;
import cl.transbank.pos.responses.TotalsResponse;

import java.util.List;

import static cl.transbank.pos.helper.StringUtils.*;

public class SDKTest {

    private static final boolean doGetTotals = false;
    private static final boolean doGetKeys = false;
    private static final boolean doLastSale = false;
    private static final boolean doSell = false;
    private static final boolean doRefund = false;
    private static final boolean doDetails = false;
    private static final boolean doClose = false;
    private static final boolean doNormalMode = false;

    public static void main(String [] args) throws TransbankException {
        POS pos = POS.getInstance();
        List<String> ports = pos.listPorts();
        System.out.println("ports: " + ports);

        String port = selectPort(ports);
        if (isEmpty(port)) {
            System.out.println("+ Puerto nulo o vacio");
        }
        System.out.println("+ abriendo puerto");
        pos.openPort(port);
        System.out.println("+ puerto abierto. Chequeando que este conectado.");
        String openPort = pos.getOpenPort();
        System.out.println("+ puerto que esta abierto: " + openPort);
        boolean pollResult = pos.poll();
        System.out.println("+ poll? " + pollResult);
        if (pollResult && doGetTotals) {
            System.out.println("+ puerto abierto. Cargando totales.");
            TotalsResponse tr = pos.getTotals();
            System.out.println("totals: " + tr.toString());
        }
        if (pollResult && doGetKeys) {
            System.out.println("+ puerto abierto y conectado. Cargando llaves.");
            KeysResponse kr = pos.loadKeys();
            System.out.println("+ llaves: " + kr);
        }
        if (pollResult && doSell) {
            System.out.println("+ puerto abierto y conectado. Realizando una venta.");
            SaleResponse sr = pos.sale(2600, 2);
            System.out.println("sale response: " + sr);
        }
        if (pollResult && doLastSale) {
            System.out.println("+ puerto abierto. Obteniendo ultima venta.");
            SaleResponse lsr = pos.getLastSale();
            System.out.println("+ last sale: " + lsr);
            System.out.println("+ lsr map: " + SaleResponse.map);
        }
        if (pollResult && doRefund) {
            System.out.println("+ puerto abierto. Devolviendo plata.");
            RefundResponse rr = pos.refund(87);
            System.out.println("+ refund: " + rr);
        }
        if (pollResult && doDetails) {
            System.out.println("+ puerto abierto. Obteniendo detalles.");
            List<DetailResponse> ldr = pos.details(false);
            System.out.println("+ details: " + ldr);
        }
        if (pollResult && doClose) {
            System.out.println("+ puerto abierto. Cierre Caja.");
            CloseResponse cr = pos.close();
            System.out.println("+ refund: " + cr);
        }
        if (pollResult && doNormalMode) {
            System.out.println("+ puerto abierto. Modo normal (quitar POS de modo integrado)");
            boolean normal = pos.setNormalMode();
            System.out.println("+ normal mode? " + normal);
        }
        System.out.println("+ cerrando puerto");
        pos.closePort();
        System.out.println("+ puerto cerrado");

    }

    private static String selectPort(List<String> ports) {
        for (String port: ports) {
            if (notEmpty(port) && port.contains("usbmodem0")) {
                return port;
            }
        }
        return null;
    }
}
