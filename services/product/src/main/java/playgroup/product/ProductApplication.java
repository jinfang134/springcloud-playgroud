package playgroup.product;

import lombok.Data;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@RestController
public class ProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }

    @GetMapping("/api/product")
    public Product getProduct() {
        return new Product(12L, "test name");
    }
}

@Data
class Product{
    private Long id;
    private String productName;

    Product(long id,String productName){
        this.id=id;
        this.productName=productName;
    }
}