package com.apress.demo.springbatch.statement.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by j1cheng on 2017-08-22.
 */
public class Statement {
    private Customer customer;
    private BigDecimal securityTotal;
    private List<Transaction> stocks;

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public BigDecimal getSecurityTotal() {
        return securityTotal;
    }
    public void setSecurityTotal(BigDecimal securityTotal) {
        this.securityTotal = securityTotal;
    }
    public List<Transaction> getStocks() {
        return stocks;
    }
    public void setStocks(List<Transaction> stocks) {
        this.stocks = stocks;
    }
}
