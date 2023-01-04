package com.assessment.assessment.components;

import com.assessment.assessment.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.logging.Logger;

@Component
public class AssessmentProducer {

    private static final Logger LOGGER = Logger.getLogger(AssessmentProducer.class.getName());

    public static final String TOPIC = "assessment";

    @Autowired
    private KafkaTemplate<String, String> template;

    public void send(String[] events) {
        LOGGER.info(String.format("Sending event(s) to topic %s - %s", TOPIC, Arrays.toString(events)));
        for (String event : events) {
            Helper.verifyEvent(event);
            template.send(TOPIC, event);
        }
    }
}
