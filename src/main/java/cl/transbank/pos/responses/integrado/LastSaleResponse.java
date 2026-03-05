package cl.transbank.pos.responses.integrado;

/**
 * Parsed response for last sale queries in POS Integrado.
 * <p>
 * Reuses the common Integrado sale fields provided by {@link BaseSaleResponse}.
 */
public class LastSaleResponse extends BaseSaleResponse {

    public LastSaleResponse(String response) {
        super(response);
    }
}
