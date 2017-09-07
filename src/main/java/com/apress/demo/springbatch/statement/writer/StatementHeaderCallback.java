package com.apress.demo.springbatch.statement.writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by j1cheng on 2017-08-23.
 */
public class StatementHeaderCallback implements FlatFileHeaderCallback {
    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write("                                             " +
                "                         Brokerage Account Statement\n");
        writer.write("\n\n");
        writer.write("Apress Investment Company                     " +
                "                        Customer Service Number\n");
        writer.write("1060 W. Addison St.                            " +
                "                       (800) 876-5309\n");
        writer.write("Chicago, IL 60613                              " +
                "                       Available 24/7\n");
        writer.write("\n\n");
    }
}
