package playground.gateway.gateway.auth;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {

    public Set<Role> getRole(){
        return Sets.newHashSet(
                new Role("admin","admin"),
                new Role("guest","guest")
        );
    }
}
