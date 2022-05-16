package es.urjc.samples.eventsourcing.shoppingcart.rest;

import es.urjc.samples.eventsourcing.shoppingcart.command.customers.CreateCardCommand;
import es.urjc.samples.eventsourcing.shoppingcart.command.customers.CreateCustomerCommand;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerEntity;
import es.urjc.samples.eventsourcing.shoppingcart.query.customer.GetAllCustomerQuery;
import es.urjc.samples.eventsourcing.shoppingcart.query.customer.GetCustomerQuery;

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

    public CustomerRestController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }


    @PostMapping
    public String createCustomer(@RequestBody CustomerDto customer) {
        String id = customer.getCustomerId() != null ? customer.getCustomerId() : UUID.randomUUID().toString();
        CreateCustomerCommand command = new CreateCustomerCommand(id, customer.getFullName(), customer.getAddress());
        commandGateway.send(command);
        return id;
    }

 
    @GetMapping
    public CompletableFuture<List<CustomerEntity>> listCustomer(){

        return queryGateway.query(new GetAllCustomerQuery(), ResponseTypes.multipleInstancesOf(CustomerEntity.class));
    }

    @GetMapping("/{customerId}")
    public CompletableFuture<CustomerEntity> getCustomer(@PathVariable String customerId) {
        return queryGateway.query(new GetCustomerQuery(customerId), ResponseTypes.instanceOf(CustomerEntity.class));
    }

    @PostMapping("/{customerId}/cart")
    public String createCart(@PathVariable String customerId) {        
        String cardId = UUID.randomUUID().toString();
        CreateCardCommand command = new CreateCardCommand(customerId, cardId);
        commandGateway.send(command);
        return cardId;
    }


}
