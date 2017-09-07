package com.apress.demo.springbatch.statement.writer;

import org.springframework.batch.item.file.ResourceSuffixCreator;

/**
 * Created by j1cheng on 2017-08-23.
 */
public class StatementSuffixGenerator implements ResourceSuffixCreator {
    @Override
    public String getSuffix(int i) {
       return i + ".txt";
    }
}
