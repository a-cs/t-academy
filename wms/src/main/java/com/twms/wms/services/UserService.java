package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.repositories.RoleRepository;
import com.twms.wms.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @SneakyThrows
    @Transactional
    public UserDTO createUser(User user){
        boolean usernameExists = userRepository.findUserByUsername(user.getUsername()).isPresent();
        if(usernameExists){
            throw new SQLIntegrityConstraintViolationException("Username already registered.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.addAccessLevel(roleRepository.findByAuthority(AccessLevel.ROLE_CLIENT));
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }

    public User createClientUser(String username){
        User clientUser = new User();
        clientUser.setUsername(username);
        //TODO: Auto generate random password
        String clientPassword = username;
        clientUser.setPassword(clientPassword);
        User savedClientUser = new User(this.createUser(clientUser));
        savedClientUser.setPassword(clientPassword);
        return savedClientUser;
    }

    public UserDTO getUserByUsername(String username){
        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new EntityNotFoundException("User " + username + " do not exist")
        );
        return new UserDTO(user);
    }

    public List<UserDTO> getAllUsers(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> userDTOList = userList.stream().map(user->new UserDTO(user)).collect(Collectors.toList());
        return userDTOList;
    }

    public UserDTO updateUserAccessLevel(Role role, Long userId, boolean isAdding){
        User savedUser = userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("User not found.")
        );
        Role thisRole = roleRepository.findById(role.getId()).orElseThrow(
                ()->new EntityNotFoundException("Role with id:" + role.getId() +" do not exist.")
        );
        if(isAdding){
            savedUser.addAccessLevel(thisRole);
        } else {
            savedUser.revokeAccessLevel(thisRole);
        }
        return new UserDTO(userRepository.save(savedUser));
    }

    public UserDTO updateUserIsEnable(Long userId, boolean isActivating){
        User savedUser = userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("User not found.")
        );
        savedUser.setEnabled(isActivating);
        return new UserDTO(userRepository.save(savedUser));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User savedUser = userRepository.findUserByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("User not found.")
        );
        return savedUser;
    }
}
