package com.agungsetiawan.finalproject.util;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Role;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public class RoleBuilder {
    private Role role;

    public RoleBuilder() {
        role=new Role();
    }
    
    public RoleBuilder id(Long id){
        role.setId(id);
        return this;
    }
    
    public RoleBuilder role(Integer rolex){
        role.setRole(rolex);
        return this;
    }
    
    public RoleBuilder name(String name){
        role.setName(name);
        return this;
    }
    
    public RoleBuilder customers(List<Customer> customers){
        role.setCustomers(customers);
        return this;
    }
    
    public Role build(){
        return role;
    }
}
