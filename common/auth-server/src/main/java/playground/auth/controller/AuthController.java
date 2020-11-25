package playground.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.auth.entity.Resource;
import playground.auth.entity.Role;
import playground.auth.service.JwtUtils;
import playground.auth.service.ResourceService;
import playground.auth.service.RoleService;
import playground.redis.AuthCache;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private AuthCache authCache;

    @Autowired
    ResourceService resourceService;

    @Autowired
    RoleService roleService;

    @GetMapping("/login")
    public String login(String username, String password) throws Exception {
        Set<Resource> resourceList=resourceService.loadResources("");
        Set<Role> roles=roleService.getRole();
        authCache.setResources(username,resourceList.stream().map(it->it.getCode()).collect(Collectors.toSet()));
        authCache.setRoles(username,roles.stream().map(it->it.getCode()).collect(Collectors.toSet()));
        return jwtUtils.generateToken(username);
    }



}
