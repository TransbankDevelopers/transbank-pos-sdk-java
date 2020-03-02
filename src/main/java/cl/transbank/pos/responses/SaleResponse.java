package cl.transbank.pos.responses;

import cl.transbank.pos.helper.StringUtils;
import cl.transbank.pos.utils.BaseResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cl.transbank.pos.helper.StringUtils.*;

public class SaleResponse {

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

    public int function;
    public int responseCode;
    public int commerceCode;
    public int terminalId;
    public int ticket;
    public int autorizationCode;
    public int amount;
    public int sharesNumber;
    public int sharesAmount;
    public int last4Digits;
    public int operationNumber;
    public String cardType;
    public LocalDateTime accountingDate;
    public long accountNumber;

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

    public String cardBrand;
    public LocalDateTime realDate;
    public int employeeId;
    public int tip;

    public SaleResponse(String last_sale) {
        System.out.println("last sale: '" + last_sale + "'");
        last_sale = last_sale.substring(1); //the first character is a space

        String[] fields = last_sale.split( "\\|");
        for(int index = 0; index < fields.length; index++) {
            System.out.println("fields[ " + index + " ] = " + fields[index] );
        }
        System.out.println("fields.length: " + fields.length);
        System.out.println("ticket: " + map.get("ticket") + " fields[] = " + fields[map.get("ticket")]);
        function = parseInt( fields[map.get("function")] );
        responseCode = parseInt( fields[map.get("responseCode")] );
        commerceCode = parseInt( fields[map.get("commerceCode")] );
        terminalId = parseInt( fields[map.get("terminalId")] );
        ticket = parseInt( fields[map.get("ticket")] );
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

    private LocalDateTime parseLocalDateTime(String realDate, String realTime) {
        return LocalDateTime.now();
    }

    private LocalDateTime parseLocalDateTime(String accountingDate) {
        return LocalDateTime.now();
    }

    public boolean isSuccessful() {
        return responseCode == 0;
    }

    public static void main(String args[]) {
        String lastSale = "0260|11|597029414300|75001146||      |0|0|||0|CR|||||||0| h";
        SaleResponse lsr = new SaleResponse(lastSale);
        System.out.println("lsr: " + lsr);
    }
}
