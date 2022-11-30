package com.twms.wms.controllers;

import com.twms.wms.entities.Role;
import com.twms.wms.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> readAll(){
        return ResponseEntity.status(HttpStatus.OK).body(roleService.getAllRoles());
    }

}
