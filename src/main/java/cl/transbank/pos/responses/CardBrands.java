/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.transbank.pos.responses;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Get human-friendly name for Cards.
 *
 * @author pedro
 */
public class CardBrands {

    private static final Map<String, String> MAP;

    static {
        Map<String, String> baseMap = new HashMap<>();
        baseMap.put("VI", "Visa");
        baseMap.put("MC", "Master Card");
        baseMap.put("CA", "Cabal");
        baseMap.put("CR", "Credencial");
        baseMap.put("AX", "Amex");
        baseMap.put("CE", "Cerrada");
        baseMap.put("DC", "Diners");
        baseMap.put("TP", "Presto");
        baseMap.put("MG", "Magna");
        baseMap.put("TM", "Mas (Cencosud)");
        baseMap.put("RP", "Ripley");
        baseMap.put("EX", "Extra");
        baseMap.put("TC", "CMR");
        baseMap.put("DB", "Redcompra");
        MAP = Collections.unmodifiableMap(baseMap);
    }

    private CardBrands() {
    }

    /**
     * Get the human-friendly name for a Card. If it's not avalible return the
     * card code.
     *
     * @param xx Card code.
     * @return Human-friendly card brand or the card code if it's not
     * available.
     */
    public static String getCardBrand(String xx) {
        if (xx == null) {
            return "";
        }
        String brand = MAP.get(xx);
        return brand == null ? xx : brand;
    }
}
