package es.urjc.samples.eventsourcing.shoppingcart.command.customers;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Customer {

    @AggregateIdentifier
    private String id;
    private String fullName;
    private String address;

    public Customer(){

    }

    @CommandHandler
    public Customer( CreateCustomerCommand command){
        AggregateLifecycle.apply(new CustomerCreatedEvent(command.getCustomerId(), command.getFullName(), command.getAddress()));
    }

    @CommandHandler
    public void handle(CreateCustomerCommand command) {
        if( command.getFullName() == null || command.getFullName().length() == 0){
            throw new IllegalArgumentException("Name is required");
        }        
        AggregateLifecycle.apply(new CustomerCreatedEvent(command.getCustomerId(), command.getFullName(), command.getAddress()));
    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent event) {
    	this.id = event.getCustomerId();
    	this.fullName = event.getFullName();
        this.address = event.getAddress();
    }
    
}
