package cl.transbank.pos.responses.autoservicio;

import cl.transbank.pos.responses.common.LoadKeysResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

public class SaleResponse extends LoadKeysResponse {

    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("Ticket", 4);
            put("AuthorizationCode", 5);
            put("Amount", 6);
            put("Last4Digits", 7);
            put("OperationNumber", 8);
            put("CardType", 9);
            put("AccountingDate", 10);
            put("AccountNumber", 11);
            put("CardBrand", 12);
            put("RealDate", 13);
            put("RealTime", 14);
            put("PrintingField", 15);
            put("SharesType", 16);
            put("SharesNumber", 17);
            put("SharesAmount", 18);
            put("SharesTypeComment", 19);
        }
    };

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
        ticket = parseStringParameter(m_response, m_parameterMap, "Ticket");
        authorizationCode = parseStringParameter(m_response, m_parameterMap, "AuthorizationCode");
        amount = parseIntParameter(m_response, m_parameterMap, "Amount");
        last4Digits = parseIntParameter(m_response, m_parameterMap, "Last4Digits");
        operationNumber = parseIntParameter(m_response, m_parameterMap, "OperationNumber");
        cardType = parseStringParameter(m_response, m_parameterMap, "CardType");
        accountingDate = parseAccountingDate(m_response, m_parameterMap);
        accountNumber = parseStringParameter(m_response, m_parameterMap, "AccountNumber");
        cardBrand = parseStringParameter(m_response, m_parameterMap, "CardBrand");
        realDate = parseRealDate(m_response, m_parameterMap);
        printingField = parsePrintingField(m_response, m_parameterMap);
        sharesType = parseIntParameter(m_response, m_parameterMap, "SharesType");
        sharesNumber = parseIntParameter(m_response, m_parameterMap, "SharesNumber");
        sharesAmount = parseIntParameter(m_response, m_parameterMap, "SharesAmount");
        sharesTypeComment = parseStringParameter(m_response, m_parameterMap, "SharesTypeComment");
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
}
