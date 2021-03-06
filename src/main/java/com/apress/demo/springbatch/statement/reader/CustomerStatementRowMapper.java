package com.apress.demo.springbatch.statement.reader;

import com.apress.demo.springbatch.statement.domain.*;
import com.apress.demo.springbatch.statement.domain.Account;
import com.apress.demo.springbatch.statement.domain.PricingTier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by j1cheng on 2017-09-06.
 */
public class CustomerStatementRowMapper implements RowMapper<Customer> {
    @Override
    public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
        Customer customer = new Customer();

        customer.setFirstName(resultSet.getString("firstName"));
        customer.setLastName(resultSet.getString("lastName"));
        customer.setId(resultSet.getLong("customer_id"));

        customer.setAccount(buildAccount(resultSet, customer));
        customer.setAddress(buildAddress(resultSet));

        return customer;
    }

    private Account buildAccount(ResultSet resultSet, Customer customer) throws SQLException {
        Account account = new Account();

        account.setAccountNumber(resultSet.getString("accountNumber"));
        account.setCashBalance(resultSet.getBigDecimal("cashBalance"));
        account.setCust(customer);
        account.setTier(PricingTier.convert(resultSet.getInt("tier")));

        return account;
    }

    private Address buildAddress(ResultSet resultSet) throws SQLException {
        Address address = new Address();

        address.setAddress1(resultSet.getString(""));
        address.setCity(resultSet.getString("city"));
        address.setState(resultSet.getString("state"));
        address.setZip(resultSet.getString("zip"));

        return address;
    }
}
