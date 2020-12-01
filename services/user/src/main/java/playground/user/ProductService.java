package playground.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import playground.entity.Product;

@FeignClient(value = "product", fallback = ProductFallback.class)
public interface ProductService {

    @GetMapping(value = "/api/product")
    Product getProduct();
}
