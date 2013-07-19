package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Customer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author awanlabs
 */
@Repository
public class CustomerDaoHibernateImpl implements CustomerDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Customer save(Customer customer) {
        sessionFactory.getCurrentSession().save(customer);
        return customer;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer findOne(Long id) {
        return (Customer) sessionFactory.getCurrentSession().get(Customer.class, id);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Customer findByUsername(String username) {
        return (Customer) sessionFactory.getCurrentSession().createQuery("select c from Customer c where c.username=:username")
                .setParameter("username", username).uniqueResult();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
