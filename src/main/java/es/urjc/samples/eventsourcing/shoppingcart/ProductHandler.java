package es.urjc.samples.eventsourcing.shoppingcart;
import java.util.List;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.CartItem;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.Product;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ProductRepository;
import es.urjc.samples.eventsourcing.shoppingcart.rest.GetAllProductQuery;
import es.urjc.samples.eventsourcing.shoppingcart.rest.GetProductQuery;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ProductRepository;
import es.urjc.samples.eventsourcing.shoppingcart.rest.GetProductQuery;



@Component
public class ProductHandler {
    private final ProductRepository repository;

    public ProductHandler(ProductRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public Product handleQuery( GetProductQuery getProductQuery){
        return repository.findById(getProductQuery.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product " + getProductQuery.getProductId()));

    }

    @QueryHandler
    public List<Product> handleListQuery( GetAllProductQuery getProductQuery){
        
        return repository.findAll();
    
    }

}
