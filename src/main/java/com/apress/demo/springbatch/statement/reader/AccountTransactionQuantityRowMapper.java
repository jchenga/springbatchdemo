package com.apress.demo.springbatch.statement.reader;

import com.apress.demo.springbatch.statement.domain.AccountTransctionQuantity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by j1cheng on 2017-08-18.
 */
public class AccountTransactionQuantityRowMapper implements RowMapper<AccountTransctionQuantity> {

    @Override
    public AccountTransctionQuantity mapRow(ResultSet resultSet, int i) throws SQLException {
        AccountTransctionQuantity qty = new AccountTransctionQuantity();

        qty.setAccountNumber(resultSet.getString("accountNumber"));
        qty.setTransactionCount(resultSet.getLong("qty"));

        return qty;
    }
}
