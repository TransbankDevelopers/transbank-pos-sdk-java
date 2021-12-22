package cl.transbank.pos.responses.integrado;

import cl.transbank.pos.responses.common.LoadKeysResponse;
import lombok.AccessLevel;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class SaleResponse extends LoadKeysResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final String ticket;
    private final String authorizationCode;
    private final int amount;
    private final int sharesNumber;
    private final int sharesAmount;
    private final int last4Digits;
    private final int operationNumber;
    private final String cardType;
    private final Date accountingDate;
    private final String accountNumber;
    private final String cardBrand;
    private final Date realDate;
    private final int employeeId;
    private final int tip;

    public SaleResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        ticket = parseStringParameter(baseResponse, parameterMap, "Ticket");
        authorizationCode = parseStringParameter(baseResponse, parameterMap, "AuthorizationCode");
        amount = parseIntParameter(baseResponse, parameterMap, "Amount");
        sharesNumber = parseIntParameter(baseResponse, parameterMap, "SharesNumber");
        sharesAmount = parseIntParameter(baseResponse, parameterMap, "SharesAmount");
        last4Digits = parseIntParameter(baseResponse, parameterMap, "Last4Digits");
        operationNumber = parseIntParameter(baseResponse, parameterMap, "OperationNumber");
        cardType = parseStringParameter(baseResponse, parameterMap, "CardType");
        accountingDate = parseAccountingDate(baseResponse, parameterMap);
        accountNumber = parseStringParameter(baseResponse, parameterMap, "AccountNumber");
        cardBrand = parseStringParameter(baseResponse, parameterMap, "CardBrand");
        realDate = parseRealDate(baseResponse, parameterMap);
        employeeId = parseIntParameter(baseResponse, parameterMap, "EmployeeId");
        tip = parseIntParameter(baseResponse, parameterMap, "Tip");
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedAccountingDate = accountingDate != null ? dateFormat.format(accountingDate) : "";
        String formattedRealDate = realDate != null ? dateFormat.format(realDate) : "";
        return super.toString() + "\n" +
            "Ticket: " + ticket + "\n" +
            "AuthorizationCode Code: " + authorizationCode + "\n" +
            "Amount: " + amount + "\n" +
            "Shares Number: " + sharesNumber + "\n" +
            "Shares Amount: " + sharesAmount + "\n" +
            "Last 4 Digits: " + last4Digits + "\n" +
            "Operation Number: " + operationNumber + "\n" +
            "Card Type: " + cardType + "\n" +
            "Accounting Date: " + formattedAccountingDate + "\n" +
            "Account Number: " + accountNumber + "\n" +
            "Card Brand: " + cardBrand + "\n" +
            "Real Date: " + formattedRealDate + "\n" +
            "Employee Id: " + employeeId + "\n" +
            "Tip: " + tip;
    }

    private Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("Ticket", 4);
        baseMap.put("AuthorizationCode", 5);
        baseMap.put("Amount", 6);
        baseMap.put("SharesNumber", 7);
        baseMap.put("SharesAmount", 8);
        baseMap.put("Last4Digits", 9);
        baseMap.put("OperationNumber", 10);
        baseMap.put("CardType", 11);
        baseMap.put("AccountingDate", 12);
        baseMap.put("AccountNumber", 13);
        baseMap.put("CardBrand", 14);
        baseMap.put("RealDate", 15);
        baseMap.put("RealTime", 16);
        baseMap.put("EmployeeId", 17);
        baseMap.put("Tip", 18);
        return Collections.unmodifiableMap(baseMap);
    }
}
