package es.urjc.samples.eventsourcing.shoppingcart.query.products;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.Product;

import java.util.UUID;

@Aggregate
public class AddProductCommand {
 
    @AggregateIdentifier
    private String productId;


	public AddProductCommand(String productId) {
		this.productId = productId;

	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}


}
