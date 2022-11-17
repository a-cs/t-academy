package com.twms.wms.services;

import com.twms.wms.dtos.WarehouseSlotDTO;
import com.twms.wms.entities.*;
import com.twms.wms.repositories.WarehouseSlotRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class WarehouseSlotServiceTest {

    @InjectMocks
    private WarehouseSlotService service;

    @Mock
    private BranchService branchService;

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

    @BeforeEach()
    public void setUp() {
        branch.setId(1L);
    }

    @Test
    public void shouldSaveAndReturnAsDTO() {
        // given
        Mockito.when(repository.save(warehouseSlot)).thenReturn(warehouseSlot);

        // when
        WarehouseSlotDTO response = service.post(warehouseSlot);

        // assert
        Assertions.assertNotNull(response);
        verify(repository).save(warehouseSlot);
    }

    @Test
    public void shouldGetAllWarehouseSlotsAndReturnAsWarehouseSlotDTO() {
        // given
        when(repository.findAll()).thenReturn(List.of(warehouseSlot));

        // when
        List<WarehouseSlotDTO> response = service.getAll();

        // assert
        Assertions.assertNotNull(response);
        verify(repository).findAll();
    }

    @Test
    public void shouldReturnDTOWhenGetByValidId() {
        // given
        when(repository.findById(any(WarehouseSlotId.class))).thenReturn(Optional.of(warehouseSlot));

        // assert
        Assertions.assertDoesNotThrow(() -> service.getByPK(
                warehouseSlotId.getBranch().getId(),
                warehouseSlotId.getBay(),
                warehouseSlotId.getAisle()
        ));
    }

    @Test
    public void shouldNotReturnWhenFindByInvalidId() {
        // given
        when(repository.findById(any(WarehouseSlotId.class))).thenReturn(Optional.empty());

        // assert
        Assertions.assertThrows(NoSuchElementException.class, () -> service.getByPK(
                warehouseSlotId.getBranch().getId(),
                warehouseSlotId.getBay(),
                warehouseSlotId.getAisle()
        ));
    }

    @Test
    public void shoudDeleteByValidId() {
        // given
        doNothing().when(repository).deleteById(any());

        // when
        service.deleteById(any());

        // assert
        verify(repository, times(1)).deleteById(any());
    }
}
