package com.twms.wms.entities;

import com.twms.wms.dtos.UserDTO;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String CNPJ;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address adress;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
