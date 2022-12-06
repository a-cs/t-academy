package com.twms.wms.controllers;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> readUser(@PathVariable("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUsername(username));
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserDTO>> readUserFiltered(@RequestParam("username") String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserFilteredByUsername(username));
    }

//    @PutMapping("/permissions/{userId}")
//    public ResponseEntity<UserDTO> setUserAccessLevel(@RequestBody Role role,
//                                                         @PathVariable("userId") Long userId){
//        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserAccessLevel(role, userId));
//    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO user,
                                                      @PathVariable("userId") Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(user, userId));
    }
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> readAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }
    @GetMapping("/pages")
    public ResponseEntity<Page<UserDTO>> readPaginated(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersPaginated(pageable));
    }
//    @PutMapping("/{userId}/enable/{enabled}")
//    public ResponseEntity<UserDTO> enableOrDisableUser(@PathVariable("userId") Long userId,
//                                                  @PathVariable("enabled") boolean enabled){
//        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserIsEnable(userId, enabled));
//    }
//
//    @GetMapping("/confirm")
//    public ResponseEntity<String> confirmUserEmail(@RequestParam("token") String token){
//        return ResponseEntity.status(HttpStatus.OK).body(userService.userConfirmation(token));
//    }
    @PutMapping("/setpassword")
    public ResponseEntity<String> setUserPassword(@RequestParam Map<String, String> passwordMap){
        String token = passwordMap.get("token");
        String password = passwordMap.get("password");
        return ResponseEntity.status(HttpStatus.OK).body(userService.setNewPassword(token, password));
//        return ResponseEntity.status(HttpStatus.OK).body(userService.userConfirmation(token));
    }
    @GetMapping("/resetpassword")
    public ResponseEntity<String> resetPassword(@RequestParam String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.resetPassword(username));
//        return ResponseEntity.status(HttpStatus.OK).body(userService.userConfirmation(token));
    }

}
