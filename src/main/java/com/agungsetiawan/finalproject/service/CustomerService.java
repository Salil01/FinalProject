/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.domain.Customer;

/**
 *
 * @author awanlabs
 */
public interface CustomerService {
    public Customer save(Customer customer);
    public Customer findOne(Long id);
    public Customer findByUsername(String username);
}
