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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

        verify(userRepository, times(1)).save(user);
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

        verify(userRepository, times(1)).findUserByUsername(user.getUsername());
    }

//    @Test
//    public void throwsExceptionWhenUserNotFoundById(){
//
//        Assertions.assertThrows(EntityNotFoundException.class,
//                () ->userService.updateUserAccessLevel(new Role(),-1L, true));
//    }
    @Test
    public void returnUserDTOWithUpdatedAcessLevel(){
//        Role role = new Role(1L, AccessLevel.ROLE_ADMIN);
//
//        User userWithNewRole = new User();
//        userWithNewRole.setAccessLevel(user.getAccessLevel());
//
//        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        Mockito.when(roleRepository.findById(role.getId())).thenReturn(Optional.of(role));
//        Mockito.when(userRepository.save(user)).thenReturn(user);
//
//        Assertions.assertEquals(new UserDTO(user), userService.updateUserAccessLevel(role, user.getId(), true));
//
//        Mockito.verify(userRepository, Mockito.times(1)).save(user);

    }
    @Test
    public void returnUserDTOWithUpdatedIsDisabled(){

        Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);

        Assertions.assertEquals(new UserDTO(user), userService.updateUserIsEnable(user.getId(), false));

        verify(userRepository, times(1)).save(user);

    }

    @Test
    public void shouldThrowExceptionIfUsernameIsTaken() {
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        verify(userRepository, times(0)).save(any(User.class));

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
                () -> {
                    userService.createUser(user);
                }
        );
    }

    @Test
    public void shouldThrowExceptionIfEmailIsTaken() {
        Mockito.when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        verify(userRepository, times(0)).save(any(User.class));

        Assertions.assertThrows(SQLIntegrityConstraintViolationException.class,
                () -> { userService.createUser(user); }
        );
    }

    @Test
    public void shoudThrowExceptionIfResetPasswordByUnregisteredEmail() {
        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.resetPassword(anyString())
        );
    }

//    @Test
//    public void shouldResetPasswordIfEmailIsValid() {
//        Mockito.when(userRepository.findUserByUsername(user.getUsername())).thenReturn(Optional.empty());
//
//        Assertions.assertDoesNotThrow(
//                () -> userService.resetPassword(anyString())
//        );
//
//        verify(confirmationTokenService, times(1)).createTokenAndSendEmail(any(User.class), false);
//    }

//    @Test shouldReturnUserPageWhenFindByEmail() {
//        Mockito.when(userRepository.findUserByEmail(anyString())).thenReturn(Optional.of(any(User.class)));
//
//    }
}
