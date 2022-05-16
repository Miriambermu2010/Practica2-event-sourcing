package es.urjc.samples.eventsourcing.shoppingcart;

import java.util.List;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.CartItem;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.Customer;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerRepository;
import es.urjc.samples.eventsourcing.shoppingcart.rest.GetAllCustomerQuery;
import es.urjc.samples.eventsourcing.shoppingcart.rest.GetCustomerQuery;

import es.urjc.samples.eventsourcing.shoppingcart.rest.GetProductQuery;


@Component
public class CustomerHandler {
    private final CustomerRepository repository;

    public CustomerHandler(CustomerRepository repository) {
        this.repository = repository;
    }

    @QueryHandler
    public Customer handleQuery( GetCustomerQuery getCustomerQuery){
        return repository.findById(getCustomerQuery.getCustomerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product " + getCustomerQuery.getCustomerId()));

    }

    @QueryHandler
    public List<Customer> handleListQuery( GetAllCustomerQuery getCustomerQuery){
        
        return repository.findAll();
    
    }
    
}
