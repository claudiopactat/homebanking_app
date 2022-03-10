package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;

public class ClientLoanDTO {

    private long id;
    private long loanId;
    private String name;
    private Double amount;
    private Integer payments;

    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan){

        this.id = clientLoan.getId();
        this.loanId = clientLoan.getId();
        this.name = clientLoan.getName();
        this.amount = clientLoan.getAmount();
        this.payments = clientLoan.getPayments();

    }

    public long getId() {
        return id;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }
}
