package cl.transbank.pos.responses;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
        Object[][] codePosition = {
                {"functionCode", 0},
                {"responseCode", 1},
                {"commerceCode", 2},
                {"terminalId", 3},
                {"ticket", 4},
                {"autorizationCode", 5},
                {"amount", 6},
                {"sharesNumber", 7},
                {"sharesAmount", 8},
                {"last4Digits", 9},
                {"operationNumber", 10},
                {"cardType", 11},
                {"accountingDate", 12},
                {"accountNumber", 13},
                {"cardBrand", 14},
                {"realDate", 15},
                {"realTime", 16},
                {"employeeId", 17},
                {"tip", 18}
        };
        Map<String, Integer> values = Stream.of(codePosition).collect(Collectors.toMap(data -> (String)data[0], data -> (Integer) data[1]));
        map = Collections.unmodifiableMap(values);
    }

    private final int functionCode;
    private final int responseCode;
    private final int commerceCode;
    private final String terminalId;
    private final String ticket;
    private final String authorizationCode;
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
                "isSuccesful=" + isSuccessful() +
                ", functionCode=" + functionCode +
                ", responseCode=" + responseCode +
                ", commerceCode=" + commerceCode +
                ", terminalId=" + terminalId +
                ", ticket=" + ticket +
                ", autorizationCode=" + authorizationCode +
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
                " }\n";
    }

    public SaleResponse(String saledata) {
        BasicConfigurator.configure();
        logger.debug("SaleResponse: string: " + saledata);
        saledata = saledata.substring(1); //the first character is a space

        String[] fields = saledata.split( "\\|");
        for(int index = 0; index < fields.length; index++) {
            logger.debug("fields[ " + index + " ] = " + fields[index] );
        }
        functionCode = parseInt( fields[map.get("functionCode")] );
        responseCode = parseInt( fields[map.get("responseCode")] );
        commerceCode = parseInt( fields[map.get("commerceCode")] );
        terminalId = fields[map.get("terminalId")];
        ticket = fields[map.get("ticket")];
        authorizationCode = fields[map.get("autorizationCode")];
        amount = parseInt( fields[map.get("amount")] );
        sharesNumber = parseInt( fields[map.get("sharesNumber")] );
        sharesAmount = parseInt( fields[map.get("sharesAmount")] );
        last4Digits = parseInt( fields[map.get("last4Digits")] );
        operationNumber = parseInt( fields[map.get("operationNumber")] );
        cardType = fields[map.get("cardType")];
        accountingDate = parseLocalDate(fields[map.get("accountingDate")]);
        accountNumber = parseLong( fields[map.get("accountNumber")] );
        cardBrand = fields[map.get("cardBrand")];
        realDate = parseLocalDateTime(fields[map.get("realDate")], fields[map.get("realTime")]);
        employeeId = parseInt( fields[map.get("employeeId")] );
        tip = parseInt( fields[map.get("tip")] );
    }

    public boolean isSuccessful() {
        return responseCode == 0;
    }

    public String getResponseMessage() {
        return ResponseCodes.getMessage(this.getResponseCode());
    }

    public int getFunctionCode() {
        return functionCode;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getCommerceCode() {
        return commerceCode;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public String getTicket() {
        return ticket;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
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

}
