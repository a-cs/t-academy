package com.twms.wms.entities;

import com.twms.wms.enums.AccessLevel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private AccessLevel authority;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AccessLevel getAuthority() {
        return authority;
    }

    public void setAuthority(AccessLevel authority) {
        this.authority = authority;
    }

    public Role() {
    }

    public Role(Long id, AccessLevel authority) {
        this.id = id;
        this.authority = authority;
    }
}
