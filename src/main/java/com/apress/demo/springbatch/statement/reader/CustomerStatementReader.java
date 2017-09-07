package com.apress.demo.springbatch.statement.reader;

import com.apress.demo.springbatch.statement.dao.TickerDao;
import com.apress.demo.springbatch.statement.domain.Customer;
import com.apress.demo.springbatch.statement.domain.Statement;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * Created by j1cheng on 2017-08-23.
 */
public class CustomerStatementReader implements ItemReader<Statement> {

    private ItemReader<Customer> customerReader;
    private TickerDao tickerDao;

    @Override
    public Statement read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        Customer customer = customerReader.read();

        if (customer == null)
         return null;
        else {
            Statement statement = new Statement();

            statement.setCustomer(customer);
            statement.setSecurityTotal(tickerDao.getTotalValueForCustomer(customer.getId()));
            statement.setStocks(tickerDao.getStocksForCustomer(customer.getId()));
            return statement;
        }
    }

    public void setCustomerReader(ItemReader<Customer> customerReader) {
        this.customerReader = customerReader;
    }

    public void setTickerDao(TickerDao tickerDao) {
        this.tickerDao = tickerDao;
    }
}
