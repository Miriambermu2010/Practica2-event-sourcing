package es.urjc.samples.eventsourcing.shoppingcart;

import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import es.urjc.samples.eventsourcing.shoppingcart.persistence.ShoppingCart;
import es.urjc.samples.eventsourcing.shoppingcart.persistence.ShoppingCartRepository;
import es.urjc.samples.eventsourcing.shoppingcart.rest.GetAllShoppingCartQuery;
import es.urjc.samples.eventsourcing.shoppingcart.rest.GetCartQuery;

@Component
public class CartHandler {
    
    private final ShoppingCartRepository repository;

    public CartHandler(ShoppingCartRepository repository) {
        this.repository = repository;
    }


    @QueryHandler
    public ShoppingCart handleQuery( GetCartQuery getCartQuery){
        System.out.println("preguntando por "+getCartQuery.getCartId());
        return repository.findById(getCartQuery.getCartId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedete Shopping Cart " + getCartQuery.getCartId()));
    
    }

    @QueryHandler
    public List<ShoppingCart> handleListQuery( GetAllShoppingCartQuery getCartQuery){
        
        return repository.findAll();
    
    }
    
}
