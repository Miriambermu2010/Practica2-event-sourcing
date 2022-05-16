package es.urjc.samples.eventsourcing.shoppingcart.command.customers;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerEntity;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.CustomerEntityRepository;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ShoppingCart;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ShoppingCartRepository;

@Component
public class CustomersProjection {
    private final CustomerEntityRepository repository;
    private final ShoppingCartRepository shoppingCartRepository;

    public CustomersProjection(CustomerEntityRepository repository, ShoppingCartRepository shoppingCartRepository) {
        this.repository = repository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @EventHandler
	public void on(CustomerCreatedEvent event) {
		CustomerEntity entity = new CustomerEntity(event.getCustomerId(), event.getFullName(), event.getAddress());
		repository.save(entity);
	}

    @EventHandler
    public void on(CardCreatedEvent event){
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setCartId(event.getCardId());
        shoppingCart.setCustomerId(event.getCustomerId());
        shoppingCartRepository.save(shoppingCart);

    }
}
