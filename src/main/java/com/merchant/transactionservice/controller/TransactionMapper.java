package com.merchant.transactionservice.controller;

import com.merchant.transactionservice.model.Transaction;
import com.merchant.transactionservice.model.TransactionDTO;
import org.springframework.stereotype.Component;


@Component
public class TransactionMapper {

    public TransactionDTO toDto(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setUuid(transaction.getUuid());
        transactionDTO.setCreationDate(transaction.getCreationDate());
        transactionDTO.setStatus(transaction.getStatus());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setCurrency(transaction.getCurrency());
        transactionDTO.setDescription(transaction.getDescription() == null ? "" : transaction.getDescription());

        return transactionDTO;
    }

    public Transaction toTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setCreationDate(transactionDTO.getCreationDate());
        transaction.setStatus(transactionDTO.getStatus());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setCurrency(transactionDTO.getCurrency());
        transaction.setDescription(transactionDTO.getDescription() == null ? "" : transactionDTO.getDescription());

        return transaction;
    }
}
