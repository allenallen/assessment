package com.assessment.assessment.model;

import com.assessment.assessment.utils.Helper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private long transactionId;

    private LocalDateTime transactionTimeStamp;

    private Double transactionAmount;

    private String cardNumber;

    private Double cardBalance;

    public Transaction(long transactionId, LocalDateTime transactionTimeStamp, Double transactionAmount, String cardNumber, Double cardBalance) {
        this.transactionId = transactionId;
        this.transactionTimeStamp = transactionTimeStamp;
        this.transactionAmount = transactionAmount;
        this.cardNumber = cardNumber;
        this.cardBalance = cardBalance;
    }

    public static Transaction newTransaction(String event) {
        Helper.verifyEvent(event);
        String[] eventParsed = event.split("\\|");
        return new Transaction(Long.parseLong(eventParsed[0]), LocalDateTime.parse(eventParsed[1]), Double.valueOf(eventParsed[2]), eventParsed[3], Double.valueOf(eventParsed[4]));
    }

    public long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    public LocalDateTime getTransactionTimeStamp() {
        return transactionTimeStamp;
    }

    public void setTransactionTimeStamp(LocalDateTime transactionTimeStamp) {
        this.transactionTimeStamp = transactionTimeStamp;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Double getCardBalance() {
        return cardBalance;
    }

    public void setCardBalance(Double cardBalance) {
        this.cardBalance = cardBalance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    protected Transaction() {
    }
}
