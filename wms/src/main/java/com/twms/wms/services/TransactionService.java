package com.twms.wms.services;

import com.twms.wms.entities.Transaction;
import com.twms.wms.exceptions.NoSuchClientException;
import com.twms.wms.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Transactional
    public Transaction createTransaction(Transaction transaction){return transactionRepository.save(transaction);}

    public List<Transaction> readAllTransactions(){return transactionRepository.findAll();}

    public Transaction readTransactionById(Long transactionId){
        Optional<Transaction> optionalClient = transactionRepository.findById(transactionId);
        return optionalClient.orElseThrow(()->new NoSuchClientException("Branch Not Created or Removed!!"));
    }

    public Transaction updateTransaction(Long transactionId, Transaction transaction){
        Transaction oldTransaction = this.readTransactionById(transactionId);

        oldTransaction.setClient(transaction.getClient());
        oldTransaction.setSku(transaction.getSku());
        oldTransaction.setQuantity(transaction.getQuantity());
        oldTransaction.setDate(transaction.getDate());
        oldTransaction.setWarehouseSlot(transaction.getWarehouseSlot());
        oldTransaction.setUser(transaction.getUser());

        return this.createTransaction(oldTransaction);
    }

    @Transactional
    public void deleteTransaction(Long transactionId){
        Transaction toDelete = this.readTransactionById(transactionId);
        transactionRepository.delete(toDelete);
    }
}
