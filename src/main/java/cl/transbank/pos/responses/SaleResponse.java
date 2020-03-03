package cl.transbank.pos.responses;

import cl.transbank.pos.helper.StringUtils;
import cl.transbank.pos.utils.BaseResponse;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cl.transbank.pos.helper.StringUtils.*;

public class SaleResponse {

    final static Logger logger = Logger.getLogger(SaleResponse.class);

    public static final Map<String, Integer> map;

    static {
        //los valores deben ser INTs. Si no, se caera en el inicializador estatico.
        // _NO_ usar un 0 antes del numero porque en java eso significa un numero octal
        Map<String, Integer> values = Stream.of(new Object[][]{
                { "function", 0},
                { "responseCode", 1},
                { "commerceCode", 2},
                { "terminalId", 3},
                { "ticket", 4},
                { "autorizationCode", 5},
                { "amount", 6},
                { "sharesNumber", 7},
                { "sharesAmount", 8},
                { "last4Digits", 9},
                { "operationNumber", 10},
                { "cardType", 11},
                { "accountingDate", 12},
                { "accountNumber", 13},
                { "cardBrand", 14},
                { "realDate", 15},
                { "realTime", 16},
                { "employeeId", 17},
                { "tip", 18}
        }).collect(Collectors.toMap(data -> (String)data[0], data -> (Integer) data[1]));
        map = Collections.unmodifiableMap(values);
    }

    private final int function;
    private final int responseCode;
    private final int commerceCode;
    private final int terminalId;
    private final String ticket;
    private final int autorizationCode;
    private final int amount;
    private final int sharesNumber;
    private final int sharesAmount;
    private final int last4Digits;
    private final int operationNumber;
    private final String cardType;
    private final LocalDate accountingDate;
    private final long accountNumber;
    private final String cardBrand;
    private final LocalDateTime realDate;
    private final int employeeId;
    private final int tip;

    @Override
    public String toString() {
        return "SaleResponse{" +
                "function=" + function +
                ", responseCode=" + responseCode +
                ", commerceCode=" + commerceCode +
                ", terminalId=" + terminalId +
                ", ticket=" + ticket +
                ", autorizationCode=" + autorizationCode +
                ", amount=" + amount +
                ", sharesNumber=" + sharesNumber +
                ", sharesAmount=" + sharesAmount +
                ", last4Digits=" + last4Digits +
                ", operationNumber=" + operationNumber +
                ", cardType=" + cardType +
                ", accountingDate=" + accountingDate +
                ", accountNumber=" + accountNumber +
                ", cardBrand=" + cardBrand +
                ", realDate=" + realDate +
                ", employeeId=" + employeeId +
                ", tip=" + tip +
                "}";
    }

    public SaleResponse(String last_sale) {
        last_sale = last_sale.substring(1); //the first character is a space

        String[] fields = last_sale.split( "\\|");
        for(int index = 0; index < fields.length; index++) {
            logger.debug("fields[ " + index + " ] = " + fields[index] );
        }
        function = parseInt( fields[map.get("function")] );
        responseCode = parseInt( fields[map.get("responseCode")] );
        commerceCode = parseInt( fields[map.get("commerceCode")] );
        terminalId = parseInt( fields[map.get("terminalId")] );
        ticket = fields[map.get("ticket")];
        autorizationCode = parseInt( fields[map.get("autorizationCode")] );
        amount = parseInt( fields[map.get("amount")] );
        sharesNumber = parseInt( fields[map.get("sharesNumber")] );
        sharesAmount = parseInt( fields[map.get("sharesAmount")] );
        last4Digits = parseInt( fields[map.get("last4Digits")] );
        operationNumber = parseInt( fields[map.get("operationNumber")] );
        cardType = fields[map.get("cardType")];
        accountingDate = parseLocalDateTime(fields[map.get("accountingDate")]);
        accountNumber = parseLong( fields[map.get("accountNumber")] );
        cardBrand = fields[map.get("cardBrand")];
        realDate = parseLocalDateTime(fields[map.get("realDate")], fields[map.get("realTime")]);
        employeeId = parseInt( fields[map.get("employeeId")] );
        tip = parseInt( fields[map.get("tip")] );
    }

    DateTimeFormatter realDateTimeformatter = DateTimeFormatter.ofPattern("ddMMyyyy HHmmss");

    private LocalDateTime parseLocalDateTime(String realDate, String realTime) {
        if ("00-00-00".equals(realDate) || isEmpty(realDate)) {
            return null;
        }
        try {
            LocalDateTime result = LocalDateTime.parse(realDate + " " + realTime, realDateTimeformatter);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    DateTimeFormatter accountingDateTimeformatter = DateTimeFormatter.ofPattern("ddMMyyyy");

    private LocalDate parseLocalDateTime(String accountingDate) {
        if ("00-00-00".equals(accountingDate) || isEmpty(accountingDate)) {
            return null;
        }
        try {
            LocalDate result = LocalDate.parse(accountingDate + " " + accountingDate, accountingDateTimeformatter);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isSuccessful() {
        return responseCode == 0;
    }

    public static void main(String args[]) {
        String lastSaleCredito = " 0210|00|597029414300|75001146|000002|799889|2600|0||2008|000087|CR|||AX|03032020|112624|0|0| Ients/lib/idea_rt.jar\\=60277\\:/Applications/IntelliJ IDEA CE.app/C";
        SaleResponse lsrc = new SaleResponse(lastSaleCredito);
        logger.info("lsr: credito: " + lsrc);
        String lastSaleDebito = " 0260|00|597029414300|75001146|000001|828630|2600|0||3331|000086|DB|00-00-00|********331|DB|02032020|162214|0|0| u";
        SaleResponse lsrd = new SaleResponse(lastSaleDebito);
        logger.info("lsr: debito: " + lsrd);
    }

    public String getResponseMessage() {
        return ResponseCodes.getMessage(this.getResponseCode());
    }

    public int getFunction() {
        return function;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getCommerceCode() {
        return commerceCode;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public String getTicket() {
        return ticket;
    }

    public int getAutorizationCode() {
        return autorizationCode;
    }

    public int getAmount() {
        return amount;
    }

    public int getSharesNumber() {
        return sharesNumber;
    }

    public int getSharesAmount() {
        return sharesAmount;
    }

    public int getLast4Digits() {
        return last4Digits;
    }

    public int getOperationNumber() {
        return operationNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public LocalDateTime getRealDate() {
        return realDate;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public int getTip() {
        return tip;
    }

    public DateTimeFormatter getRealDateTimeformatter() {
        return realDateTimeformatter;
    }

    public DateTimeFormatter getAccountingDateTimeformatter() {
        return accountingDateTimeformatter;
    }
}
