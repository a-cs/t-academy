package com.twms.wms.services;

import com.twms.wms.dtos.TransactionDTO;
import com.twms.wms.entities.Transaction;
import com.twms.wms.entities.WarehouseSlot;
import com.twms.wms.enums.TransactionType;
import com.twms.wms.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    WarehouseSlotService warehouseSlotService;

    @Autowired
    UserService userService;

    @Transactional
    public List<TransactionDTO> createTransaction(Transaction transaction){
        transaction.setUser(userService.getUserById(transaction.getUser().getId()));
        List<Transaction> transactionList = new ArrayList<>();

        if(transaction.getType() == TransactionType.IN){

            WarehouseSlot slot = warehouseSlotService.getFirstEmptySlot(
                    transaction.getWarehouseSlot().getWarehouseSlotId().getBranch().getId());

            slot.setQuantity(transaction.getQuantity());
            slot.setClient(transaction.getClient());
            slot.setSku(transaction.getSku());
            slot.setArrivalDate(Instant.now());

            transaction.setWarehouseSlot(slot);
            transactionList.add(transactionRepository.save(transaction));

        } else if (transaction.getType() == TransactionType.OUT) {

            List<WarehouseSlot> slotList = warehouseSlotService.getOldestSlotByClientAndSkuAndBranch(transaction.getClient().getId(),
                    transaction.getWarehouseSlot().getWarehouseSlotId().getBranch().getId(),
                    transaction.getSku().getId());
            AtomicInteger total = new AtomicInteger(slotList.stream().mapToInt(WarehouseSlot::getQuantity).sum());

            if(total.get() < transaction.getQuantity())
                throw new RuntimeException("Not enough products.");

            slotList.forEach((slot) -> {
                if(transaction.getQuantity() > 0){
                    if(transaction.getQuantity() >= slot.getQuantity()){
                        Transaction t = new Transaction(transaction, slot);
                        t.setQuantity(slot.getQuantity());

                        transaction.setQuantity(transaction.getQuantity()-slot.getQuantity());
                        slot.setClient(null);
                        slot.setQuantity(0);
                        slot.setSku(null);
                        slot.setArrivalDate(null);
                        transactionList.add(transactionRepository.save(t));


                    } else {
                        Transaction t = new Transaction(transaction, slot);
                        t.setQuantity(transaction.getQuantity());
                        slot.setQuantity(slot.getQuantity() - transaction.getQuantity());
                        transaction.setQuantity(0);
                        transactionList.add(transactionRepository.save(t));
                    }
                }

            });
        }
        transaction.setDate(Timestamp.from(Instant.now()));
        return transactionList.stream().map(transact ->new TransactionDTO(transact)).toList();
    }

    public List<Transaction> readAllTransactions(){return transactionRepository.findAll();}

    public Transaction readTransactionById(Long transactionId){
        Optional<Transaction> optionalClient = transactionRepository.findById(transactionId);
        return optionalClient.orElseThrow(()->new EntityNotFoundException("Branch Not Created or Removed!!"));
    }

    public TransactionDTO updateTransaction(Long transactionId, Transaction transaction){
        Transaction oldTransaction = this.readTransactionById(transactionId);

        oldTransaction.setClient(transaction.getClient());
        oldTransaction.setSku(transaction.getSku());
        oldTransaction.setQuantity(transaction.getQuantity());
        oldTransaction.setDate(transaction.getDate());
        oldTransaction.setWarehouseSlot(transaction.getWarehouseSlot());
        oldTransaction.setUser(transaction.getUser());

        return this.createTransaction(oldTransaction).get(0);
    }

    @Transactional
    public void deleteTransaction(Long transactionId){
        Transaction toDelete = this.readTransactionById(transactionId);
        transactionRepository.delete(toDelete);
    }

    public Page<TransactionDTO> readAllTransactionsPaginatedViaDTO(Pageable pageable) {
        Page<Transaction> pageTransaction = transactionRepository.findAll(pageable);

        return pageTransaction.map(transaction->new TransactionDTO(transaction));
    }
}
