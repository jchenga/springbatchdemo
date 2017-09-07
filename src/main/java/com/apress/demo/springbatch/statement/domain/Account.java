package com.apress.demo.springbatch.statement.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by j1cheng on 2017-08-15.
 */
public class Account {
    private long id = -1;
    private String accountNumber;
    private Customer cust;
    private BigDecimal cashBalance;
    private PricingTier tier;
    private List<Transaction> transactions;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Customer getCust() {
        return cust;
    }
    public void setCust(Customer cust) {
        this.cust = cust;
    }
    public BigDecimal getCashBalance() {
        return cashBalance;
    }
    public void setCashBalance(BigDecimal cashBalance) {
        this.cashBalance = cashBalance;
    }
    public List<Transaction> getTransactions() {
        return transactions;
    }
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
    public PricingTier getTier() {
        return tier;
    }
    public void setTier(PricingTier tier) {
        this.tier = tier;
    }
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
