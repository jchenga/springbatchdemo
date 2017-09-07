package com.apress.demo.springbatch.statement.processor;

import com.apress.demo.springbatch.statement.domain.AccountTransaction;
import com.apress.demo.springbatch.statement.domain.PricingTier;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;

/**
 * Created by j1cheng on 2017-08-22.
 */
public class FeesItemProcessor implements ItemProcessor<AccountTransaction, AccountTransaction> {
    @Override
    public AccountTransaction process(AccountTransaction transaction) throws Exception {

        if (transaction.getTier() == PricingTier.I) {
            priceTierOneTransaction(transaction);
        } else if (transaction.getTier() == PricingTier.II) {
            priceTierTwoTransaction(transaction);
        } else if (transaction.getTier() == PricingTier.III) {
            priceTierThreeTransaction(transaction);
        } else {
            priceTierFourTransaction(transaction);
        }
        return transaction;
    }

    private void priceTierTwoTransaction(AccountTransaction transaction) {
        transaction.setFee(new BigDecimal(3.00));
    }

    private void priceTierOneTransaction(AccountTransaction transaction) {
        BigDecimal fee = transaction.getPrice().multiply(new BigDecimal(.001));

        fee = fee.add(new BigDecimal(9.00));
    }

    private void priceTierThreeTransaction(AccountTransaction transaction) {
        transaction.setFee(new BigDecimal(2.00));
    }

    private void priceTierFourTransaction(AccountTransaction transaction) {

        transaction.setFee(new BigDecimal(1.00));
    }

}
