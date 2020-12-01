package playground.user;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.annotation.AuthException;
import playground.annotation.HasResource;
import playground.entity.Product;
import playground.entity.User;
import playground.redis.AuthCache;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthCache authCache;

    @Value("${server.port}")
    private String port;

    @HasResource("user:add")
    @GetMapping("")
    public User getUser() {
        String message = "provide from port: " + port;
//        Product p = productService.getProduct();
        return new User(12L, "test name", message, null);
    }

    @GetMapping("/cur")
    public User current(){
        return authCache.getCurrentUser();
    }

}
