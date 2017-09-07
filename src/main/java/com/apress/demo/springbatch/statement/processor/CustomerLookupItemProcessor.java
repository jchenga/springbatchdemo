package com.apress.demo.springbatch.statement.processor;

import com.apress.demo.springbatch.statement.InvalidItemException;
import com.apress.demo.springbatch.statement.dao.AccountDao;
import com.apress.demo.springbatch.statement.dao.CustomerDao;
import com.apress.demo.springbatch.statement.dao.TickerDao;
import com.apress.demo.springbatch.statement.domain.*;
import com.bbyc.demo.springbatch.statement.domain.*;
import org.springframework.batch.item.ItemProcessor;

/**
 * Created by j1cheng on 2017-08-15.
 */
public class CustomerLookupItemProcessor implements ItemProcessor<Object, Object> {

    private CustomerDao customerDao;
    private TickerDao tickerDao;
    private AccountDao accountDao;

    @Override
    public Object process(Object curItem) throws Exception {
        if (curItem instanceof Customer) {
            doCustomerUpdate((Customer) curItem);
        } else if (curItem instanceof Transaction) {
            doTransactionUpdate((Transaction) curItem);
        } else {
            throw new InvalidItemException("An invalid item was received: " + curItem);
        }
        return curItem;
    }

    public void doCustomerUpdate(Customer curCustomer) {
        Customer storedCustomer = customerDao.findCustomerByTaxId(curCustomer.getTaxId());
        Account account = accountDao.findAccountByNumber(curCustomer.getAccount().getAccountNumber());

        curCustomer.setId(storedCustomer.getId());
        curCustomer.setAccount(account);
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setTickerDao(TickerDao tickerDao) {
        this.tickerDao = tickerDao;
    }

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    private void doTransactionUpdate(Transaction curItem) {
        updateTicker(curItem);
        updateAccount(curItem);

        curItem.setType(TransactionType.STOCK);
    }

    private void updateAccount(Transaction curItem) {
        Account account = accountDao.findAccountByNumber(curItem.getAccountNumber());
        curItem.setAccountId(account.getId());
    }

    private void updateTicker(Transaction curItem) {
        Ticker ticker = tickerDao.findTickerBySymbol(curItem.getTicker());

        if (ticker == null) {
            Ticker newTicker = new Ticker();
            newTicker.setTicker(curItem.getTicker());

            tickerDao.saveTicker(newTicker);
            ticker = tickerDao.findTickerBySymbol(curItem.getTicker());
        }

        curItem.setTickerId(ticker.getId());
    }


}
