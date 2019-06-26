package com.wolves.tolink.dto;

import com.wolves.tolink.entity.Role;
import com.wolves.tolink.entity.User;

public class UserDTO extends User {

    Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
