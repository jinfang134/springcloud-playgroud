package playground.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.auth.entity.Resource;
import playground.auth.entity.Role;
import playground.entity.User;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthCacheWriter authCache;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleService roleService;

    public String login(String username, String password) {
        Set<Resource> resourceList=resourceService.loadResources("");
        Set<Role> roles=roleService.getRole();
        String userId= UUID.randomUUID().toString();
        authCache.setResources(userId,resourceList.stream().map(it->it.getCode()).collect(Collectors.toSet()));
        authCache.setRoles(userId,roles.stream().map(it->it.getCode()).collect(Collectors.toSet()));
        authCache.setUser(userId,new User(username,username));
        return jwtUtils.generateToken(userId);
    }
}
