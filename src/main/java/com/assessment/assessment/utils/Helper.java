package com.assessment.assessment.utils;

import com.assessment.assessment.exceptions.IncorrectFormatException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    private final static String ANY_NON_DIGIT = "\\D";

    public static boolean verifyEvent(String event) {

        String[] eventArr = event.split("\\|");
        if (eventArr.length != 5) return false;

        String transactionid = eventArr[0];
        String transactionTimeStamp = eventArr[1];
        String transactionAmount = eventArr[2];
        String cardNumber = eventArr[3];
        String cardBalance = eventArr[4];

        Pattern pattern = Pattern.compile(ANY_NON_DIGIT);
        Matcher matcher = pattern.matcher(transactionid);

        if (matcher.find()) {
            throw new IncorrectFormatException("Transaction ID has incorrect format");
        }

        try {
            LocalDateTime.parse(transactionTimeStamp);
        } catch (DateTimeParseException e) {
            throw new IncorrectFormatException("Transaction Timestamp not recognized");
        }

        try {
            Double.parseDouble(transactionAmount);
        } catch (NumberFormatException e) {
            throw new IncorrectFormatException("Transaction amount was invalid");
        }

        matcher = pattern.matcher(cardNumber);
        if (matcher.find()) {
            throw new IncorrectFormatException("Card Number has incorrect format");
        }

        try {
            Double.parseDouble(cardBalance);
        } catch (NumberFormatException e) {
            throw new IncorrectFormatException("Card Balance was invalid");
        }

        return true;
    }

}
