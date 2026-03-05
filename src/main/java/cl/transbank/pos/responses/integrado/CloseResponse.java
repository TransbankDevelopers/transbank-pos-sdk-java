package cl.transbank.pos.responses.integrado;

import cl.transbank.pos.responses.common.LoadKeysResponse;

/**
 * Parsed response for close operations in POS Integrado.
 * <p>
 * Reuses the common fields provided by {@link LoadKeysResponse}.
 */
public class CloseResponse extends LoadKeysResponse {

    public CloseResponse(String response) {
        super(response);
    }
}
