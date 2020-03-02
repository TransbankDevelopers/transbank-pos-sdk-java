package cl.transbank.pos.responses;

import cl.transbank.pos.utils.BaseResponse;
import cl.transbank.pos.utils.TotalsCResponse;

public class TotalsResponse extends TotalsCResponse {

    public TotalsCResponse response;
    public TotalsCResponse getResponse() {
        return response;
    }

    public TotalsResponse(TotalsCResponse cresponse) {
        response = cresponse;
    }

    public boolean isSuccessful() {
        return response.getResponseCode() == 0;
    }

    @Override
    public String toString() {
        return "Function: " + this.getFunction() + "\n" +
                "Response: " + this.getResponse() + "\n" +
                "Success?: " + this.isSuccessful() + "\n" +
                "TX Count: " + this.getTxCount() + "\n" +
                "TX Total: " + this.getTxTotal();
    }

}
