package com.twms.wms.dtos;


import com.twms.wms.entities.Address;
import lombok.Data;

@Data
public class AddressDTO {
    private String street;
    private String number;
    private String city;
    private String state;
    private String zipCode;

    public AddressDTO fromAddress(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet(address.getStreet());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setZipCode(address.getZipCode());
        return addressDTO;
    }
}
