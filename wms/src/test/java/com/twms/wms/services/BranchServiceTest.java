package com.twms.wms.services;


import com.twms.wms.entities.Address;
import com.twms.wms.entities.Branch;
import com.twms.wms.entities.Client;
import com.twms.wms.repositories.AddressRepository;
import com.twms.wms.repositories.BranchRepository;
import com.twms.wms.repositories.SKURepository;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class BranchServiceTest {
    @InjectMocks
    private BranchService service;

    @Mock
    private BranchRepository repository;

    @Mock
    private AddressService addressService;

    @Mock
    private SKURepository skuRepository;

    @Mock
    private WarehouseSlotRepository warehouseSlotRepository;

    @Test
    public void shouldRespondNotNullToSaveBranch(){
        Branch branch = new Branch();
        branch.setName("Teste");

        Address address = new Address();
        address.setId(1L);

        branch.setAddress(address);

        Mockito.when(repository.save(branch)).thenReturn(branch);

        Mockito.when(addressService.getById(branch.getAddress().getId())).thenReturn(new Address());

        Assertions.assertNotNull(service.createBranch(branch));

        Mockito.verify(repository, Mockito.times(1)).save(branch);
    }

    @Test
    public void shouldListResponseWhenRequestedBranchs(){
        List<Branch> branches = new ArrayList<>();
        Mockito.when(repository.findAll()).thenReturn(branches);
        Assertions.assertNotNull(service.readAllBranchs());
    }

    @Test
    public void shouldRespondVoidWhenABranchIsDeleted(){
        Branch branch = new Branch();
        branch.setName("Teste");
        branch.setId(1L);

        Mockito.when(repository.findById(branch.getId())).thenReturn(Optional.of(branch));
        Mockito.doNothing().when(repository).delete(branch);

        Mockito.when(skuRepository.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(warehouseSlotRepository.existsBySkuIdIn(any())).thenReturn(false);

        Assertions.assertDoesNotThrow(()->service.deleteBranch(branch.getId()));
        Mockito.verify(repository, Mockito.times(1)).delete(branch);
    }

    @Test
    public void shouldReturnModifiedBranchWhenUpdate(){

        Branch branch = new Branch();
        branch.setName("Teste");
        branch.setId(1L);

        Address address = new Address();
        address.setId(1L);

        branch.setAddress(address);

        Mockito.when(repository.save(branch)).thenReturn(branch);



        Mockito.when(repository.findById(branch.getId())).thenReturn(Optional.of(branch));

        Assertions.assertDoesNotThrow(()->service.updateBranch(branch.getId(), branch));
        Mockito.verify(repository, Mockito.times(1)).save(branch);

    }

}
