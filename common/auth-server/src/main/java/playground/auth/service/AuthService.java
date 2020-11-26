package playground.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import playground.auth.entity.Resource;
import playground.auth.entity.Role;
import playground.redis.AuthCache;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthCache authCache;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleService roleService;

    public String login(String username, String password) {
        Set<Resource> resourceList=resourceService.loadResources("");
        Set<Role> roles=roleService.getRole();
        authCache.setResources(username,resourceList.stream().map(it->it.getCode()).collect(Collectors.toSet()));
        authCache.setRoles(username,roles.stream().map(it->it.getCode()).collect(Collectors.toSet()));
        return jwtUtils.generateToken(username);
    }
}
