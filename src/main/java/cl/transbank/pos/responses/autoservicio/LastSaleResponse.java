package cl.transbank.pos.responses.autoservicio;

/**
 * Parsed response for last sale queries in POS Autoservicio.
 * <p>
 * Reuses the same sale data structure provided by {@link SaleResponse}.
 */
public class LastSaleResponse extends SaleResponse {

    public LastSaleResponse(String response) {
        super(response);
    }
}
