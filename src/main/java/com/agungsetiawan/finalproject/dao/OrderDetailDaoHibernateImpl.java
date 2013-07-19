package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.OrderDetail;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author awanlabs
 */
@Repository
public class OrderDetailDaoHibernateImpl implements OrderDetailDao{
    
    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        sessionFactory.getCurrentSession().save(orderDetail);
        return orderDetail;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
