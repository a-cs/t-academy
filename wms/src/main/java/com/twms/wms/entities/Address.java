package com.twms.wms.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Street field cannot be blank or null.")
    private String street;

    @NotBlank(message = "Address number field cannot be blank or null.")
    private String number;

    @NotBlank(message = "City field cannot be blank or null.")
    private String city;

    @NotBlank(message = "State field cannot be blank or null.")
    @Size(min = 2, max = 2, message = "State must be 2 characters long.")
    private String state;


    @NotBlank(message = "Zip Code field cannot be blank or null.")
    @Size(min = 8, max = 8, message = "Zip Code field must be 8 characters long.")
    private String zipCode;
}
