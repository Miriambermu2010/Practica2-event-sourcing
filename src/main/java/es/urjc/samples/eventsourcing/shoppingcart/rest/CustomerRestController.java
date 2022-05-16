package es.urjc.samples.eventsourcing.shoppingcart.rest;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.Customer;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerRepository;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ShoppingCart;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ShoppingCartRepository;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;

import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import java.util.UUID;


@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;


    private final CustomerRepository repository;

    private final ShoppingCartRepository shoppingCartRepository;

    public CustomerRestController(CustomerRepository repository,ShoppingCartRepository shoppingCartRepository, CommandGateway commandGateway, QueryGateway queryGateway) {

        this.repository = repository;
        this.shoppingCartRepository = shoppingCartRepository;

        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }


    @PostMapping
    public String createCustomer(@RequestBody Customer customer) {
        if (customer.getCustomerId() == null) {
            customer.setCustomerId(UUID.randomUUID().toString());
        }

        repository.save(customer);

        return customer.getCustomerId();
    }

 
    @GetMapping
    public CompletableFuture<List<Customer>> listCustomer(){

        return queryGateway.query(new GetAllCustomerQuery(), ResponseTypes.multipleInstancesOf(Customer.class));
    }

    @GetMapping("/{customerId}")
    public CompletableFuture<Customer> getCustomer(@PathVariable String customerId) {
        return queryGateway.query(new GetCustomerQuery(customerId), ResponseTypes.instanceOf(Customer.class));
    }

    @PostMapping("/{customerId}/cart")
    public String createCart(@PathVariable String customerId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartId(UUID.randomUUID().toString());
        shoppingCart.setCustomerId(customerId);
        shoppingCartRepository.save(shoppingCart);

        return shoppingCart.getCartId();
    }


}
