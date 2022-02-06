package com.merchant.transactionservice.service;

import com.merchant.transactionservice.exception.TransactionNotFoundException;
import com.merchant.transactionservice.model.Transaction;
import com.merchant.transactionservice.model.TransactionStatus;
import com.merchant.transactionservice.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
@Transactional
public class TransactionService {

    private static final String PARAM_CURRENCY = "currency";
    private static final String PARAM_STATUS = "status";

    private final TransactionRepository transactionRepository;

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    //can filter by currency and status
    public List<Transaction> findAll(Map<String, String> allParams) {
        if ( allParams == null )
            return transactionRepository.findAll( Example.of( new Transaction() ) );

        return transactionRepository.findAll( Example.of( buildTransaction(allParams), ExampleMatcher.matchingAny()
                .withIgnorePaths("uuid")), Sort.by(Sort.Direction.ASC, "id"));
    }

    public Transaction findByUuid(String uuid) {
        Optional<Transaction> transaction = transactionRepository.findByUuid(uuid);
        return transaction.orElseThrow( () -> new TransactionNotFoundException(uuid) );
    }

    public void save(Transaction newTransaction) {
        newTransaction.setCreationDate( LocalDateTime.now() );
        newTransaction.setStatus(TransactionStatus.CREATED);
        transactionRepository.save(newTransaction);
    }

    public void update(Transaction newTransaction, String uuid) {
        Optional<Transaction> optionalTransaction = transactionRepository.findByUuid(uuid);
        Transaction transaction = optionalTransaction.orElseThrow(() -> new TransactionNotFoundException(uuid));

        if (!newTransaction.getDescription().isEmpty()) {
            transaction.setDescription(newTransaction.getDescription());
        }
        transactionRepository.save(transaction);
    }

    public void deleteTransaction(String uuid) {
        Optional<Transaction> optionalTransaction = transactionRepository.findByUuid(uuid);
        Transaction transaction = optionalTransaction.orElseThrow( () -> new TransactionNotFoundException(uuid) );
        transactionRepository.delete(transaction);
        log.debug( "Transaction ( " + uuid + " ) deleted successfully" );
    }

    private Transaction buildTransaction(Map<String, String> allParams) {
        Transaction transaction = new Transaction();

        if ( allParams.containsKey(PARAM_CURRENCY) ) {
            String currency = allParams.get(PARAM_CURRENCY);
            if ( !currency.isEmpty() )
                transaction.setCurrency(allParams.get(PARAM_CURRENCY));
        }

        if ( allParams.containsKey(PARAM_STATUS) ) {
            String status = allParams.get(PARAM_STATUS);
            if ( !status.isEmpty() )
                transaction.setStatus(TransactionStatus.valueOf( status.toUpperCase() ));
        }
        return transaction;
    }
}
