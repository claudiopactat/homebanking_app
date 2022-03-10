package com.mindhub.homebancking.dtos;

import com.mindhub.homebancking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {

    private long id;
    private String name;
    private Double maxAmount;
    private List<Integer> payments = new ArrayList<>();
    private Double interes;

    public LoanDTO() {
    }

    public LoanDTO(Loan loan) {
        this.id = loan.getId();
        this.name = loan.getName();
        this.maxAmount = loan.getMaxAmount();
        this.payments = loan.getPayments();
        this.interes = loan.getInteres();
    }

    public long getId() {
        return id;
    }

    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }
}
