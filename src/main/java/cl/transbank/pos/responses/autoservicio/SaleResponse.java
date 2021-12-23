package cl.transbank.pos.responses.autoservicio;

import cl.transbank.pos.responses.common.LoadKeysResponse;
import lombok.AccessLevel;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class SaleResponse extends LoadKeysResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final String ticket;
    private final String authorizationCode;
    private final int amount;
    private final int last4Digits;
    private final int operationNumber;
    private final String cardType;
    private final Date accountingDate;
    private final String accountNumber;
    private final String cardBrand;
    private final Date realDate;
    private final List<String> printingField;
    private final int sharesType;
    private final int sharesNumber;
    private final int sharesAmount;
    private final String sharesTypeComment;

    public SaleResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        ticket = parseStringParameter(baseResponse, parameterMap, "Ticket");
        authorizationCode = parseStringParameter(baseResponse, parameterMap, "AuthorizationCode");
        amount = parseIntParameter(baseResponse, parameterMap, "Amount");
        last4Digits = parseIntParameter(baseResponse, parameterMap, "Last4Digits");
        operationNumber = parseIntParameter(baseResponse, parameterMap, "OperationNumber");
        cardType = parseStringParameter(baseResponse, parameterMap, "CardType");
        accountingDate = parseAccountingDate(baseResponse, parameterMap);
        accountNumber = parseStringParameter(baseResponse, parameterMap, "AccountNumber");
        cardBrand = parseStringParameter(baseResponse, parameterMap, "CardBrand");
        realDate = parseRealDate(baseResponse, parameterMap);
        printingField = parsePrintingField(baseResponse, parameterMap);
        sharesType = parseIntParameter(baseResponse, parameterMap, "SharesType");
        sharesNumber = parseIntParameter(baseResponse, parameterMap, "SharesNumber");
        sharesAmount = parseIntParameter(baseResponse, parameterMap, "SharesAmount");
        sharesTypeComment = parseStringParameter(baseResponse, parameterMap, "SharesTypeComment");
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
            "Last 4 Digits: " + last4Digits + "\n" +
            "Operation Number: " + operationNumber + "\n" +
            "Card Type: " + cardType + "\n" +
            "Accounting Date: " + formattedAccountingDate + "\n" +
            "Account Number: " + accountNumber + "\n" +
            "Card Brand: " + cardBrand + "\n" +
            "Real Date: " + formattedRealDate + "\n" +
            "Printing Field: " + ((printingField.size() > 1) ? "\r\n" + String.join("\r\n", printingField) : printingField.get(0))  + "\n" +
            "Shares Type: " + sharesType + "\n" +
            "Shares Number: " + sharesNumber + "\n" +
            "Shares Amount: " + sharesAmount + "\n" +
            "Shares Type Comment: " + sharesTypeComment;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("Ticket", 4);
        baseMap.put("AuthorizationCode", 5);
        baseMap.put("Amount", 6);
        baseMap.put("Last4Digits", 7);
        baseMap.put("OperationNumber", 8);
        baseMap.put("CardType", 9);
        baseMap.put("AccountingDate", 10);
        baseMap.put("AccountNumber", 11);
        baseMap.put("CardBrand", 12);
        baseMap.put("RealDate", 13);
        baseMap.put("RealTime", 14);
        baseMap.put("PrintingField", 15);
        baseMap.put("SharesType", 16);
        baseMap.put("SharesNumber", 17);
        baseMap.put("SharesAmount", 18);
        baseMap.put("SharesTypeComment", 19);
        return Collections.unmodifiableMap(baseMap);
    }
}
