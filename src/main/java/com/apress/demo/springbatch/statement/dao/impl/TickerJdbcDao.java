package com.apress.demo.springbatch.statement.dao.impl;

import com.apress.demo.springbatch.statement.dao.TickerDao;
import com.apress.demo.springbatch.statement.domain.Ticker;
import com.apress.demo.springbatch.statement.domain.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by j1cheng on 2017-08-16.
 */
public class TickerJdbcDao extends JdbcTemplate implements TickerDao {

    private static final String FIND_BY_SYMBOL = "select * from ticker t where ticker = ?";
    private static final String SAVE_TICKER = "insert into ticker (ticker, currentPrice) values (?,?)";
    private static final String FIND_ALL = "select distinct ticker from ticker order by ticker limit ?, ?";
    private static final String TOTAL_VALUE = "select SUM(value) as totalValue " +
            "from (select ticker, qty * currentPrice as value " +
            "from (select tk.ticker, SUM(ts.qty) as qty, tk.currentPrice " +
            "from transaction ts inner join " +
            "ticker tk on ts.tickerId = tk.id inner join " +
            "account a on ts.account_id = a.id inner join " +
            "customer c on c.id = a.customer_id " +
            "where c.id = ? " +
            "group by tk.ticker, tk.currentPrice) as stocks) as total";
    private static final String STOCKS_BY_CUSTOMER = "select ticker, qty, qty * currentPrice as value " +
            "from (select tk.ticker, SUM(ts.qty) as qty, tk.currentPrice " +
            "from transaction ts inner join  " +
            "ticker tk on ts.tickerId = tk.id inner join  " +
            "account a on ts.account_id = a.id inner join " +
            "customer c on c.id = a.customer_id " +
            "where c.id = ? " +
            "group by tk.ticker, tk.currentPrice) as stocks";

    @Override
    public Ticker findTickerBySymbol(String symbol) {
        List<Ticker> tickers = query(FIND_BY_SYMBOL, new Object[]{symbol}, new RowMapper<Ticker>() {
            @Override
            public Ticker mapRow(ResultSet rs, int i) throws SQLException {
                Ticker ticker = new Ticker();

                ticker.setId(rs.getLong("id"));
                ticker.setPrice(rs.getBigDecimal("currentPrice"));
                ticker.setTicker(rs.getString("ticker"));

                return ticker;
            }
        });

        if (tickers != null && tickers.size() > 0) {
            return tickers.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void saveTicker(Ticker ticker) {
        update(SAVE_TICKER, new Object[] {ticker.getTicker(), ticker.getPrice()});
    }

    @Override
    public List<String> getTickersPaged(int page, int pageSize) {
        return queryForList(FIND_ALL, new Object[]{(page * pageSize)}, String.class);
    }

    @Override
    public BigDecimal getTotalValueForCustomer(long id) {
        BigDecimal result = (BigDecimal) queryForObject(TOTAL_VALUE, new Object[] {id}, BigDecimal.class);
        if (result == null)
            result = new BigDecimal(0);

        return result;
    }

    @Override
    public List<Transaction> getStocksForCustomer(long id) {
        return query(STOCKS_BY_CUSTOMER, new Object[]{id}, new RowMapper<Transaction>() {
            @Override
            public Transaction mapRow(ResultSet resultSet, int i) throws SQLException {
                Transaction transaction = new Transaction();

                transaction.setDollarAmount(resultSet.getBigDecimal("value"));
                transaction.setQuantity(resultSet.getLong("qty"));
                transaction.setTicker(resultSet.getString("ticker"));

                return transaction;
            }
        });
    }
}
