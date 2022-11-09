package com.twms.wms.entities;

import com.twms.wms.enums.AccessLevel;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String description;
    @NotBlank
    private AccessLevel accessLevel = AccessLevel.OPERATOR;
}
