package com.twms.wms.services;

import com.twms.wms.entities.Address;
import com.twms.wms.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public Address post(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }

    public Address getById(Long id) {
        Optional<Address> possibleAddress = addressRepository.findById(id);
        Address address = possibleAddress.orElseThrow(() -> new EntityNotFoundException("Address does not exist."));
        return address;
    }

    public Address putById(Long id, Address newAddress) {
        Address persistedAddress = getById(id);

        persistedAddress.setStreet(newAddress.getStreet());
        persistedAddress.setNumber(newAddress.getNumber());
        persistedAddress.setCity(newAddress.getCity());
        persistedAddress.setState(newAddress.getState());
        persistedAddress.setZipCode(newAddress.getZipCode());

        return addressRepository.save(persistedAddress);
    }

    @Transactional
    public void deleteById(Long id) {
        Address persistedAddress = getById(id);
        addressRepository.delete(persistedAddress);
    }
}
