package com.twms.wms.controllers;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.User;
import com.twms.wms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("signup")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/manage/{username}")
    public ResponseEntity<UserDTO> readUser(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUsername(username));
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> userLogin(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.OK).body(userService.userLogin(user));
    }

    @PutMapping("/permissions/{userId}")
    public ResponseEntity<UserDTO> updateUserAccessLevel(@RequestBody User user,
                                                         @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserAccessLevel(user, userId));
    }
}
