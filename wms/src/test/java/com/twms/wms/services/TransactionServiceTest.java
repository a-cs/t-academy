package com.twms.wms.services;

import com.twms.wms.entities.*;
import com.twms.wms.enums.TransactionType;
import com.twms.wms.repositories.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    @InjectMocks
    @Spy
    TransactionService service;

    @Mock
    UserService userService;

    @Mock
    WarehouseSlotService warehouseSlotService;

    @Mock
    TransactionRepository transactionRepository;

    @Test
    public void shouldReturnListOfTransactionsAfterInOperation(){
        User user = new User();
        user.setId(1l);

        WarehouseSlot warehouseSlot = new WarehouseSlot();
        warehouseSlot.setWarehouseSlotId(new WarehouseSlotId(new Branch(),1,"A"));

        Transaction transaction = new Transaction();
        transaction.setQuantity(20);
        transaction.setClient(new Client());
        transaction.setSku(new SKU());
        transaction.setUser(user);
        transaction.setType(TransactionType.IN);
        transaction.setWarehouseSlot(warehouseSlot);

        Mockito.when(userService.getUserById(any())).thenReturn(user);
        Mockito.when(warehouseSlotService.getFirstEmptySlot(any())).thenReturn(warehouseSlot);
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);

        Assertions.assertDoesNotThrow(() -> service.createTransaction(transaction));

    }

    @Test
    public void shouldReturnfTransactionAfterOutOperation(){
        User user = new User();
        user.setId(1l);

        Client client = new Client();
        client.setId(1L);

        SKU sku = new SKU();
        sku.setId(1L);

        WarehouseSlot warehouseSlot = new WarehouseSlot();
        warehouseSlot.setWarehouseSlotId(new WarehouseSlotId(new Branch(),1,"A"));
        warehouseSlot.setQuantity(20);
        warehouseSlot.setClient(client);
        warehouseSlot.setSku(sku);

        Transaction transaction = new Transaction();
        transaction.setQuantity(20);
        transaction.setClient(client);
        transaction.setSku(sku);
        transaction.setUser(user);
        transaction.setType(TransactionType.OUT);
        transaction.setWarehouseSlot(warehouseSlot);

        Mockito.when(userService.getUserById(any())).thenReturn(user);
        Mockito.when(warehouseSlotService.getOldestSlotByClientAndSkuAndBranch(any(),any(),any())).thenReturn(List.of(warehouseSlot));
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);

        Assertions.assertDoesNotThrow(() -> service.createTransaction(transaction));

    }
    @Test
    public void shouldReturnListOfTransactionsAfterOutOperation(){
        User user = new User();
        user.setId(1l);

        Client client = new Client();
        client.setId(1L);

        SKU sku = new SKU();
        sku.setId(1L);

        Branch branch = new Branch();
        branch.setId(1L);

        WarehouseSlot warehouseSlot = new WarehouseSlot();
        warehouseSlot.setWarehouseSlotId(new WarehouseSlotId(branch,1,"A"));
        warehouseSlot.setQuantity(10);
        warehouseSlot.setClient(client);
        warehouseSlot.setSku(sku);

        WarehouseSlot warehouseSlot2 = new WarehouseSlot();
        warehouseSlot2.setWarehouseSlotId(new WarehouseSlotId(branch,2,"A"));
        warehouseSlot2.setQuantity(11);
        warehouseSlot2.setClient(client);
        warehouseSlot2.setSku(sku);

        Transaction transaction = new Transaction();
        transaction.setQuantity(20);
        transaction.setClient(client);
        transaction.setSku(sku);
        transaction.setUser(user);
        transaction.setType(TransactionType.OUT);
        transaction.setWarehouseSlot(warehouseSlot);

        Mockito.when(userService.getUserById(any())).thenReturn(user);
        Mockito.when(warehouseSlotService.getOldestSlotByClientAndSkuAndBranch(any(),any(),any())).thenReturn(List.of(warehouseSlot,warehouseSlot2));
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);

        Assertions.assertDoesNotThrow(() -> service.createTransaction(transaction));

    }

    @Test
    public void shouldThrowExceptionAfterIllegalOutOperation(){
        User user = new User();
        user.setId(1l);

        Client client = new Client();
        client.setId(1L);

        SKU sku = new SKU();
        sku.setId(1L);

        WarehouseSlot warehouseSlot = new WarehouseSlot();
        warehouseSlot.setWarehouseSlotId(new WarehouseSlotId(new Branch(),1,"A"));
        warehouseSlot.setQuantity(10);
        warehouseSlot.setClient(client);
        warehouseSlot.setSku(sku);

        Transaction transaction = new Transaction();
        transaction.setQuantity(20);
        transaction.setClient(client);
        transaction.setSku(sku);
        transaction.setUser(user);
        transaction.setType(TransactionType.OUT);
        transaction.setWarehouseSlot(warehouseSlot);

        Mockito.when(userService.getUserById(any())).thenReturn(user);
        Mockito.when(warehouseSlotService.getOldestSlotByClientAndSkuAndBranch(any(),any(),any())).thenReturn(List.of(warehouseSlot));
        Mockito.when(transactionRepository.save(any())).thenReturn(transaction);

        Assertions.assertThrows(IllegalArgumentException.class,() -> service.createTransaction(transaction));

    }

    @Test
    public void shouldReturnPageOfTransactions(){
        Page<Transaction> transactions = Mockito.mock(Page.class);
        Mockito.when(transactionRepository.findAll(any(Pageable.class))).thenReturn(transactions);

        Assertions.assertDoesNotThrow(() -> service.readAllTransactionsPaginatedViaDTO(PageRequest.of(0, 4)));
        Mockito.verify(transactionRepository,Mockito.times(1)).findAll(any(Pageable.class));
    }

    @Test
    public void shouldListAllTransactionsWhenReadAllTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        Mockito.when(transactionRepository.findAll()).thenReturn(transactions);

        Assertions.assertNotNull(service.readAllTransactions());
        Mockito.verify(transactionRepository,Mockito.times(1)).findAll();
    }

    @Test
    public void shouldReturnTransactionWhenReadById(){
        Transaction transaction = new Transaction();
        Mockito.when(transactionRepository.findById(any(Long.class))).thenReturn(Optional.of(transaction));

        Assertions.assertDoesNotThrow(()->service.readTransactionById(1L));
        Mockito.verify(transactionRepository,Mockito.times(1)).findById(any(Long.class));
    }

    @Test
    public void shouldTrhowExceptionWhenTransactionDoesNotExist(){
        Mockito.when(transactionRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class,()->service.readTransactionById(1L));
        //Mockito.verify(transactionRepository,Mockito.times(1)).findById(any(Long.class));
    }

}
