package com.apress.demo.springbatch.statement.dao;

import com.apress.demo.springbatch.statement.domain.Customer;

/**
 * Created by j1cheng on 2017-08-15.
 */
public interface CustomerDao {
    Customer findCustomerByTaxId(String taxId);
    void saveCustomer(Customer customer);
}
