package playground.gateway.gateway.auth;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ResourceService {
    public Set<Resource> loadResources(String role) {
        return Sets.newHashSet(
                new Resource("addUser", "user:add"),
                new Resource("listUser", "user:list")
        );
    }
}
