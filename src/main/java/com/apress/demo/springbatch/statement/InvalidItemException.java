package com.apress.demo.springbatch.statement;

/**
 * Created by j1cheng on 2017-08-15.
 */
public class InvalidItemException extends Exception {

    public InvalidItemException() {
    }

    public InvalidItemException(String arg0) {
        super(arg0);
    }

    public InvalidItemException(Throwable arg0) {
        super(arg0);
    }

    public InvalidItemException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }


}
