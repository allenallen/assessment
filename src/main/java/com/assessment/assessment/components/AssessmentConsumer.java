package com.assessment.assessment.components;

import com.assessment.assessment.model.Transaction;
import com.assessment.assessment.model.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

@Component
public class AssessmentConsumer {

    private static final Logger LOGGER = Logger.getLogger(AssessmentConsumer.class.getName());

    @Autowired
    private TransactionRepository transactionRepository;

    private CountDownLatch latch = new CountDownLatch(1);

    private String payload;

    @KafkaListener(groupId = "assessmentListener", topics = AssessmentProducer.TOPIC)
    public void listen(String in) {
        LOGGER.info(String.format("Received event from topic %s - %s", AssessmentProducer.TOPIC, in));
        payload = in;
        latch.countDown();
        Transaction transaction = Transaction.newTransaction(in);
        transactionRepository.save(transaction);
    }

    public void resetLatch() {
        latch = new CountDownLatch(1);
    }

    public CountDownLatch getLatch() {
        return latch;
    }

    public String getPayload() {
        return payload;
    }
}
