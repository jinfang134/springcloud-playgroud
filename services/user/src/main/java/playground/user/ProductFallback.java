package playground.user;

import org.springframework.stereotype.Component;
import playground.entity.Product;

@Component
public class ProductFallback implements ProductService {
    @Override
    public Product getProduct() {
        return new Product();
    }
}
