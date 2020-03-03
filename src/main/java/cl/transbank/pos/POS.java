package cl.transbank.pos;

import cl.transbank.pos.exceptions.TransbankCannotOpenPortException;
import cl.transbank.pos.exceptions.TransbankInvalidPortException;
import cl.transbank.pos.exceptions.TransbankLinkException;
import cl.transbank.pos.exceptions.TransbankPortNotConfiguredException;
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

    public static final String NATIVE_TRANSBANK_WRAP = "NATIVE_TRANSBANK_WRAP";

    public static final String EMPTY_VARIABLE_ERROR = "The environment variable " + NATIVE_TRANSBANK_WRAP + " is empty. Please configure it correctly.";
    public static final String LIBRARY_LOAD_ERROR = "The Transbank native library could not be loaded. \n" +
            " To load this library the environment variable " + NATIVE_TRANSBANK_WRAP + " must point to the file (not the folder) with the native library. \n" +
            " Right now this variable " + NATIVE_TRANSBANK_WRAP + " has the value: ";
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

    /**
     * The Constructor of the POS.
     * It's private so the user is supposed to use getInstance instead
     * @param libraryPath the path of the dll (Windows) or .so (Linux) or dylib (Mac OS) with the native C Transbank library
     *
     */
    private POS(String libraryPath) {
        port = new Port(null); //by setting the portname to null, we ensue the POS cannot be used just yet
        this.libraryPath = libraryPath;
    }

    /**
     * Factory method. It returns a POS instance. There should only be a single instance, so it returns a singleton.
     * @return the POS singleton instance
     * @throws TransbankLinkException if it cannot find the native C Transbank library
     */
    public static POS getInstance() throws TransbankLinkException {
        if (instance == null) {
            String nativeTransbankWrapper = System.getenv(NATIVE_TRANSBANK_WRAP);
            logger.info("environment variable " + NATIVE_TRANSBANK_WRAP + " : " + nativeTransbankWrapper);
            if (StringUtils.isEmpty(nativeTransbankWrapper)) {
                throw new TransbankLinkException(EMPTY_VARIABLE_ERROR);
            }
            try {
                System.load(nativeTransbankWrapper);
                logger.debug("Native library loaded!");
            } catch (UnsatisfiedLinkError e) {
                throw new TransbankLinkException(LIBRARY_LOAD_ERROR + nativeTransbankWrapper, e);
            }
            instance = new POS(nativeTransbankWrapper);
        }
        return instance;
    }

    /**
     * It makes the POS load the keys from the Transbank servers. It does not actually return the keys to the caller.
     * @return Whether the keys were loaded. Also the terminal id, function and commerce id
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
    public KeysResponse loadKeys() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            KeysResponse response = new KeysResponse(TransbankWrap.load_keys());
            logger.debug("load keys response: " + response);
            return response;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_LOADING_KEYS);
        }
    }

    /**
     * This list the serial ports connected to the computer. The user should choose one to call open port.
     * @return a list of port names
     * @throws TransbankLinkException if there are problems calling the native library
     */
    public List<String> listPorts() throws TransbankLinkException {
        List<String> result = new ArrayList<String>();

        String list = null;
        try {
            list = TransbankWrap.list_ports();
        } catch (UnsatisfiedLinkError e) {
            throw new TransbankLinkException(LIBRARY_LOAD_ERROR + libraryPath, e);
        }
        if (list != null) {
            String[] array = list.split("\\|");
            for (String elem : array) {
                result.add(elem);
            }
        }
        return result;
    }

    /**
     * Opens a serial port with the default baud rate. Will silently succeed, not returning anything on success,
     * and throwing an exception on failure.
     * @param portname the name of the port
     * @throws TransbankInvalidPortException the port specified is empty or otherwise found invalid before trying to actually open
     * @throws TransbankCannotOpenPortException when there's a problem opening the port
     */
    public void openPort(String portname) throws TransbankInvalidPortException, TransbankCannotOpenPortException {
        openPort(portname, this.defaultBaudRate);
    }

    /**
     * Opens a serial port with the default baud rate. Will silently succeed, not returning anything on success,
     * and throwing an exception on failure.
     * @param portname the name of the port
     * @param baudRate baud rate to use with the port
     * @throws TransbankInvalidPortException the port specified is empty or otherwise found invalid before trying to actually open it
     * @throws TransbankCannotOpenPortException when there's a problem opening the port
     */
    public void openPort(String portname, TbkBaudRate baudRate) throws TransbankInvalidPortException, TransbankCannotOpenPortException {
        port.usePortname(portname);
        TbkReturn result = TransbankWrap.open_port(portname, baudRate.swigValue());
        if (result == TbkReturn.TBK_NOK) {
            port.clearPortname();
            throw new TransbankCannotOpenPortException(String.format(CANNOT_OPEN_PORT, portname, baudRate));
        }

    }

    /**
     * This method just closes the port. The port name will be set null too.
     */
    public void closePort() {
        port.clearPortname();
        TransbankWrap.close_port();
    }

    /**
     * Checks whether the POS is connected.
     * @return boolean whether the POS is connected or not
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
    public boolean poll() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            TbkReturn response = TransbankWrap.poll();
            logger.debug("poll: response: " + response);
            return TbkReturn.TBK_OK.equals(response);
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_POLL);
        }
    }

    /**
     * Returns the total of the sales done with this POS.
     * As a side effect it will print it on the POS.
     * @return Whether the operation succeeded, the amount of transactions, the total money transacted, the terminal and commerce id
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
    public TotalsResponse getTotals() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            TotalsResponse tresponse = new TotalsResponse(TransbankWrap.get_totals());
            logger.debug("totals: response: " + tresponse);
            return tresponse;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_TOTALS);
        }
    }

    /**
     * Returns the last sale done.
     * As a side effect, it prints it on the POS.
     * @return the data of the last sale.
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
    public SaleResponse getLastSale() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            SaleResponse lsresponse = new SaleResponse(TransbankWrap.last_sale());
            logger.debug("last sale: response: " + lsresponse);
            return lsresponse;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_LAST_SALE);
        }
    }

    /**
     * Starts the sale process on the POS. Upon calling this, the final user must use the POS to sell something to a client.
     * @param amount the amount sold, in whatever currency configured. Probably CLP.
     * @param ticket the number of the Boleta. it's a number, but in practice it will be padded / cut to six characters (padded with leftward 0s)
     * @return the data of the sale, including whether it succeeded at all
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
    public SaleResponse sale(int amount, int ticket) throws TransbankPortNotConfiguredException {
        String strTicket = pad(ticket, 6);
        if (port.isConfigured()) {
            SaleResponse sr = new SaleResponse(TransbankWrap.sale(amount, strTicket, false));
            logger.debug("sale: response: " + sr);
            return sr;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_SENDING_SALE);
        }
    }

    /**
     * Does a refund of a sale. It needs the final user to swipe the client's card through the POS and insert an authorization PIN.
     * @param operationId the operation id of the sale. This was returned when the sale was done,
     *                    or it can be seen in the last sale result, or the details result
     * @return The data of the refund, including whether it succeeded, and some other data like the terminal id, the commerce id, etc.
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
    public RefundResponse refund(int operationId) throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            RefundResponse rr = new RefundResponse(TransbankWrap.refund(operationId));
            logger.debug("refund: response: " + rr);
            return rr;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_REFUND_SALE);
        }
    }

    /**
     * Obtains the data of the last few sales. If the printOnPos param is true, it will use the POS's printer to print them
     * but it will return an empty list to the user. If the param is false, it won't print on the POS but it will actually
     * return the data in the List to the user.
     * @param printOnPos whether to print the list on the POS or not. Printing on the POS and receiving the data electronically
     *                   are mutually exclusive.
     * @return a list of the sales done this (logical) day, but only if the printOnPos param is false. Otherwise, an empty list.
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
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

    /**
     * Closes the day, wiping the sales done from the POS memory (so List and Last Sale will return empty) and loading the keys
     * @return The same data as the load_keys operation
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
    public CloseResponse close() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            CloseResponse cr = new CloseResponse(TransbankWrap.close());
            logger.debug("sale: response: " + cr);
            return cr;
        } else {
            throw new TransbankPortNotConfiguredException(CONFIGURE_BEFORE_CLOSING);
        }
    }

    /**
     * Takes the POS out of integrated mode, thus making it impossible to keep using it through the SDK without going
     * through the "Comercio" menu on the physical POS
     * @return whether it succeeded. It probably did unless it was disconnected already.
     * @throws TransbankPortNotConfiguredException if called before opening a port.
     */
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