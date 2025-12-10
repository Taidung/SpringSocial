package vn.taidung.springsocial.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

    private final Long id;
    private final Long roleLevel;

    public CustomUser(Long id, String username, String password, Long roleLevel,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
        this.roleLevel = roleLevel;
    }

    public Long getId() {
        return id;
    }

    public Long getRoleLevel() {
        return roleLevel;
    }

}
