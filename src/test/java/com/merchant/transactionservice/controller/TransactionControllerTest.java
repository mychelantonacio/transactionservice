package com.merchant.transactionservice.controller;

import com.merchant.transactionservice.model.Transaction;
import com.merchant.transactionservice.model.TransactionStatus;
import com.merchant.transactionservice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TransactionControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;

    private List<Transaction> initialTransactions;
    private Map<String, String> params;

    private static final String Url = "/api/v1/transactions";


    @BeforeEach
    void init() {

        params = new HashMap<>();

        Transaction t1 = new Transaction("GBP", TransactionStatus.CREATED);
        Transaction t2 = new Transaction("EUR", TransactionStatus.PROCESSED);
        Transaction t3 = new Transaction("USD", TransactionStatus.FAILED);
        Transaction t4 = new Transaction("USD", TransactionStatus.CREATED);

        List<Transaction> transactions = new ArrayList<>(Arrays.asList(t1, t2, t3, t4));

        initialTransactions = transactions;
    }

    @Test
    public void listAllTransactions() throws Exception {

        when(transactionService.findAll(params)).thenReturn(initialTransactions);

         MvcResult result = mockMvc.perform( MockMvcRequestBuilders
                .get( Url )
                .accept( MediaType.APPLICATION_JSON) )
                .andDo( print() )
                .andExpect(status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4) ))
                 .andReturn();
    }

    @Test
    public void listAllTransactionsFilterByCurrency() throws Exception {

        params.put("currency", "GBP");
        when(transactionService.findAll(params)).thenReturn(Arrays.asList(initialTransactions.get(0)));

        MvcResult result = mockMvc.perform( MockMvcRequestBuilders
                .get( Url )
                .param("currency", "GBP")
                .accept( MediaType.APPLICATION_JSON) )
                .andDo( print() )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].currency", is(initialTransactions.get(0).getCurrency()) ))
                .andReturn();
    }

    @Test
    public void listAllTransactionsFilterByStatus() throws Exception {

        params.put("status", "CREATED");
        when(transactionService.findAll(params)).thenReturn(Arrays.asList(initialTransactions.get(0),initialTransactions.get(3)));

        MvcResult result = mockMvc.perform( MockMvcRequestBuilders
                .get( Url )
                .param("status", "CREATED")
                .accept( MediaType.APPLICATION_JSON) )
                .andDo( print() )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", is(initialTransactions.get(0).getStatus().toString()) ))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].status", is(initialTransactions.get(3).getStatus().toString()) ))
                .andReturn();
    }
}
