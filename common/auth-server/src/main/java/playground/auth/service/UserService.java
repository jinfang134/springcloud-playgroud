package playground.auth.service;

import org.springframework.stereotype.Service;
import playground.entity.User;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    List<User> list= Arrays.asList(
            new User("user1","name1")
    );

    public List<User> findByUsername(String username){
        return list.stream().filter(it-> it.getUsername().equals(username)).collect(Collectors.toList());
    }
}
