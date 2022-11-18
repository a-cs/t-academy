package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.repositories.RoleRepository;
import com.twms.wms.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    User user;

    @BeforeEach
    public void setup(){
        Role clientRole = new Role();
        clientRole.setAuthority(AccessLevel.ROLE_CLIENT);

        user = new User();
        user.setId(1L);
        user.setUsername("userTest");
        user.setPassword("passwordTest");
        user.addAccessLevel(clientRole);

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        Mockito.when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        Mockito.when(roleRepository.findByAuthority(AccessLevel.ROLE_CLIENT)).thenReturn(clientRole);
    }

    @Test
    public void returnUserOnCreateNewUser(){

        Mockito.when(userRepository.save(user)).thenReturn(user);
        UserDTO responseUserDTO = userService.createUser(user);

        Assertions.assertNotNull(responseUserDTO);
        Assertions.assertEquals(responseUserDTO, new UserDTO(user));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void returnUserOnCreateClientUser(){
        User clientUser = new User();
        clientUser.setUsername(user.getUsername());
        clientUser.setPassword("encodedPassword");
        clientUser.setAccessLevel(user.getAccessLevel());
        Mockito.when(userRepository.save(clientUser)).thenReturn(user);

        Assertions.assertNotNull(userService.createClientUser(user.getUsername()));
        Assertions.assertEquals(User.class,userService.createClientUser(user.getUsername()).getClass());
        Mockito.verify(userRepository, Mockito.times(2)).save(any(User.class));
    }

    @Test
    public void throwsExceptionWhenUserNotFoundByUsername(){

        Assertions.assertThrows(UsernameNotFoundException.class,
                () ->userService.getUserByUsername("notRegisteredUsername"));
    }

    @Test
    public void returnUserDTOOnFindByUsername(){

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertEquals(new UserDTO(user), userService.getUserByUsername(user.getUsername()));

        Mockito.verify(userRepository, Mockito.times(1)).findUserByUsername(user.getUsername());
    }

    @Test
    public void throwsExceptionWhenUserNotFoundById(){

        Assertions.assertThrows(EntityNotFoundException.class,
                () ->userService.updateUserAccessLevel(new Role(),-1L, true));
    }
    @Test
    public void returnUserDTOWithUpdatedAcessLevel(){
        Role role = new Role(1L, AccessLevel.ROLE_ADMIN);

        User userWithNewRole = new User();
        userWithNewRole.setAccessLevel(user.getAccessLevel());

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(new UserDTO(user), userService.updateUserAccessLevel(role, user.getId(), true));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }
    @Test
    public void returnUserDTOWithUpdatedIsEnabled(){

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(new UserDTO(user), userService.updateUserIsEnable(user.getId(), true));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }
}
