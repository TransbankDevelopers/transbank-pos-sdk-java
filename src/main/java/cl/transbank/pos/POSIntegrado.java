package cl.transbank.pos;

import cl.transbank.pos.exceptions.TransbankException;
import cl.transbank.pos.responses.commonResponse.LoadKeysResponse;
import cl.transbank.pos.utils.Serial;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class POSIntegrado extends Serial {
    public LoadKeysResponse loadKeys() {
        try {
            write("0800");
        } catch (TransbankException e) {
            e.printStackTrace();
        }
        LoadKeysResponse response = new LoadKeysResponse(currentResponse);
        log.debug(response.toString());
        return response;
    }
}
