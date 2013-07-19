package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author awanlabs
 */
@Repository
public class OrderDaoHibernateImpl implements OrderDao{
    
    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired
    private OrderDetailDao orderDetailDao;

    @Override
    public Order save(Order order) {
        sessionFactory.getCurrentSession().save(order);
        for(OrderDetail detail:order.getOrderDetails()){
            orderDetailDao.save(detail);
        }
        return order;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order findOne(Long id) {
        return (Order) sessionFactory.getCurrentSession().get(Order.class, id);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> findByCustomer(Customer customer) {
        return sessionFactory.getCurrentSession().createQuery("select o from Order o where o.customer.username=:customer")
                .setParameter("customer", customer.getUsername()).list();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> findAll() {
        return sessionFactory.getCurrentSession().createQuery("select o from Order o").list();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
