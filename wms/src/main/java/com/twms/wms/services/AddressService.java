package com.twms.wms.services;

import com.twms.wms.dtos.AddressDTO;
import com.twms.wms.entities.Address;
import com.twms.wms.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Transactional
    public AddressDTO post(AddressDTO addressDTO) {
        Address address = new Address().fromDTO(addressDTO);
        Address persistedAddress = addressRepository.save(address);
        AddressDTO persistedAddressDTO = new AddressDTO().fromAddress(persistedAddress);
        return persistedAddressDTO;
    }

    public List<AddressDTO> getAll() {
        List<Address> addresses = addressRepository.findAll();
        List<AddressDTO> addressDTOs = new ArrayList<>();
        addresses.forEach(address -> addressDTOs.add(new AddressDTO().fromAddress(address)));
        return addressDTOs;
    }

    public AddressDTO getById(Long id) {
        Optional<Address> possibleAddress = addressRepository.findById(id);
        Address address = possibleAddress.orElseThrow(() -> new NoSuchElementException("Address does not exists."));
        return new AddressDTO().fromAddress(address);
    }

    private Address getAddressById(Long id) {
        Optional<Address> possibleAddress = addressRepository.findById(id);
        return possibleAddress.orElseThrow();
    }

    public AddressDTO putById(Long id, AddressDTO addressDTO) {
        Address persistedAddress = getAddressById(id);
        Address newAddress = new Address().fromDTO(addressDTO);

        persistedAddress.setStreet(newAddress.getStreet());
        persistedAddress.setNumber(newAddress.getNumber());
        persistedAddress.setCity(newAddress.getCity());
        persistedAddress.setState(newAddress.getState());
        persistedAddress.setZipCode(newAddress.getZipCode());

        return new AddressDTO().fromAddress(addressRepository.save(persistedAddress));
    }

    @Transactional
    public void deleteById(Long id) {
        Address persistedAddress = getAddressById(id);
        addressRepository.delete(persistedAddress);
    }
}
