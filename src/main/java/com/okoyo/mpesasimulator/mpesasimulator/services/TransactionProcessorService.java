package com.okoyo.mpesasimulator.mpesasimulator.services;

import com.okoyo.mpesasimulator.mpesasimulator.components.Generator;
import com.okoyo.mpesasimulator.mpesasimulator.configurations.RabbitMQConfiguration;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressCallbackDTO;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressCallbackResponse;
import com.okoyo.mpesasimulator.mpesasimulator.dto.MpesaExpressDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionProcessorService {
    private final Generator generator;
    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessorService.class);
    private final WebClientService webClientService;


    public TransactionProcessorService(Generator generator, @Qualifier("rabbitTemplate") RabbitTemplate rabbitTemplate, WebClientService webClientService) {
        this.generator = generator;
        this.rabbitTemplate = rabbitTemplate;
        this.webClientService = webClientService;
    }

    @RabbitListener(queues = RabbitMQConfiguration.MSIM_EXPRESS_QUEUE)
    public void mpesaExpressProcessor(MpesaExpressDTO mpesaExpressDTO) {
        MpesaExpressCallbackDTO callBackMessage = new MpesaExpressCallbackDTO(mpesaExpressDTO.getMerchantRequestID(),
                mpesaExpressDTO.getCheckoutRequestID(),
                "0", "The service request is processed successfully.",
                mpesaExpressDTO.getCallBackUrl(), mpesaExpressDTO.getAmount(),
                generator.transactionRequestIdGenerator(), LocalDateTime.now());

        logger.info("{}", callBackMessage);

        try {
            // Send to callback queue
            rabbitTemplate.convertAndSend(RabbitMQConfiguration.CALLBACK_EXCHANGE, RabbitMQConfiguration.MSIM_EXPRESS_CALLBACK_QUEUE_ROUTING_KEY, callBackMessage);
            logger.info("Transaction processed: {}", mpesaExpressDTO);

        } catch (AmqpException ex) {
            logger.error("Failed to send message to RabbitMQ: {}", ex.getMessage());
            logger.debug("Callback Message: {}", callBackMessage);
        } catch (Exception ex) {
            logger.error("Unexpected error has occurred: {}", ex.getMessage());
            logger.debug("Failed Transaction: {}", callBackMessage);
        }
    }

    @RabbitListener(queues = RabbitMQConfiguration.MSIM_CALLBACK_QUEUE)
    public void mpesaExpressCallback(MpesaExpressCallbackDTO mpesaExpressCallbackDTO) {
        MpesaExpressCallbackResponse callbackResponse = webClientService.mpesaExpressCallback(mpesaExpressCallbackDTO, mpesaExpressCallbackDTO.getCallBackUrl()).block();
        assert callbackResponse != null;
        logger.info("Callback response: {}", callbackResponse.toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
