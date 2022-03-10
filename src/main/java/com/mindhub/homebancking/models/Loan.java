package com.mindhub.homebancking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;
    private Double maxAmount;

    @ElementCollection
    @Column(name="payment")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>();

    private Double interes;


    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments, Double interes) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.interes = interes;
    }

    /*
    public Loan(String name, Double maxAmount, List<Integer> payments, Set<ClientLoan> clientLoans) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.clientLoans = clientLoans;
    }
    */


    public Double getInteres() {
        return interes;
    }

    public void setInteres(Double interes) {
        this.interes = interes;
    }

    @JsonIgnore
    public List<Client> getClients() {
        return clientLoans.stream().map(sub -> sub.getClient()).collect(toList());
    }

    public long getId() {
        return id;
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
