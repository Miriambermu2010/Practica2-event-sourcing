package es.urjc.samples.eventsourcing.shoppingcart.command.customers;

public class CreateCardCommand {
    
    private String customerId;
    private String cardId;
    public CreateCardCommand(String customerId, String cardId) {
        this.customerId = customerId;
        this.cardId = cardId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getCardId() {
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    
}
