package es.urjc.samples.eventsourcing.shoppingcart.query.customer;

public class GetCustomerQuery {
    String customerId;

    public GetCustomerQuery(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId() {
        return customerId;
    } 
}
