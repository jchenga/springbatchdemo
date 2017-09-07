package com.apress.demo.springbatch.statement.reader;

import com.apress.demo.springbatch.statement.domain.AccountTransaction;
import com.apress.demo.springbatch.statement.domain.PricingTier;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by j1cheng on 2017-08-22.
 */
public class AccountTransactionRowMapper implements RowMapper<AccountTransaction> {
    @Override
    public AccountTransaction mapRow(ResultSet resultSet, int i) throws SQLException {
        AccountTransaction accountTransaction = new AccountTransaction();

        accountTransaction.setAccountId(resultSet.getLong("accountId"));
        accountTransaction.setAccountNumber(resultSet.getString("accountNumber"));
        accountTransaction.setId(resultSet.getLong("transactionId"));
        accountTransaction.setQuantity(resultSet.getLong("qty"));
        accountTransaction.setTicker(resultSet.getString("ticker"));
        accountTransaction.setTier(PricingTier.convert(resultSet.getInt("tier")));
        accountTransaction.setTradeTimestamp(resultSet.getDate("executedTime"));
        accountTransaction.setPrice(resultSet.getBigDecimal("dollarAmount"));
        return accountTransaction;
    }
}
