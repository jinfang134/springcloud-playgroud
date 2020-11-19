package playgroud.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import playground.entity.Product;
import playground.entity.User;

@RestController
public class UserController {

    @Autowired
    private ProductService productService;

    @Value("${server.port}")
    private String port;

    @GetMapping("/api/user")
    public User getUser() {
        String message = "provide from port: " + port;
        Product p = productService.getProduct();
        return new User(12L, "test name", message, p);
    }
}
