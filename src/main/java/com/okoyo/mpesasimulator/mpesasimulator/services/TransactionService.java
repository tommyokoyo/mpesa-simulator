package com.okoyo.mpesasimulator.mpesasimulator.services;

import com.okoyo.mpesasimulator.mpesasimulator.components.Generator;
import com.okoyo.mpesasimulator.mpesasimulator.configurations.RabbitMQConfiguration;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressDTO;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressRequest;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final RabbitTemplate rabbitTemplate;
    private final Generator generator;
    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(RabbitTemplate rabbitTemplate, Generator generator) {
        this.rabbitTemplate = rabbitTemplate;
        this.generator = generator;
    }

    public MpesaExpressResponse msimExpressRequest(MpesaExpressRequest mpesaExpressRequest) {
        // write transaction to queue send response
        MpesaExpressDTO transactionMessage = new MpesaExpressDTO(generator.MerchantIdGenerator(), generator.CheckoutRequestIdGenerator(),
                mpesaExpressRequest.getPartyA(), mpesaExpressRequest.getPartyB(), mpesaExpressRequest.getTransactionType(),
                mpesaExpressRequest.getPhoneNumber(), mpesaExpressRequest.getAmount(), mpesaExpressRequest.getCallBackUrl(),
                mpesaExpressRequest.getAccountReference(), mpesaExpressRequest.getTransactionDescription());

        try {
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.TRANSACTION_EXCHANGE, RabbitMQConfiguration.MSIM_EXPRESS_QUEUE_ROUTING_KEY, transactionMessage);
            logger.info("Transaction added to queue: {}", transactionMessage);
            return new MpesaExpressResponse(transactionMessage.getMerchantRequestID(), transactionMessage.getCheckoutRequestID(),
                    "0", "The service request has been accepted successfully.", "Success. Request accepted for processing.");

        } catch (AmqpException ex) {
            logger.error("Failed to send message to RabbitMQ: {}", ex.getMessage());
            logger.debug("Transaction Message: {}", transactionMessage);
            return new MpesaExpressResponse(transactionMessage.getMerchantRequestID(), transactionMessage.getCheckoutRequestID(),
                    "1", "The service request has failed", "Failed. Could not process request.");
        } catch (Exception ex) {
            logger.error("Unexpected error has occurred: {}", ex.getMessage());
            logger.debug("Failed Transaction: {}", transactionMessage);
            return new MpesaExpressResponse(transactionMessage.getMerchantRequestID(), transactionMessage.getCheckoutRequestID(),
                    "1", "The service request has failed", "Failed. Could not process request.");

        }
    }
}
