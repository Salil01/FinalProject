package com.agungsetiawan.finalproject.util;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.Role;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public class CustomerBuilder {
    private Customer customer;

    public CustomerBuilder() {
        customer=new Customer();
    }
    
    public CustomerBuilder id(Long id){
        customer.setId(id);
        return this;
    }
    
    public CustomerBuilder username(String username){
        customer.setUsername(username);
        return this;
    }
    
    public CustomerBuilder password(String password){
        customer.setPassword(password);
        return this;
    }
    
    public CustomerBuilder fullName(String fullName){
        customer.setFullName(fullName);
        return this;
    }
    
    public CustomerBuilder email(String email){
        customer.setEmail(email);
        return this;
    }
    
    public CustomerBuilder address(String address){
        customer.setAddress(address);
        return this;
    }
    
    public CustomerBuilder phone(String phone){
        customer.setPhone(phone);
        return this;
    }
    
    public CustomerBuilder orders(List<Order> orders){
        customer.setListOrder(orders);
        return this;
    }
    
    public CustomerBuilder role(Role role){
        customer.setRole(role);
        return this;
    }
    
    public Customer build(){
        return customer;
    }
}
