package com.twms.wms.services;

import com.twms.wms.entities.Address;
import com.twms.wms.repositories.AddressRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AddressServiceTest {

    @InjectMocks
    private AddressService service;

    @Mock
    private AddressRepository repository;

    final Address address = new Address(1L,
            "Rua João da Costa",
            "999",
            "Brasília",
            "DF",
            "7777777"
    );

    @Test
    void shouldSaveAndReturnTheAddressGivenToPost() {
        // given
        Mockito.when(repository.save(address)).thenReturn(address);

        // when
        Address response = service.post(address);

        // assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(address, response);
        verify(repository).save(address);
    }

    @Test
    void shouldGetAllAddresses() {
        // given
        List<Address> addresses = new ArrayList<>();
        when(repository.findAll()).thenReturn(addresses);

        // then
        Assertions.assertNotNull(service.getAll());
        verify(repository).findAll();
    }

    @Test
    void shouldGetOneAddress() {
        // given
        Mockito.when(repository.findById(anyLong())).thenReturn(Optional.of(address));

        // when
        Address response = service.getById(anyLong());

        //
        Assertions.assertNotNull(response);
        Assertions.assertEquals(address, response);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void shouldThrowEntityNotFound() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.getById(anyLong()));
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void shoudDeleteValidId() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.of(address));
        doNothing().when(repository).delete(address);

        // then
        service.deleteById(anyLong());
        verify(repository, times(1)).delete(address);
    }

    @Test
    void shouldNotDeleteInvalidId() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteById(anyLong()));
        verify(repository, times(0)).delete(address);
    }

    @Test
    void shouldPutByValidId() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.of(address));

        // when
        service.putById(anyLong(), address);

        // then
        verify(repository, times(1)).save(address);

    }


    @Test
    void shouldNotPutByInvalidId() {
        // given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        // then
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> service.putById(anyLong(), address)
        );
        verify(repository, times(0)).save(address);

    }
}
