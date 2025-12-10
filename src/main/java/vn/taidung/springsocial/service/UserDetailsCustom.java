package vn.taidung.springsocial.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import vn.taidung.springsocial.model.Role;

@Component("userDetailsService")
public class UserDetailsCustom implements UserDetailsService {

    private final UserService userService;

    public UserDetailsCustom(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        vn.taidung.springsocial.model.User user = this.userService.getUserByEmailHandler(email);
        Role role = user.getRole();
        String authority = role != null ? "ROLE_" + role.getName().toUpperCase() : "ROLE_USER";
        Long roleLevel = role != null ? role.getLevel() : 1L;

        return new CustomUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                roleLevel,
                Collections.singletonList(new SimpleGrantedAuthority(authority)));
    }

}
