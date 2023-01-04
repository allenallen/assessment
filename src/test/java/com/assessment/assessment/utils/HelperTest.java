package com.assessment.assessment.utils;

import com.assessment.assessment.exceptions.IncorrectFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelperTest {

    @Test
    public void verifiedEvent() {
        String event = "10000001|2022-11-05T09:25:00|10.25|2000000001|110.25";
        assertTrue(Helper.verifyEvent(event), "Error in verification");
    }

    @Test
    public void eventIncorrectTransactionIdFormat() {
        String event = "1000000TEST|2022-11-05T09:25:00|10.25|2000000001|110.25";
        assertThrows(IncorrectFormatException.class, () -> Helper.verifyEvent(event));
    }

    @Test
    public void eventUnparseableTimeStamp() {
        String event = "100000001|2022-9999-05T09:25:00|10.25|2000000001|110.25";
        assertThrows(IncorrectFormatException.class, () -> Helper.verifyEvent(event));
    }

    @Test
    public void eventInvalidAmount() {
        String event = "100000001|2022-11-05T09:25:00|10e1s.25|2000000001|110.25";
        assertThrows(IncorrectFormatException.class, () -> Helper.verifyEvent(event));
    }

    @Test
    public void eventInvalidCardNumber() {
        String event = "100000001|2022-11-05T09:25:00|10.25|2000000001+|110.25";
        assertThrows(IncorrectFormatException.class, () -> Helper.verifyEvent(event));
    }

    @Test
    public void eventInvalidCardBalance() {
        String event = "100000001|2022-11-05T09:25:00|10.25|2000000001|110AA.25";
        assertThrows(IncorrectFormatException.class, () -> Helper.verifyEvent(event));
    }
}
