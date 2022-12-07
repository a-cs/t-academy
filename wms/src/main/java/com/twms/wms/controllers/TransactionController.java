package com.twms.wms.controllers;

import com.twms.wms.dtos.TransactionDTO;
import com.twms.wms.entities.Client;
import com.twms.wms.entities.Transaction;
import com.twms.wms.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping("/prettify")
    public ResponseEntity<Page<TransactionDTO>> getAllTransactionsPrettily(Pageable pageable){return ResponseEntity.status(HttpStatus.OK).body(transactionService.readAllTransactionsPaginatedViaDTO(pageable));}

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){return ResponseEntity.status(HttpStatus.OK).body(transactionService.readAllTransactions());}

    @GetMapping("/{idTransaction}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("idTransaction") Long idTransaction){return ResponseEntity.status(HttpStatus.OK).body(transactionService.readTransactionById(idTransaction));}

    @PostMapping
    public ResponseEntity<List<Transaction>> postTransaction(@RequestBody Transaction transaction){
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transaction));
    }

    @PutMapping("/{idTransaction}")
    public ResponseEntity<Transaction> putTransaction(@PathVariable("idTransaction") Long idTransaction,
                                            @RequestBody Transaction transaction){
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.updateTransaction(idTransaction,transaction));
    }

    @DeleteMapping("/{idTransaction}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable("idTransaction") Long idTransaction){
        transactionService.deleteTransaction(idTransaction);
        return ResponseEntity.noContent().build();
    }

}
