package com.merchant.transactionservice.model;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Component
public class TransactionDTO {

    private String uuid;
    private LocalDateTime creationDate;
    private TransactionStatus status;
    private BigDecimal amount;
    private String currency;
    private String description;


    public TransactionDTO() {}

    public TransactionDTO(String uuid, LocalDateTime creationDate, TransactionStatus status, BigDecimal amount, String currency, String description) {
        this.uuid = uuid;
        this.creationDate = creationDate;
        this.status = status;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
