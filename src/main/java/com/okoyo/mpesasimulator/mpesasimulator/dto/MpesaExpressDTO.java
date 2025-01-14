package com.okoyo.mpesasimulator.mpesasimulator.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MpesaExpressDTO {
    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String TransactionType;
    private String PartyA;
    private String PartyB;
    private String PhoneNumber;
    private Double Amount;
    private String CallBackUrl;
    private String AccountReference;
    private String TransactionDescription;

    public MpesaExpressDTO(String merchantRequestID, String checkoutRequestID, String partyA, String partyB, String transactionType, String phoneNumber, Double amount, String callBackUrl, String accountReference, String transactionDescription) {
        MerchantRequestID = merchantRequestID;
        CheckoutRequestID = checkoutRequestID;
        PartyA = partyA;
        TransactionType = transactionType;
        PartyB = partyB;
        PhoneNumber = phoneNumber;
        Amount = amount;
        CallBackUrl = callBackUrl;
        AccountReference = accountReference;
        TransactionDescription = transactionDescription;
    }

    @Override
    public String toString() {
        return "MpesaExpressDTO [MerchantRequestID=" + MerchantRequestID
                + ", CheckoutRequestID=" + CheckoutRequestID + ", TransactionType=" + TransactionType
                + ", PartyA=" + PartyA + ", PartyB=" + PartyB + ", PhoneNumber=" + PhoneNumber
                + ", Amount=" + Amount + ", CallBackUrl=" + CallBackUrl + ", AccountReference=" + AccountReference
                + ", TransactionDescription=" + TransactionDescription + "]";
    }

}
