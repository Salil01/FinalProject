package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.OrderDao;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao=orderDao;
    }
    
    @Override
    public Order save(Order order) {
        return orderDao.save(order);
    }

    @Override
    public Order findOne(Long id) {
        return orderDao.findOne(id);
    }

    @Override
    public List<Order> findByCustomer(String username) {
        return orderDao.findByCustomer(username);
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public Order edit(Order order) {
        return orderDao.edit(order);
    }
    
}
