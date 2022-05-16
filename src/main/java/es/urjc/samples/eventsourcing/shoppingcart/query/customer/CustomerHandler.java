package es.urjc.samples.eventsourcing.shoppingcart.query.customer;

import java.util.List;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.CartItem;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerEntity;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerEntityRepository;


@Component
public class CustomerHandler {
    private final CustomerEntityRepository repository;

    public CustomerHandler(CustomerEntityRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public CustomerEntity handleQuery( GetCustomerQuery getCustomerQuery){
        return repository.findById(getCustomerQuery.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product " + getCustomerQuery.getCustomerId()));

    }

    @QueryHandler
    public List<CustomerEntity> handleListQuery( GetAllCustomerQuery getCustomerQuery){
        
        return repository.findAll();
    
    }
    
}
