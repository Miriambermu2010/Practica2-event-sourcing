package es.urjc.samples.eventsourcing.shoppingcart.query.product;

public class GetProductQuery {
    String productId;

    public GetProductQuery(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    } 
}
