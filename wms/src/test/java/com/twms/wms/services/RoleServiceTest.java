package com.twms.wms.services;

import com.twms.wms.entities.Role;
import com.twms.wms.repositories.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
public class RoleServiceTest {

    @InjectMocks
    private RoleService service;

    @Mock
    RoleRepository roleRepository;

    @Test
    public void shouldReturnALIstOfRoles(){
        List<Role> list = new ArrayList<>();

        Mockito.when(roleRepository.findAll()).thenReturn(list);
        Assertions.assertDoesNotThrow(() -> service.getAllRoles());
        Mockito.verify(roleRepository,Mockito.times(1)).findAll();

    }

}
