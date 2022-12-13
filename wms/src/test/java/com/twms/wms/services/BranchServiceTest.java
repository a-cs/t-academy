package com.twms.wms.services;


import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.entities.*;
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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        branch.setMax_columns(2);
        branch.setMax_rows(2);

        Mockito.when(repository.save(branch)).thenReturn(branch);

        Mockito.when(addressService.getById(branch.getAddress().getId())).thenReturn(new Address());

        Assertions.assertNotNull(service.createBranch(branch));

        Mockito.verify(repository, Mockito.times(1)).save(branch);
    }
    @Test
    public void shouldThrowExceptionIfAddressIsNull() {
        Branch branch = new Branch();
        branch.setName("Teste");
        List<Branch> branchList = new ArrayList<>();
        branchList.add(branch);
        Mockito.when(repository.findByName(any(String.class))).thenReturn(branchList);

        verify(repository, times(0)).save(any(Branch.class));

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
                () -> {
                    service.createBranch(branch);
                }
        );
    }

    @Test
    public void shouldThrowExceptionIfBranchNameIsTaken() {
        Branch branch = new Branch();
        branch.setName("Teste");

        Address address = new Address();
        address.setId(1L);

        branch.setAddress(address);

        Mockito.when(addressService.getById(any(Long.class))).thenReturn(null);

        verify(repository, times(0)).save(any(Branch.class));

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
                () -> {
                    service.createBranch(branch);
                }
        );
    }

    @Test
    public void shouldRespondNotNullToSaveBranchBiggerThan26(){
        Branch branch = new Branch();
        branch.setName("Teste");

        Address address = new Address();
        address.setId(1L);

        branch.setAddress(address);
        branch.setMax_columns(29);
        branch.setMax_rows(29);

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
    public void shouldReturnStringWhenUsingConversion(){
        List<Branch> branches = new ArrayList<>();
        Mockito.when(repository.findByNameContainingIgnoreCase(any())).thenReturn(branches);
        Assertions.assertNotNull(service.searchTerm("any"));
        Mockito.verify(repository,Mockito.times(1)).findByNameContainingIgnoreCase(any());
    }

    @Test
    public void shouldReturnListOfBranchsWhenSearchedByListOfIds(){
        List<Branch> branches = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        Mockito.when(repository.findByIdIn(ids)).thenReturn(branches);
        Assertions.assertNotNull(service.getBranchesByIds(ids));
        Mockito.verify(repository,Mockito.times(1)).findByIdIn(any());
    }

    @Test
    public void shouldReturnListOfBranchesWhenSearchedByListOfFilteredIds(){
        List<Branch> branches = new ArrayList<>();
        ListIdsFilterDTO ids = new ListIdsFilterDTO();
        Mockito.when(repository.findByIdIn(any())).thenReturn(branches);
        Assertions.assertNotNull(service.getBranchesByIds(ids));
        Mockito.verify(repository,Mockito.times(1)).findByIdIn(any());
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
    public void shouldThrowExceptionWhenABranchIsLinked(){
        Branch branch = new Branch();
        branch.setName("Teste");
        branch.setId(1L);

        SKU sku = new SKU();
        sku.setId(1L);
        sku.setMeasurementUnit(new MeasurementUnit());
        sku.setName("any");
        sku.setCategory(new Category());


        Mockito.when(skuRepository.findAll()).thenReturn(List.of(sku));
        Mockito.when(repository.findById(branch.getId())).thenReturn(Optional.of(branch));
        Mockito.doNothing().when(repository).delete(branch);

        Mockito.when(skuRepository.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(warehouseSlotRepository.existsBySkuIdIn(any())).thenReturn(true);

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,()->service.deleteBranch(branch.getId()));
        //Mockito.verify(repository, Mockito.times(1)).delete(branch);
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
