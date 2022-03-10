package com.mindhub.homebancking.dtos;

import com.mindhub.homebancking.models.Transaction;
import com.mindhub.homebancking.models.TransactionType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class TransactionDTO {

    private long id;
    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private Double amountCurrent;

    public TransactionDTO(){

    }

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.date = transaction.getDate();
        this.amountCurrent = transaction.getAmountCurrent();
    }

    public long getId() {
        return id;
    }

    public Double getAmountCurrent() {
        return amountCurrent;
    }

    public void setAmountCurrent(Double amountCurrent) {
        this.amountCurrent = amountCurrent;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
