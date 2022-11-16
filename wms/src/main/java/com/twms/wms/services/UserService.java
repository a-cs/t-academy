package com.twms.wms.services;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import com.twms.wms.repositories.RoleRepository;
import com.twms.wms.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public UserDTO createUser(User user){
        boolean usernameExists = userRepository.findUserByUsername(user.getUsername()).isPresent();
        if(usernameExists){
            throw new IllegalStateException("Username already registered.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //TODO: Consultar role no banco??
        user.addAccessLevel(roleRepository.findByAuthority(AccessLevel.ROLE_CLIENT));
        User savedUser = userRepository.save(user);
        return new UserDTO(savedUser);
    }

    public User createClientUser(String username){
        User clientUser = new User();
        clientUser.setUsername(username);
        //TODO: Auto generate random password
        String clientPassword = username;
        clientUser.setPassword(username);
        User savedClientUser = new User(this.createUser(clientUser));
        savedClientUser.setPassword(clientPassword);
        return savedClientUser;
    }

    public UserDTO getUserByUsername(String username){

        User user = userRepository.findUserByUsername(username).orElseThrow(
                ()->new UsernameNotFoundException("User not found.")
        );
        return new UserDTO(user);
    }

    public UserDTO userLogin(User user){
        User userRegisted = userRepository.findUserByUsername(user.getUsername()).orElseThrow(
                ()->new UsernameNotFoundException("User not found.")
        );
        boolean isAuthenticated = bCryptPasswordEncoder.matches(user.getPassword(), userRegisted.getPassword());

        if(isAuthenticated){
            return new UserDTO(userRegisted);
        }
        throw new BadCredentialsException("Wrong user or password.");
    }

    public UserDTO updateUserAccessLevel(User user, Long userId){
        User savedUser = userRepository.findById(userId).orElseThrow(
                ()->new EntityNotFoundException("User not found.")
        );
        savedUser.setAccessLevel(user.getAccessLevel());
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
