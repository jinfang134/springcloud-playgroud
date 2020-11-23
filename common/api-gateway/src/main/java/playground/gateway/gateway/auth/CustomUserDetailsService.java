package playground.gateway.gateway.auth;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
//@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) {
        String password = passwordEncoder.encode("123456");

        return new org.springframework.security.core.userdetails.User(
                username,
                password,
                this.obtainGrantedAuthorities(username));
    }

    /**
     * 获得登录者所有角色的权限集合.
     *
     * @param user
     * @return
     */
    protected Set<GrantedAuthority> obtainGrantedAuthorities(String user) {
        Set<Role> roles = roleService.getRole();
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
    }
}
