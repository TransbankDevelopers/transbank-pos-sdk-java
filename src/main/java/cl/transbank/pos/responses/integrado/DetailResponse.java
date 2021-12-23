package cl.transbank.pos.responses.integrado;

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
public class DetailResponse extends SaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final int last4Digits;
    private final int operationNumber;
    private final String cardType;
    private final Date accountingDate;
    private final String accountNumber;
    private final String cardBrand;
    private final Date realDate;
    private final int employeeId;
    private final int tip;
    private final int sharesAmount;
    private final int sharesNumber;

    public DetailResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        last4Digits = parseIntParameter(baseResponse, parameterMap, "Last4Digits");
        operationNumber = parseIntParameter(baseResponse, parameterMap, "OperationNumber");
        cardType = parseStringParameter(baseResponse, parameterMap, "CardType");
        accountingDate = parseAccountingDate(baseResponse, parameterMap);
        accountNumber = parseStringParameter(baseResponse, parameterMap, "AccountNumber");
        cardBrand = parseStringParameter(baseResponse, parameterMap, "CardBrand");
        realDate = parseRealDate(baseResponse, parameterMap);
        employeeId = parseIntParameter(baseResponse, parameterMap, "EmployeeId");
        tip = parseIntParameter(baseResponse, parameterMap, "Tip");
        sharesAmount = parseIntParameter(baseResponse, parameterMap, "SharesAmount");
        sharesNumber = parseIntParameter(baseResponse, parameterMap, "SharesNumber");
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedAccountingDate = accountingDate != null ? dateFormat.format(accountingDate) : "";
        String formattedRealDate = realDate != null ? dateFormat.format(realDate) : "";
        return "\nFunction: " + getFunctionCode() + "\n" +
            "Response code: " + getResponseCode() + "\n" +
            "Response message: " + getResponseMessage() + "\n" +
            "Success?: " + getSuccess() + "\n" +
            "Commerce Code: " + getCommerceCode() + "\n" +
            "Terminal Id: " + getTerminalId() + "\n" +
            "Ticket: " + getTicket() + "\n" +
            "AuthorizationCode Code: " + getAuthorizationCode() + "\n" +
            "Amount: " + getAmount() + "\n" +
            "Last 4 Digits: " + last4Digits + "\n" +
            "Operation Number: " + operationNumber + "\n" +
            "Card Type: " + cardType + "\n" +
            "Accounting Date: " + formattedAccountingDate + "\n" +
            "Account Number: " + accountNumber + "\n" +
            "Card Brand: " + cardBrand + "\n" +
            "Real Date: " + formattedRealDate + "\n" +
            "Employee Id: " + employeeId + "\n" +
            "Tip: " + tip + "\n" +
            "Shares Amount: " + sharesAmount + "\n" +
            "Shares Number: " + sharesNumber;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("Last4Digits", 7);
        baseMap.put("OperationNumber", 8);
        baseMap.put("CardType", 9);
        baseMap.put("AccountingDate", 10);
        baseMap.put("AccountNumber", 11);
        baseMap.put("CardBrand", 12);
        baseMap.put("RealDate", 13);
        baseMap.put("RealTime", 14);
        baseMap.put("EmployeeId", 15);
        baseMap.put("Tip", 16);
        baseMap.put("SharesAmount", 17);
        baseMap.put("SharesNumber", 18);
        return Collections.unmodifiableMap(baseMap);
    }

}
