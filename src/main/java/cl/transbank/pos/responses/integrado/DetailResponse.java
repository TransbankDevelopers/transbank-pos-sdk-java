package cl.transbank.pos.responses.integrado;

import lombok.AccessLevel;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class DetailResponse extends SaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> m_parameterMap = new HashMap<String, Integer>()
    {
        {
            put("Last4Digits", 7);
            put("OperationNumber", 8);
            put("CardType", 9);
            put("AccountingDate", 10);
            put("AccountNumber", 11);
            put("CardBrand", 12);
            put("RealDate", 13);
            put("RealTime", 14);
            put("EmployeeId", 15);
            put("Tip", 16);
            put("SharesAmount", 17);
            put("SharesNumber", 18);
        }
    };

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
        last4Digits = parseIntParameter(m_response, m_parameterMap, "Last4Digits");
        operationNumber = parseIntParameter(m_response, m_parameterMap, "OperationNumber");
        cardType = parseStringParameter(m_response, m_parameterMap, "CardType");
        accountingDate = parseAccountingDate(m_response, m_parameterMap);
        accountNumber = parseStringParameter(m_response, m_parameterMap, "AccountNumber");
        cardBrand = parseStringParameter(m_response, m_parameterMap, "CardBrand");
        realDate = parseRealDate(m_response, m_parameterMap);
        employeeId = parseIntParameter(m_response, m_parameterMap, "EmployeeId");
        tip = parseIntParameter(m_response, m_parameterMap, "Tip");
        sharesAmount = parseIntParameter(m_response, m_parameterMap, "SharesAmount");
        sharesNumber = parseIntParameter(m_response, m_parameterMap, "SharesNumber");
    }

    @Override
    public String toString()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String formattedAccountingDate = accountingDate != null ? dateFormat.format(accountingDate) : "";
        String formattedRealDate = realDate != null ? dateFormat.format(realDate) : "";
        return super.toString() + "\n" +
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

}
