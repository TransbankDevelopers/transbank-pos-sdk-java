/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.transbank.pos.responses;

/**
 * Common interface for all Responses
 * @author pedro
 */
public interface Response {

    boolean isSuccessful();

    int getFunctionCode();

    int getResponseCode();

    String getResponseMessage();
}
