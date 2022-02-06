package com.merchant.transactionservice.controller;


import com.merchant.transactionservice.model.Transaction;
import com.merchant.transactionservice.model.TransactionDTO;
import com.merchant.transactionservice.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
        this.transactionMapper = new TransactionMapper();
    }

    @GetMapping
    public List<TransactionDTO> findAll(@RequestParam Map<String, String> params) {
        List<Transaction> transactions = transactionService.findAll(params);
        List<TransactionDTO> transactionDtos = transactions.stream().map(transactionMapper::toDto).collect(Collectors.toList());
        return transactionDtos;
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<TransactionDTO> findByUuid(@PathVariable String uuid) {
        Transaction transaction = transactionService.findByUuid(uuid);
        return ResponseEntity.ok( transactionMapper.toDto(transaction) );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransactionDTO> create(@RequestBody TransactionDTO transactionDTO) {
        Transaction newTransaction = transactionMapper.toTransaction(transactionDTO);
        transactionService.save(newTransaction);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = "/{uuid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody TransactionDTO transactionDTO, @PathVariable String uuid) {
        Transaction transaction = transactionMapper.toTransaction(transactionDTO);
        transactionService.update(transaction, uuid);
    }

    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable String uuid) {
        transactionService.deleteTransaction(uuid);
    }
}