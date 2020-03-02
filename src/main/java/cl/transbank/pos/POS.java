package cl.transbank.pos;

import cl.transbank.pos.exceptions.TransbankCannotOpenPortException;
import cl.transbank.pos.exceptions.TransbankInvalidPortException;
import cl.transbank.pos.exceptions.TransbankLinkException;
import cl.transbank.pos.exceptions.TransbankPortNotConfiguredException;
import cl.transbank.pos.exceptions.TransbankSaleException;
import cl.transbank.pos.helper.StringUtils;
import cl.transbank.pos.responses.CodesResponses;
import cl.transbank.pos.responses.KeysResponse;
import cl.transbank.pos.responses.SaleResponse;
import cl.transbank.pos.responses.TotalsResponse;
import cl.transbank.pos.utils.TbkBaudRate;
import cl.transbank.pos.utils.TbkReturn;
import cl.transbank.pos.utils.TransbankWrap;

import java.util.ArrayList;
import java.util.List;

import static cl.transbank.pos.helper.StringUtils.pad;

public class POS {
    public static final String ERROR_VARIABLE_VACIA = "The environment variable NATIVE_TRANSBANK_WRAP is empty. Please configure it correctly.";
    public static final String ERROR_CARGA_LIBRERIA = "The Transbank native library could not be loaded. \n" +
            " To load this library the environment variable NATIVE_TRANSBANK_WRAP must point to the file (not the folder) with the native library. \n" +
            " Right now this variable NATIVE_TRANSBANK_WRAP has the value: " ;
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
            throw new TransbankPortNotConfiguredException("The port is not configured. Please configure it before loading the keys.");
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
            String [] array = list.split("\\|");
            for(String elem: array) {
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
            throw new TransbankCannotOpenPortException("Cannot open the port " + portname + " with baud rate " + baudRate);
        }

    }

    public void closePort() {
        port.clearPortname();
        TransbankWrap.close_port();
    }

    public boolean poll() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            TbkReturn response = TransbankWrap.poll();
            return TbkReturn.TBK_OK.equals(response);
        } else {
            throw new TransbankPortNotConfiguredException("The port is not configured. Please configure it before polling the POS.");
        }
    }

    public TotalsResponse getTotals() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            TotalsResponse tresponse = new TotalsResponse(TransbankWrap.get_totals());
            return tresponse;
        } else {
            throw new TransbankPortNotConfiguredException("The port is not configured. Please configure it before trying to get the totals.");
        }
    }

    public SaleResponse getLastSale() throws TransbankPortNotConfiguredException {
        if (port.isConfigured()) {
            SaleResponse lsresponse = new SaleResponse(TransbankWrap.last_sale());
            return lsresponse;
        } else {
            throw new TransbankPortNotConfiguredException("The port is not configured. Please configure it before accessing the last sale.");
        }
    }

    public SaleResponse sale(int amount, int ticket) throws TransbankPortNotConfiguredException, TransbankSaleException {
        return sale(amount, pad(ticket, 6));
    }
    public SaleResponse sale(int amount, String ticket) throws TransbankPortNotConfiguredException, TransbankSaleException {
        if (port.isConfigured()) {
            SaleResponse sr = new SaleResponse(TransbankWrap.sale(amount, ticket, false));
            if (sr.isSuccessful()) {
                return sr;
            } else {
                throw new TransbankSaleException("Sale returned an error: " + CodesResponses.map.get(sr.responseCode));
            }

        } else {
            throw new TransbankPortNotConfiguredException("The port is not configured. Please configure it before sending a sale.");
        }
    }
}

class Port {
    private String port;

    protected String getPort() {
        return port;
    }

    protected void usePortname(String port) throws TransbankInvalidPortException {
        if (StringUtils.isEmpty(port)) {
            throw new TransbankInvalidPortException("Se especifico un puerto vacio");
        }
        this.port = port;
    }

    protected void clearPortname() {
        this.port = null;
    }

    protected boolean isConfigured() {
        return StringUtils.notEmpty(port);
    }

    protected Port(String port) {
        this.port = port;
    }
}