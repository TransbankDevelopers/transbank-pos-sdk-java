package cl.transbank.pos;

import cl.transbank.pos.exceptions.TransbankCannotOpenPortException;
import cl.transbank.pos.exceptions.TransbankInvalidPortException;
import cl.transbank.pos.exceptions.TransbankLinkException;
import cl.transbank.pos.exceptions.TransbankPortNotConfiguredException;
import cl.transbank.pos.exceptions.TransbankSaleException;
import cl.transbank.pos.helper.StringUtils;
import cl.transbank.pos.responses.CloseResponse;
import cl.transbank.pos.responses.DetailResponse;
import cl.transbank.pos.responses.RefundResponse;
import cl.transbank.pos.responses.KeysResponse;
import cl.transbank.pos.responses.SaleResponse;
import cl.transbank.pos.responses.TotalsResponse;
import cl.transbank.pos.utils.TbkBaudRate;
import cl.transbank.pos.utils.TbkReturn;
import cl.transbank.pos.utils.TransbankWrap;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static cl.transbank.pos.helper.StringUtils.pad;

public class POS {

    final static Logger logger = Logger.getLogger(POS.class);

    public static final String ERROR_VARIABLE_VACIA = "The environment variable NATIVE_TRANSBANK_WRAP is empty. Please configure it correctly.";
    public static final String ERROR_CARGA_LIBRERIA = "The Transbank native library could not be loaded. \n" +
            " To load this library the environment variable NATIVE_TRANSBANK_WRAP must point to the file (not the folder) with the native library. \n" +
            " Right now this variable NATIVE_TRANSBANK_WRAP has the value: ";
    public static final String CONFIGURE_BEFORE_TOTALS = "The port is not configured. Please configure it before trying to get the totals.";
    public static final String CONFIGURE_BEFORE_LAST_SALE = "The port is not configured. Please configure it before accessing the last sale.";
    public static final String CONFIGURE_BEFORE_SENDING_SALE = "The port is not configured. Please configure it before sending a sale.";
    public static final String CONFIGURE_BEFORE_REFUND_SALE = "The port is not configured. Please configure it before trying to refund a sale.";
    public static final String CONFIGURE_BEFORE_DETAILS = "The port is not configured. Please configure it before trying to obtain the sales details.";
    public static final String CONFIGURE_BEFORE_CLOSING = "The port is not configured. Please configure it before trying to close.";
    public static final String CONFIGURE_BEFORE_NORMAL = "The port is not configured. Please configure it before trying setting the POS to normal mode..";
    public static final String CONFIGURE_BEFORE_LOADING_KEYS = "The port is not configured. Please configure it before loading the keys.";
    public static final String CANNOT_OPEN_PORT = "Cannot open the port %s with baud rate %s.";
    public static final String CONFIGURE_BEFORE_POLL = "The port is not configured. Please configure it before polling the POS.";
    private static POS instance = null;

    private final String libraryPath;
    private final Port port;
    private final TbkBaudRate defaultBaudRate = TbkBaudRate.TBK_115200;

    private POS(String libraryPath) {
        port = new Port(null);
        this.libraryPath = libraryPath;
    }

    public static POS getInstance() throws TransbankLinkException {
        if (instance == null) {
            String nativeTransbankWrapper = System.getenv("NATIVE_TRANSBANK_WRAP");
            System.out.println("environment variable: " + nativeTransbankWrapper);
            if (StringUtils.isEmpty(nativeTransbankWrapper)) {
                throw new TransbankLinkException(ERROR_VARIABLE_VACIA);
            }
            try {
                System.load(nativeTransbankWrapper);
            } catch (UnsatisfiedLinkError e) {
                throw new TransbankLinkException(ERROR_CARGA_LIBRERIA + nativeTransbankWrapper, e);
            }
            instance = new POS(nativeTransbankWrapper);
        }
        return instance;
    }

