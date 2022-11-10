package com.twms.wms.dtos;

import com.twms.wms.entities.User;
import com.twms.wms.enums.AccessLevel;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String username;
    private AccessLevel accessLevel = AccessLevel.OPERATOR;

    public UserDTO() {
    }
    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.accessLevel = user.getAccessLevel();
    }
}
