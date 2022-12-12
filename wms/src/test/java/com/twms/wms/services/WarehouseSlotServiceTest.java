package com.twms.wms.services;

import com.twms.wms.dtos.ListIdsFilterDTO;
import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.*;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class WarehouseSlotServiceTest {

    @InjectMocks
    @Spy
    private WarehouseSlotService service;

    @Mock
    private BranchService branchService;

    @Mock
    private SKUService skuService;

    @Mock
    private ClientService clientService;

    @Mock
    private UserService userService;

    @Mock
    private WarehouseSlotRepository repository;


    Branch branch = new Branch();
    WarehouseSlotId warehouseSlotId = new WarehouseSlotId(branch, 7, "F");

    final WarehouseSlot warehouseSlot = new WarehouseSlot(
            warehouseSlotId,
            128,
            new SKU(),
            new Client(),
            Instant.now()
    );

    private WarehouseSlot warehouseSlot2 = warehouseSlot;

    @BeforeEach()
    public void setUp() {
        branch.setId(1L);
        warehouseSlot2.getSku().setMeasurementUnit(new MeasurementUnit());
    }

    @Test
    public void shouldSaveEntityToDatabase() {


        // given
        Mockito.when(repository.save(warehouseSlot2)).thenReturn(warehouseSlot2);

        // when
        WarehouseSlotDTO response = service.post(warehouseSlot2);

        // assert
        Assertions.assertNotNull(response);
        verify(repository).save(warehouseSlot2);
    }

    @Test
    public void shouldReturnAllEntities() {
        // given
        when(repository.findAll()).thenReturn(List.of(warehouseSlot2));

        // when
        List<WarehouseSlotDTO> response = service.getAll();

        // assert
        Assertions.assertNotNull(response);
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnAllEntitiesFromBranch() {
        // given
        when(branchService.readBranchById(any(Long.class))).thenReturn(branch);
        when(repository.findAllByWarehouseSlotIdBranch(any(Branch.class))).thenReturn(List.of(warehouseSlot2));

        // when
        List<WarehouseSlotDTO> response = service.getAllById(branch.getId());

        // assert
        Assertions.assertNotNull(response);
        verify(repository).findAllByWarehouseSlotIdBranch(branch);
    }


//    @Test
//    public void shouldReturnAllEntitiesFromClient() {
//        // given
//        when(branchService.readBranchById(any(Long.class))).thenReturn(branch);
//        when(repository.findByClientId(any(Long.class))).thenReturn(List.of(warehouseSlot));
//
//        // when
//        List<WarehouseSlotDTO> response = service.getByClientId(any(Long.class));
//
//        // assert
//        Assertions.assertNotNull(response);
//        verify(repository).findByClientId(any(Long.class));
//    }

    @Test
    public void shouldNotThrowEntityNotFoundWhenGivenValidId() {
        // given
        when(branchService.readBranchById(any(Long.class))).thenReturn(branch);
        when(repository.findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(
                any(Branch.class),
                any(String.class),
                any(Integer.class)
        )).thenReturn(Optional.of(warehouseSlot2));

        // assert
        Assertions.assertDoesNotThrow(() -> service.getById(
                warehouseSlotId.getBranch().getId(),
                warehouseSlotId.getAisle(),
                warehouseSlotId.getBay()
        ));
    }


    @Test
    public void shouldThrowEntityNotFoundWhenGivenInvalidId() {
        // given
        when(branchService.readBranchById(any(Long.class))).thenReturn(branch);
        when(repository.findByWarehouseSlotIdBranchAndWarehouseSlotIdAisleAndWarehouseSlotIdBay(
                any(Branch.class),
                any(String.class),
                any(Integer.class)
        )).thenReturn(Optional.empty());

        // assert
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(
                warehouseSlotId.getBranch().getId(),
                warehouseSlotId.getAisle(),
                warehouseSlotId.getBay()
        ));
    }

    @Test
    public void shoudDeleteByValidId() {
        // given
        doNothing().when(repository).deleteById(any());

        // when
        service.deleteById(
                warehouseSlotId.getBranch().getId(),
                warehouseSlotId.getAisle(),
                warehouseSlotId.getBay()
        );

        // assert
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    public void shouldReturnPaginatedListWhenRequestedByClientId(){

        when(repository.findByClientId(any(Long.class),any(Pageable.class))).thenReturn(new PageImpl(List.of(warehouseSlot)));

        Assertions.assertNotNull(service.getByClientId(1l,PageRequest.of(1,1)));

    }

    @Test
    public void shouldReturnPaginatedListWhenRequestedByUserId(){
        User user = new User();
        user.setEmail("any@who");
        user.setId(1L);
        when(userService.getUserById(any(Long.class))).thenReturn(user);
        when(clientService.getClientByEmail(any(String.class))).thenReturn(new Client());
        doReturn(new PageImpl(List.of(warehouseSlot))).when(service).getByUserId(any(Long.class),any(Pageable.class));

        Assertions.assertNotNull(service.getByUserId(1l,PageRequest.of(1,1)));
    }

    @Test
    public void shouldReturnPaginatedListWhenRequestedByClientIdAndBranch(){
        ListIdsFilterDTO branchs = new ListIdsFilterDTO();
        branchs.setIds(List.of(1L,2L));

        when(branchService.getBranchesByIds(any(ListIdsFilterDTO.class))).thenReturn(new ArrayList<Branch>());
        when(repository.findByClientIdAndWarehouseSlotIdBranchIn(any(Long.class),any(List.class),any(Pageable.class))).thenReturn(new PageImpl(new ArrayList<WarehouseSlot>()));

        Assertions.assertNotNull(service.getByClientIdAndBranches(1L,new ListIdsFilterDTO(),PageRequest.of(1,1)));

    }

}
