package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.email.EmailSender;
import com.twms.wms.email.EmailService;
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
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    @Spy
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Mock
    private ConfirmationTokenService confirmationTokenService;

    User user;

    @BeforeEach
    public void setup(){
        Role clientRole = new Role();
        clientRole.setAuthority(AccessLevel.ROLE_CLIENT);

        user = new User();
        user.setId(1L);
        user.setUsername("userTest");
        user.setEmail("user@email.com");
        user.setPassword("passwordTest");

        //user.setEnabled(false);

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());
        Mockito.when(bCryptPasswordEncoder.encode(any(String.class))).thenReturn("encodedPassword");
        Mockito.when(roleRepository.findByAuthority(AccessLevel.ROLE_CLIENT)).thenReturn(clientRole);
    }

    @Test
    public void returnUserOnCreateNewUser(){

        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findUserByUsername(any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findUserByEmail(any())).thenReturn(Optional.empty());
        Mockito.when(bCryptPasswordEncoder.encode(any())).thenReturn("random value");
        //Mockito.doNothing().when(confirmationTokenService.createTokenAndSendEmail(any(),eq(true)));

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
        Mockito.doReturn(new UserDTO(user)).when(userService).createUser(any());

        Assertions.assertNotNull(userService.createClientUser(user.getUsername(), user.getEmail()));
        Assertions.assertEquals(User.class,userService.createClientUser(user.getUsername(), user.getEmail()).getClass());
        //Mockito.verify(userRepository, Mockito.times(2)).save(any(User.class));
    }

    @Test
    public void throwsExceptionWhenUserNotFoundByUsername(){

        Assertions.assertThrows(EntityNotFoundException.class,
                () ->userService.getUserByUsername("notRegisteredUsername"));
    }

    @Test
    public void returnUserDTOOnFindByUsername(){

        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertEquals(new UserDTO(user), userService.getUserByUsername(user.getUsername()));

        Mockito.verify(userRepository, Mockito.times(1)).findUserByUsername(user.getUsername());
    }

//    @Test
//    public void throwsExceptionWhenUserNotFoundById(){
//
//        Assertions.assertThrows(EntityNotFoundException.class,
//                () ->userService.updateUserAccessLevel(new Role(),-1L, true));
//    }

    @Test
    public void returnUserDTOWithUpdatedIsDisabled(){

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(new UserDTO(user), userService.updateUserIsEnable(user.getId(), false));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }
    @Test
    public void returnUpdatedUserDTO(){

        UserDTO userDTO = new UserDTO(user);
        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.existsById(user.getId())).thenReturn(true);

        Assertions.assertEquals(userDTO, userService.updateUser(userDTO, user.getId()));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void returnAllUserDTOPaginated(){

        List<User> userList = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        userList.add(user);
        Page<User> userPage = new PageImpl<>(userList);
        Page<UserDTO> userDTOPage = new PageImpl<>(userList.stream().map(UserDTO::new).toList());
        Pageable pageable = PageRequest.of(0,1);

        Mockito.when(userRepository.findAll(pageable)).thenReturn(userPage);

        Assertions.assertEquals(userDTOPage, userService.getUsersPaginated(pageable));

        Mockito.verify(userRepository, Mockito.times(1)).findAll(pageable);
    }
}