    public KeysResponse loadKeys() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            KeysResponse response = new KeysResponse(TransbankWrap.load_keys());
            return response;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_LOADING_KEYS);
        }
    }

    public List<String> listPorts() throws TransbankLinkException {
        List<String> result = new ArrayList<String>();

        String list = null;
        try {
            list = TransbankWrap.list_ports();
        } catch (UnsatisfiedLinkError e) {
            throw new TransbankLinkException(ERROR_CARGA_LIBRERIA + libraryPath, e);
        }
        if (list != null) {
            String[] array = list.split("\\|");
            for (String elem : array) {
                result.add(elem);
            }
        }
        return result;
    }

    public void openPort(String portname) throws TransbankInvalidPortException, TransbankCannotOpenPortException {
        openPort(portname, this.defaultBaudRate);
    }

    public void openPort(String portname, TbkBaudRate baudRate) throws TransbankInvalidPortException, TransbankCannotOpenPortException {
        port.usePortname(portname);
        TbkReturn result = TransbankWrap.open_port(portname, baudRate.swigValue());
        if (result == TbkReturn.TBK_NOK) {
            port.clearPortname();
            throw new TransbankCannotOpenPortException(String.format(CANNOT_OPEN_PORT, portname, baudRate));
        }

    }

    public void closePort() {
        port.clearPortname();
        TransbankWrap.close_port();
    }

    public boolean poll() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            TbkReturn response = TransbankWrap.poll();
            logger.debug("poll: response: " + response);
            return TbkReturn.TBK_OK.equals(response);
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_POLL);
        }
    }

    public TotalsResponse getTotals() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            TotalsResponse tresponse = new TotalsResponse(TransbankWrap.get_totals());
            logger.debug("totals: response: " + tresponse);
            return tresponse;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_TOTALS);
        }
    }

    public SaleResponse getLastSale() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            SaleResponse lsresponse = new SaleResponse(TransbankWrap.last_sale());
            logger.debug("last sale: response: " + lsresponse);
            return lsresponse;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_LAST_SALE);
        }
    }

    public SaleResponse sale(int amount, int ticket) throws TransbankPortNotConfiguredException, TransbankSaleException {
        return sale(amount, pad(ticket, 6));
    }

    public SaleResponse sale(int amount, String ticket) throws TransbankPortNotConfiguredException, TransbankSaleException {
        if (port.isConfigured()) {
            SaleResponse sr = new SaleResponse(TransbankWrap.sale(amount, ticket, false));
            logger.debug("sale: response: " + sr);
            return sr;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_SENDING_SALE);
        }
    }

    public RefundResponse refund(int operationId) throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            RefundResponse rr = new RefundResponse(TransbankWrap.refund(operationId));
            logger.debug("refund: response: " + rr);
            return rr;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_REFUND_SALE);
        }
    }

    public List<DetailResponse> details(boolean printOnPos) throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            String data = TransbankWrap.sales_detail(printOnPos);
            logger.debug("details: raw line " + data + "\n;");
            String[] lines = data.split("\n");
            logger.debug("details: lines: " + (lines == null ? -1 : lines.length));
            List<DetailResponse> ldr = new ArrayList<>();
            for (String line : lines) {
                if (line.indexOf('|') < 0) {
                    logger.debug("linea invalida: " + line);
                    continue;
                }
                try {
                    DetailResponse dr = new DetailResponse(line);
                    logger.debug("refund: response: " + dr);
                    ldr.add(dr);
                } catch (Exception e) {
                    //if the parsing of the line fails, let's just skip it and go to the next one. Still, we log it
                    logger.debug("Parse error: " + line);
                    continue;
                }
            }
            return ldr;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_DETAILS);
        }
    }

    public CloseResponse close() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            CloseResponse cr = new CloseResponse(TransbankWrap.close());
            logger.debug("sale: response: " + cr);
            return cr;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_CLOSING);
        }
    }

    public boolean setNormalMode() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            TbkReturn result = TransbankWrap.set_normal_mode();
            logger.debug("normal mode: response: " + result);
            return TbkReturn.TBK_OK.equals(result);
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_NORMAL);
        }
    }
}

class Port {
    public static final String EMPTY_PORT_NAME = "Empty port name specified";
    private String portName = null;

    protected String getPortName() {
        return portName;
    }

    protected void usePortname(String port) throws TransbankInvalidPortException {
        if (StringUtils.isEmpty(port)) {
            throw new TransbankInvalidPortException(EMPTY_PORT_NAME);
        }
        this.portName = port;
    }

    protected void clearPortname() {
        this.portName = null;
    }

    protected boolean isConfigured() {
        return StringUtils.notEmpty(portName);
    }

    protected Port(String port) {
        this.portName = port;
    }
}