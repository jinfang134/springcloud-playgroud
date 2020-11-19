package playgroud.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import playground.entity.Product;

@FeignClient(value = "product")
public interface ProductService {

    @GetMapping(value = "/api/product")
    Product getProduct();
}
