package cl.transbank.pos.responses.autoservicio;

import lombok.AccessLevel;
import lombok.Getter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cl.transbank.pos.utils.ParameterParser.*;

@Getter
public class MultiCodeSaleResponse extends SaleResponse {

    @Getter(AccessLevel.NONE)
    private final Map<String, Integer> parameterMap;

    private final long commerceProviderCode;
    private final List<String> printingField;
    private final int sharesType;
    private final int sharesNumber;
    private final int sharesAmount;
    private final String sharesTypeComment;

    public MultiCodeSaleResponse(String response) {
        super(response);
        parameterMap = initializeParameterMap();

        commerceProviderCode = parseLongParameter(baseResponse, parameterMap, "CommerceCode");
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
        String formattedAccountingDate = getAccountingDate() != null ? dateFormat.format(getAccountingDate()) : "";
        String formattedRealDate = getRealDate() != null ? dateFormat.format(getRealDate()) : "";
        return  "Function:" + getFunctionCode() + "\n" +
                "Response: " + getResponseMessage() + "\n" +
                "Commerce Code: " + getCommerceCode() + "\n" +
                "Terminal Id: " + getTerminalId() + "\n" +
                "Ticket: " + getTicket() + "\n" +
                "AuthorizationCode Code: " + getAuthorizationCode() + "\n" +
                "Amount: " + getAmount() + "\n" +
                "Last 4 Digits: " + getLast4Digits() + "\n" +
                "Operation Number: " + getOperationNumber() + "\n" +
                "Card Type: " + getCardType() + "\n" +
                "Accounting Date: " + formattedAccountingDate + "\n" +
                "Account Number: " + getAccountNumber() + "\n" +
                "Card Brand: " + getCardBrand() + "\n" +
                "Real Date: " + formattedRealDate + "\n" +
                "Commerce Provider Code: " + commerceProviderCode + "\n" +
                "Printing Field: " + ((printingField.size() > 1) ? "\r\n" + String.join("\r\n", printingField) : printingField.get(0))  + "\n" +
                "Shares Type: " + sharesType + "\n" +
                "Shares Number: " + sharesNumber + "\n" +
                "Shares Amount: " + sharesAmount + "\n" +
                "Shares Type Comment: " + sharesTypeComment;
    }

    private static Map<String, Integer> initializeParameterMap() {
        Map<String, Integer> baseMap = new HashMap<>();
        baseMap.put("CommerceProviderCode", 14);
        baseMap.put("PrintingField", 16);
        baseMap.put("SharesType", 17);
        baseMap.put("SharesNumber", 18);
        baseMap.put("SharesAmount", 19);
        baseMap.put("SharesTypeComment", 20);
        return Collections.unmodifiableMap(baseMap);
    }
}
