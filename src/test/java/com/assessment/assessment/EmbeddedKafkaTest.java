package com.assessment.assessment;

import com.assessment.assessment.components.AssessmentConsumer;
import com.assessment.assessment.components.AssessmentProducer;
import com.assessment.assessment.exceptions.IncorrectFormatException;
import com.assessment.assessment.model.Transaction;
import com.assessment.assessment.model.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {
        "listeners=PLAINTEXT://localhost:9092",
        "port=9092"
})
public class EmbeddedKafkaTest {

    @Autowired
    private AssessmentConsumer assessmentConsumer;

    @Autowired
    private AssessmentProducer assessmentProducer;

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    public void successSendAndReceive() throws Exception {
        String event = "10000001|2022-11-05T09:25:00|10.25|2000000001|110.25";
        assessmentProducer.send(new String[]{event});

        boolean messageConsumed = assessmentConsumer.getLatch().await(10, TimeUnit.SECONDS);
        assertTrue(messageConsumed);

        List<Transaction> transactions = transactionRepository.findAll();
        assertTrue(transactions.size() > 0);

        assertEquals(10000001, transactions.get(0).getTransactionId());
        assertEquals(event, assessmentConsumer.getPayload());
    }

    @Test
    public void invalidEvent_producerWillNotSend_throwsIncorrectFormatException() throws Exception {
        String event = "10000001TEST|2022-11-05T09:25:00|10.25|2000000001|110.25";

        assertThrows(IncorrectFormatException.class, () -> assessmentProducer.send(new String[]{event}));
    }

}
