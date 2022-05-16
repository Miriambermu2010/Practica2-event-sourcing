package es.urjc.samples.eventsourcing.shoppingcart.command.customers;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Card {

    @AggregateIdentifier
    private String cardId;
    private String customerId;

    public Card(){

    }

    @CommandHandler
    public Card(CreateCardCommand command) {
        AggregateLifecycle.apply(new CardCreatedEvent(command.getCardId(), command.getCustomerId()));
    }

    @CommandHandler
    public void handle(CreateCardCommand command) {
        if( command.getCustomerId() == null)
            throw new IllegalArgumentException("Customer is required");
        AggregateLifecycle.apply(new CardCreatedEvent(command.getCardId(), command.getCustomerId()));
    }

    @EventSourcingHandler
    public void on(CardCreatedEvent event) {
    	this.cardId = event.getCardId();
        this.customerId = event.getCustomerId();
    }
}
