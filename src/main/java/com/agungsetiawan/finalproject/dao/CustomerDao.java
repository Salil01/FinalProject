package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Customer;

/**
 *
 * @author awanlabs
 */
public interface CustomerDao {
    public Customer save(Customer customer);
    public Customer findOne(Long id);
    public Customer findByUsername(String username);
}
