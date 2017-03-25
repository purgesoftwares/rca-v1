package com.arnav.model;

import com.arnav.model.address.Address;
import com.arnav.model.customer.Customer;

/**
 * Created by HP on 3/26/2017.
 */
public class CouponCollectedResponse {
    public CouponCollectedResponse(Customer customer, Address address) {
        this.customer = customer;
        this.address = address;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    private Customer customer;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    private Address address;
}
