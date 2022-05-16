package es.urjc.samples.eventsourcing.shoppingcart.rest;

public class CustomerDto {
    private String customerId;
    private String fullName;
    private String address;
    
    public CustomerDto(){
        
    }

    public CustomerDto(String customerId, String fullName, String address) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.address = address;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
}
