package com.twms.wms.entities;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.enums.AccessLevel;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(min = 3)
    private String username;
    @NotBlank
    @Size(min = 5)
    private String password;
    private AccessLevel accessLevel = AccessLevel.OPERATOR;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.id = userDTO.getId();
        this.accessLevel = userDTO.getAccessLevel();
    }

}
