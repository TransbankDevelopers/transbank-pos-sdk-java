package cl.transbank.pos.responses.integradoResponses;

import cl.transbank.pos.responses.commonResponses.LoadKeysResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

public class SaleResponse extends LoadKeysResponse {

    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("Ticket", 4);
            put("AuthorizationCode", 5);
            put("Amount", 6);
            put("SharesNumber", 7);
            put("SharesAmount", 8);
            put("Last4Digits", 9);
            put("OperationNumber", 10);
            put("CardType", 11);
            put("AccountingDate", 12);
            put("AccountNumber", 13);
            put("CardBrand", 14);
            put("RealDate", 15);
            put("RealTime", 16);
            put("EmployeeId", 17);
            put("Tip", 18);

        }
    };

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
        ticket = parseStringParameter(m_response, m_parameterMap, "Ticket");
        authorizationCode = parseStringParameter(m_response, m_parameterMap, "AuthorizationCode");
        amount = parseIntParameter(m_response, m_parameterMap, "Amount");
        sharesNumber = parseIntParameter(m_response, m_parameterMap, "SharesNumber");
        sharesAmount = parseIntParameter(m_response, m_parameterMap, "SharesAmount");
        last4Digits = parseIntParameter(m_response, m_parameterMap, "Last4Digits");
        operationNumber = parseIntParameter(m_response, m_parameterMap, "OperationNumber");
        cardType = parseStringParameter(m_response, m_parameterMap, "CardType");
        accountingDate = parseAccountingDate(m_response, m_parameterMap);
        accountNumber = parseStringParameter(m_response, m_parameterMap, "AccountNumber");
        cardBrand = parseStringParameter(m_response, m_parameterMap, "CardBrand");
        realDate = parseRealDate(m_response, m_parameterMap);
        employeeId = parseIntParameter(m_response, m_parameterMap, "EmployeeId");
        tip = parseIntParameter(m_response, m_parameterMap, "Tip");
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formatedAccountingDate = accountingDate != null ? dateFormat.format(accountingDate) : "";
        String formatedRealDate = realDate != null ? dateFormat.format(realDate) : "";
        return super.toString() + "\n" +
            "Ticket: " + ticket + "\n" +
            "AuthorizationCode Code: " + authorizationCode + "\n" +
            "Amount: " + amount + "\n" +
            "Shares Number: " + sharesNumber + "\n" +
            "Shares Amount: " + sharesAmount + "\n" +
            "Last 4 Digits: " + last4Digits + "\n" +
            "Operation Number: " + operationNumber + "\n" +
            "Card Type: " + cardType + "\n" +
            "Accounting Date: " + formatedAccountingDate + "\n" +
            "Account Number: " + accountNumber + "\n" +
            "Card Brand: " + cardBrand + "\n" +
            "Real Date: " + formatedRealDate + "\n" +
            "Employee Id: " + employeeId + "\n" +
            "Tip: " + tip;
    }
}
