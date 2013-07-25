package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.CustomerDao;
import com.agungsetiawan.finalproject.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService{
    
    private CustomerDao customerDao;

    @Autowired
    public CustomerServiceImpl(CustomerDao customerDao) {
        this.customerDao=customerDao;
    }

    @Override
    public Customer save(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public Customer findOne(Long id) {
        return customerDao.findOne(id);
    }

    @Override
    public Customer findByUsername(String username) {
        return customerDao.findByUsername(username);
    }
    
}
