package com.apress.demo.springbatch.statement.processor;

import com.apress.demo.springbatch.statement.domain.AccountTransctionQuantity;
import com.apress.demo.springbatch.statement.domain.PricingTier;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j1cheng on 2017-08-18.
 */
public class PricingTierItemProcessor implements ItemProcessor<AccountTransctionQuantity, AccountTransctionQuantity> {

    private List<Integer> accountsProcessed = new ArrayList<>();

    @Override
    public AccountTransctionQuantity process(AccountTransctionQuantity atq) throws Exception {
        if (atq.getTransactionCount() <= 1000) {
            atq.setTier(PricingTier.I);
        } else if (atq.getTransactionCount() > 1000 &&
                atq.getTransactionCount() <= 100000) {
            atq.setTier(PricingTier.II);
        } else if (atq.getTransactionCount() > 100_000 && atq.getTransactionCount() <= 1_000_000)
            atq.setTier(PricingTier.III);
        else
            atq.setTier(PricingTier.IV);
        return atq;
    }
}
