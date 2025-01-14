package com.okoyo.mpesasimulator.mpesasimulator.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class MpesaExpressCallbackDTO {

    private String MerchantRequestID;
    private String CheckoutRequestID;
    private String ResultCode;
    private String ResultDesc;
    private String CallBackUrl;
    private Map<String, Object> Callbackmetadata;

    public MpesaExpressCallbackDTO(String merchantRequestID, String checkoutRequestID, String resultCode, String resultDesc, String callBackUrl, Double amount, String mpesaReceiptNumber, LocalDateTime transactionDateTime) {
        this.MerchantRequestID = merchantRequestID;
        this.CheckoutRequestID = checkoutRequestID;
        this.ResultCode = resultCode;
        this.ResultDesc = resultDesc;
        this.CallBackUrl = callBackUrl;

        List<Map<String, Object>> items = new ArrayList<>();

        Map<String, Object> amountItem = new HashMap<>();
        amountItem.put("Name", "Amount");
        amountItem.put("Value", amount);
        items.add(amountItem);

        Map<String, Object> receiptNumberItem = new HashMap<>();
        receiptNumberItem.put("Name", "MpesaReceiptNumber");
        receiptNumberItem.put("Value", mpesaReceiptNumber);
        items.add(receiptNumberItem);

        Map<String, Object> transactionDateItem = new HashMap<>();
        transactionDateItem.put("Name", "TransactionDate");
        transactionDateItem.put("Value", transactionDateTime);
        items.add(transactionDateItem);

        this.Callbackmetadata = new HashMap<>();
        this.Callbackmetadata.put("Item", items);
    }

    @Override
    public String toString() {
        return "MpesaExpressCallbackDTO [MerchantRequestID=" + MerchantRequestID
                + ", CheckoutRequestID=" + CheckoutRequestID + ", ResultCode=" + ResultCode
                + ", ResultDesc=" + ResultDesc + ", CallBackUrl=" + CallBackUrl + ", Callbackmetadata="
                + Callbackmetadata + "]";
    }

}
