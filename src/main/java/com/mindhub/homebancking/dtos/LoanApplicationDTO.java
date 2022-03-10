package com.mindhub.homebancking.dtos;

public class LoanApplicationDTO {

    private String name;
    private Double amount;
    private Integer payments;
    private String number;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(String name, Double amount, Integer payments, String number) {
        this.name = name;
        this.amount = amount;
        this.payments = payments;
        this.number = number;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
