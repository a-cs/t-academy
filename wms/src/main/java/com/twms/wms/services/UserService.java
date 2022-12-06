package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.email.EmailLayout;
import com.twms.wms.email.EmailService;
import com.twms.wms.entities.ConfirmationToken;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.repositories.RoleRepository;
import com.twms.wms.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @SneakyThrows
    @Transactional
    public UserDTO createUser(User user){
        boolean usernameExists = userRepository.findUserByUsername(user.getUsername()).isPresent();
        boolean emailExists = userRepository.findUserByEmail(user.getEmail()).isPresent();
        if(usernameExists){
            throw new SQLIntegrityConstraintViolationException("Username already taken.");
        } else if(emailExists){
            throw new SQLIntegrityConstraintViolationException("Email already registered.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(UUID.randomUUID().toString()));
        user.setAccessLevel(roleRepository.findByAuthority(AccessLevel.ROLE_CLIENT));
        User savedUser = userRepository.save(user);

        confirmationTokenService.createTokenAndSendEmail(user);
        return new UserDTO(savedUser);
    }

    public User createClientUser(String username, String clientEmail){
        User clientUser = new User();
        clientUser.setUsername(username);
        clientUser.setEmail(clientEmail);
        //TODO: Auto generate random password
//        String clientPassword = username;
//        clientUser.setPassword(clientPassword);
        User savedClientUser = new User(this.createUser(clientUser));
//        savedClientUser.setPassword(clientPassword);
        return savedClientUser;
    }

    public String resetPassword(String username){
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new EntityNotFoundException("User " + username + " do not exist")
        );
        confirmationTokenService.createTokenAndSendEmail(user);
        return "An email was sent to your email address.";
    }

    public UserDTO getUserByUsername(String username){
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new EntityNotFoundException("User " + username + " do not exist")
        );
        return new UserDTO(user);
    }

    public User getUserById(Long userId){
        User savedUser = userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("User not found.")
        );
        return savedUser;
    }
    public Page<UserDTO> getUserFilteredByUsername(String searchTerm, Pageable pageable){
        Page<UserDTO> userFiltered = userRepository.findByUsernameContainingIgnoreCase(searchTerm, pageable).map(UserDTO::new);
//        Page<UserDTO> userDTOFiltered = userFiltered.stream().map(user->new UserDTO(user)).collect(Collectors.toList());

        return userFiltered;
    }

    public List<UserDTO> getAllUsers(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userList.stream().map(user->new UserDTO(user)).collect(Collectors.toList());
        return userDTOList;
    }

//    public UserDTO updateUserAccessLevel(Role role, Long userId){
//        User savedUser = this.getUserById(userId);
//        Role thisRole = roleRepository.findById(role.getId()).orElseThrow(
//                ()->new EntityNotFoundException("Role with id:" + role.getId() +" do not exist.")
//        );
//        savedUser.setAccessLevel(role);
//        return new UserDTO(userRepository.save(savedUser));
//    }

    public UserDTO updateUserIsEnable(Long userId, boolean isActivating){
        User savedUser = this.getUserById(userId);
        savedUser.setEnabled(isActivating);
        return new UserDTO(userRepository.save(savedUser));
    }

//    public String verificationEmailSender(){
//        return "ok";
//    }
//
//    public String userConfirmation(String token){
//        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);
//        if (confirmationToken.getConfirmedAt() != null){
//            throw new IllegalArgumentException("Email has already been confirmed");
//        }
//        if(confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())){
//            throw new IllegalArgumentException("Token has expired");
//        }
//        confirmationToken.setConfirmedAt(LocalDateTime.now());
//        this.updateUserIsEnable(confirmationToken.getUser().getId(), true);
//        return "User has been confirmed";
//    }
    public String setNewPassword(String token, String password){
        ConfirmationToken confirmationToken = confirmationTokenService.getConfirmationToken(token);
        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalArgumentException("Token has already been used.");
        }
        if(confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("Token has expired");
        }
        confirmationToken.setConfirmedAt(LocalDateTime.now());
        User user = this.getUserById(confirmationToken.getUser().getId());
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEnabled(true);
        userRepository.save(user);
        return "Password has been changed successfully.";
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User savedUser = userRepository.findUserByEmail(email).orElseThrow(
                ()->new UsernameNotFoundException("User not found.")
        );
        return savedUser;
    }

    public UserDTO updateUser(UserDTO user, Long userId) {
        User savedUser = this.getUserById(userId);
        if(userRepository.existsById(userId)){
            User updatedUser = new User(user);
            updatedUser.setPassword(savedUser.getPassword());
            return new UserDTO(userRepository.save(updatedUser));
        }
        throw new UsernameNotFoundException("User not found.");
    }

    public Page<UserDTO> getUsersPaginated(Pageable pageable) {
        return userRepository.findAll(pageable).map(UserDTO::new);
    }
}
