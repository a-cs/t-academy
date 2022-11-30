package com.twms.wms.dtos;

import com.twms.wms.entities.Role;
import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private boolean enabled;
    private Role accessLevel;
//    private Set<Role> accessLevel = new HashSet<>();

    public UserDTO() {
    }
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.accessLevel = user.getAccessLevel();
        this.email = user.getEmail();
        this.enabled = user.isEnabled();
    }
}
