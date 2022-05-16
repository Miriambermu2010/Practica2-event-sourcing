package es.urjc.samples.eventsourcing.shoppingcart.command.customers;

public class CardCreatedEvent {
    private String cardId;
    private String customerId;
    public CardCreatedEvent(String cardId, String customerId) {
        this.cardId = cardId;
        this.customerId = customerId;
    }
    public String getCardId() {
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
}
