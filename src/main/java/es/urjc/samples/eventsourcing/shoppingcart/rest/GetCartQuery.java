package es.urjc.samples.eventsourcing.shoppingcart.rest;

public class GetCartQuery {
    String cartId;

    public GetCartQuery(String cartId) {
        this.cartId = cartId;
    }

    public String getCartId() {
        return cartId;
    } 
    
}
