package com.okoyo.mpesasimulator.mpesasimulator.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MpesaExpressResponse {
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResponseCode;
    private String ResponseDescription;
    private String CustomerMessage;

    public MpesaExpressResponse(String merchantRequestID, String checkoutRequestID, String responseCode, String responseDescription, String customerMessage) {
        MerchantRequestID = merchantRequestID;
        CheckoutRequestID = checkoutRequestID;
        ResponseCode = responseCode;
        ResponseDescription = responseDescription;
        CustomerMessage = customerMessage;
    }
}
