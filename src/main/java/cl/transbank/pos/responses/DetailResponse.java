package cl.transbank.pos.responses;

import cl.transbank.pos.exceptions.TransbankParseException;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static cl.transbank.pos.helper.StringUtils.parseInt;
import static cl.transbank.pos.helper.StringUtils.parseLocalDate;
import static cl.transbank.pos.helper.StringUtils.parseLocalDateTime;
import static cl.transbank.pos.helper.StringUtils.parseLong;

@Log4j2
public class DetailResponse {

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
                {"authorizationCode", 5},
                {"amount", 6},
                {"last4Digits", 7},
                {"operationNumber", 8},
                {"cardType", 9},
                {"accountingDate", 10},
                {"accountNumber", 11},
                {"cardBrand", 12},
                {"realDate", 13},
                {"realTime", 14},
                {"employeeId", 15},
                {"tip", 16},
                {"feeAmount", 17},
                {"feeNumber", 18}
        };
        Map<String, Integer> values = Stream.of(codePosition).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
        map = Collections.unmodifiableMap(values);
    }

    private final int functionCode;
    private final int responseCode;
    private final long commerceCode;
    private final String terminalId;
    private final String ticket;
    private final String authorizationCode;
    private final int amount;
    private final int last4Digits;
    private final int operationNumber;
    private final String cardType;
    private final LocalDate accountingDate;
    private final long accountNumber;
    private final String cardBrand;
    private final LocalDateTime realDate;
    private final int employeeId;
    private final int tip;
    private final int feeAmount;
    private final int feeNumber;

    public DetailResponse(String saleData) throws TransbankParseException {
        if (saleData == null || saleData.indexOf('|') < 0) {
            log.debug("linea invalida: " + saleData);
            throw new TransbankParseException("Could not parse into a DetailResponse the line " + saleData);
        }
        try {
            log.debug("DetailsResponse: string: " + saleData);
            saleData = saleData.trim(); //the first character is a space

            String[] fields = saleData.split("\\|");
            for (int index = 0; index < fields.length; index++) {
                log.debug("fields[ " + index + " ] = " + fields[index]);
            }
            functionCode = parseInt(fields[map.get("functionCode")]);
            responseCode = parseInt(fields[map.get("responseCode")]);
            commerceCode = parseInt(fields[map.get("commerceCode")]);
            terminalId = fields[map.get("terminalId")];
            ticket = fields[map.get("ticket")];
            authorizationCode = fields[map.get("authorizationCode")];
            amount = parseInt(fields[map.get("amount")]);
            last4Digits = parseInt(fields[map.get("last4Digits")]);
            operationNumber = parseInt(fields[map.get("operationNumber")]);
            cardType = fields[map.get("cardType")];
            accountingDate = parseLocalDate(fields[map.get("accountingDate")]);
            accountNumber = parseLong(fields[map.get("accountNumber")]);
            cardBrand = fields[map.get("cardBrand")];
            realDate = parseLocalDateTime(fields[map.get("realDate")], fields[map.get("realTime")]);
            employeeId = parseInt(fields[map.get("employeeId")]);
            tip = parseInt(fields[map.get("tip")]);
            feeAmount = parseInt(fields[map.get("feeAmount")]);
            feeNumber = parseInt(fields[map.get("feeNumber")]);
        } catch (Exception e) {
            log.debug("Error al parsear: " + saleData);
            throw new TransbankParseException("Error when parsing into a DetailResponse the line " + saleData, e);
        }
    }

    public boolean isSuccessful() {
        return responseCode == 0;
    }

    @Override
    public String toString() {
        return "DetailResponse{" +
                "functionCode=" + functionCode +
                ", responseCode=" + responseCode +
                ", commerceCode=" + commerceCode +
                ", terminalId=" + terminalId +
                ", ticket=" + ticket +
                ", authorizationCode=" + authorizationCode +
                ", amount=" + amount +
                ", last4Digits=" + last4Digits +
                ", operationNumber=" + operationNumber +
                ", cardType=" + cardType +
                ", accountingDate=" + accountingDate +
                ", accountNumber=" + accountNumber +
                ", cardBrand=" + cardBrand +
                ", realDate=" + realDate +
                ", employeeId=" + employeeId +
                ", tip=" + tip +
                ", feeAmount=" + feeAmount +
                ", feeNumber=" + feeNumber +
                " }\n";
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

    public long getCommerceCode() {
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

    public int getFeeAmount() {
        return feeAmount;
    }

    public int getFeeNumber() {
        return feeNumber;
    }
}
