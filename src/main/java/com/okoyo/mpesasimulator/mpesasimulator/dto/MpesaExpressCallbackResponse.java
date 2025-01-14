package com.okoyo.mpesasimulator.mpesasimulator.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MpesaExpressCallbackResponse {
    public MpesaExpressCallbackResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    @JsonProperty("status")
    private String status;
    @JsonProperty("message")
    private String message;

    @Override
    public String toString() {
        return "MpesaExpressCallbackResponse [status=" + status + ", message=" + message + "]";
    }
}
