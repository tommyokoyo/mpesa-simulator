package com.okoyo.mpesasimulator.mpesasimulator.controllers;

import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressRequest;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressResponse;
import com.okoyo.mpesasimulator.mpesasimulator.services.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mpesa-simulator/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/mpesa-express")
    public ResponseEntity<MpesaExpressResponse> mpesaExpress(@RequestBody MpesaExpressRequest mpesaExpressRequest) {
        MpesaExpressResponse mpesaExpressResponse = transactionService.msimExpressRequest(mpesaExpressRequest);
        return ResponseEntity.status(HttpStatus.OK).body(mpesaExpressResponse);
    }
}
