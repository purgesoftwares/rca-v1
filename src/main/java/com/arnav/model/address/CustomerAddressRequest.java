package com.arnav.model.address;

/**
 * Created by HP on 3/16/2017.
 */
public class CustomerAddressRequest {

    /**
     * customerId :
     * address :
     */

    private String customerId;

    private Address address;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerAddressRequest{" +
                "customerId='" + customerId + '\'' +
                ", address=" + address +
                '}';
    }
}
