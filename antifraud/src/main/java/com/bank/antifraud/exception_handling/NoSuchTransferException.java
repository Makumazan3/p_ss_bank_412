package com.bank.antifraud.exception_handling;

public class NoSuchTransferException extends RuntimeException {
    public NoSuchTransferException() {
        super();
    }

    public NoSuchTransferException(String message) {
        super(message);
    }
}
