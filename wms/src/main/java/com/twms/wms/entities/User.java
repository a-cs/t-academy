package com.twms.wms.entities;

import com.twms.wms.dtos.UserDTO;
import com.twms.wms.enums.AccessLevel;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "User cannot be Null")
    @Size(min = 3, max = 32)
    private String username;
    @NotNull
    @Email
    private String email;
    @Size(min = 5, max = 128)
    @NotNull(message = "Password cannot be Null")
    @NotBlank
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> accessLevel = new HashSet<>();
    private boolean enabled = false;

    public User() {
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.id = userDTO.getId();
        this.accessLevel = userDTO.getAccessLevel();
        this.email = userDTO.getEmail();
    }

    public void addAccessLevel(Role role){
        this.accessLevel.add(role);
    }
    public void revokeAccessLevel(Role role){
        this.accessLevel.remove(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return accessLevel.stream().map(role ->
                new SimpleGrantedAuthority(role.getAuthority().name())).collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
