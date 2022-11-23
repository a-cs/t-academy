package com.twms.wms.controllers;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/search")
    public ResponseEntity<UserDTO> readUser(@RequestParam("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUsername(username));
    }

    @PutMapping("/permissions/add/{userId}")
    public ResponseEntity<UserDTO> addUserAccessLevel(@RequestBody Role role,
                                                         @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserAccessLevel(role, userId, true));
    }
    @PutMapping("/permissions/revoke/{userId}")
    public ResponseEntity<UserDTO> revokeUserAccessLevel(@RequestBody Role role,
                                                      @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserAccessLevel(role, userId, false));
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> readAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
    @PutMapping("/{userId}/enable/{enabled}")
    public ResponseEntity<UserDTO> enableOrDisableUser(@PathVariable("userId") Long userId,
                                                  @PathVariable("enabled") boolean enabled){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserIsEnable(userId, enabled));
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmUserEmail(@RequestParam("token") String token){
        return ResponseEntity.status(HttpStatus.OK).body(userService.userConfirmation(token));
    }
}
