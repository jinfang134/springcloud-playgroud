package playground.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.annotation.AuthException;
import playground.annotation.HasResource;
import playground.entity.Product;
import playground.entity.User;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private ProductService productService;

    @Value("${server.port}")
    private String port;

    @HasResource("user:add")
    @GetMapping("/api/user")
    public User getUser() {
        String message = "provide from port: " + port;
        Product p = productService.getProduct();
        return new User(12L, "test name", message, p);
    }

}
