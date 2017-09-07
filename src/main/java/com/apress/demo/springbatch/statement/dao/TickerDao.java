package com.apress.demo.springbatch.statement.dao;

import com.apress.demo.springbatch.statement.domain.Ticker;
import com.apress.demo.springbatch.statement.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by j1cheng on 2017-08-15.
 */
public interface TickerDao {
    Ticker findTickerBySymbol(String symbol);
    void saveTicker(Ticker ticker);
    List<String> getTickersPaged(int page, int pageSize);
    BigDecimal getTotalValueForCustomer(long id);
    List<Transaction> getStocksForCustomer(long id);
}
