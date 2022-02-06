package com.merchant.transactionservice.exception;


public class TransactionNotFoundException extends RuntimeException{
    public TransactionNotFoundException(String uuid) {
        super( "Transaction (uuid) " + uuid + " Not Found" );
    }
}
