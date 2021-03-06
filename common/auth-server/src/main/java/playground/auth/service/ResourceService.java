package playground.auth.service;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;
import playground.auth.entity.Resource;

import java.util.Set;

@Service
public class ResourceService {
    public Set<Resource> loadResources(String role) {
        return Sets.newHashSet(
                new Resource("addUser", "user:add"),
                new Resource("listUser", "user:list"),
                new Resource("addProduct", "product:add"),
                new Resource("listProduct", "product:list")
        );
    }
}
