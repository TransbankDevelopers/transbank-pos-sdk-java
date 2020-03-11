package cl.transbank.pos;

import cl.transbank.pos.exceptions.TransbankException;
import cl.transbank.pos.responses.CloseResponse;
import cl.transbank.pos.responses.DetailResponse;
import cl.transbank.pos.responses.RefundResponse;
import cl.transbank.pos.responses.KeysResponse;
import cl.transbank.pos.responses.SaleResponse;
import cl.transbank.pos.responses.TotalsResponse;
import org.apache.log4j.Logger;

import java.util.List;

import static cl.transbank.pos.helper.StringUtils.*;

public class SDKTest {
    private static final Logger logger = Logger.getLogger(SDKTest.class);

    private static final boolean DO_GET_TOTALS = false;
    private static final boolean DO_GET_KEYS = false;
    private static final boolean DO_LAST_SALE = false;
    private static final boolean DO_SELL = true;
    private static final boolean DO_REFUND = false;
    private static final boolean DO_DETAILS = false;
    private static final boolean DO_CLOSE = false;
    private static final boolean DO_NORMAL_MODE = false;

    public static void main(String [] args) throws TransbankException {
        POS pos = POS.getInstance();
        List<String> ports = pos.listPorts();
        logger.info("ports: " + ports);

        String port = selectPort(ports);
        if (isEmpty(port)) {
            logger.info("+ Puerto nulo o vacio");
        }
        logger.info("+ abriendo puerto");
        pos.openPort(port);
        logger.info("+ puerto abierto. Chequeando que este conectado.");
        String openPort = pos.getOpenPort();
        logger.info("+ puerto que esta abierto: " + openPort);
        boolean pollResult = pos.poll();
        logger.info("+ poll? " + pollResult);
        if (!pollResult) {
            logger.info("+ no se pudo hacer poll. Cerrando puerto y terminando.");
            pos.closePort();
            return;
        }
        if (DO_GET_TOTALS) {
            logger.info("+ puerto abierto. Cargando totales.");
            TotalsResponse tr = pos.getTotals();
            logger.info("totals: " + tr.toString());
        }
        if (DO_GET_KEYS) {
            logger.info("+ puerto abierto y conectado. Cargando llaves.");
            KeysResponse kr = pos.loadKeys();
            logger.info("+ llaves: " + kr);
        }
        if (DO_SELL) {
            logger.info("+ puerto abierto y conectado. Realizando una venta.");
            SaleResponse sr = pos.sale(2600, 2);
            logger.info("sale response: " + sr);
        }
        if (DO_LAST_SALE) {
            logger.info("+ puerto abierto. Obteniendo ultima venta.");
            SaleResponse lsr = pos.getLastSale();
            logger.info("+ last sale: " + lsr);
            logger.info("+ lsr map: " + SaleResponse.map);
        }
        if (DO_REFUND) {
            logger.info("+ puerto abierto. Devolviendo plata.");
            RefundResponse rr = pos.refund(87);
            logger.info("+ refund: " + rr);
        }
        if (DO_DETAILS) {
            logger.info("+ puerto abierto. Obteniendo detalles.");
            List<DetailResponse> ldr = POS.getInstance().details(false);
            logger.info("+ details: " + ldr);
        }
        if (DO_CLOSE) {
            logger.info("+ puerto abierto. Cierre Caja.");
            CloseResponse cr = pos.close();
            logger.info("+ refund: " + cr);
        }
        if (DO_NORMAL_MODE) {
            logger.info("+ puerto abierto. Modo normal (quitar POS de modo integrado)");
            boolean normal = pos.setNormalMode();
            logger.info("+ normal mode? " + normal);
        }
        logger.info("+ cerrando puerto");
        pos.closePort();
        logger.info("+ puerto cerrado");

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
