package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private TransactionType type;
    private Double amount;
    private String description;
    private LocalDateTime date;
    private Double amountCurrent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="transaction")
    private Account account;

    public Transaction(){

    }

    public Transaction(TransactionType type, Double amount, String description, LocalDateTime date, Account account, Double amountCurrent) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.account = account;
        this.amountCurrent = amountCurrent;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
