package com.twms.wms.controllers;

import com.twms.wms.dtos.AddressDTO;
import com.twms.wms.entities.Address;
import com.twms.wms.services.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    @PostMapping("/")
    public ResponseEntity<AddressDTO> post(@RequestBody @Valid AddressDTO address) {
        return ResponseEntity.status(HttpStatus.CREATED).body(addressService.post(address));
    }

    @GetMapping("/")
    public ResponseEntity<List<AddressDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getAll());
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getById(@PathVariable("addressId") Long addressId) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.getById(addressId));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> put(@PathVariable("addressId") Long addressId,
                                          @Valid @RequestBody AddressDTO addressDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(addressService.putById(addressId, addressDTO));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> delete(@PathVariable("addressId") Long addressId) {
        addressService.deleteById(addressId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
