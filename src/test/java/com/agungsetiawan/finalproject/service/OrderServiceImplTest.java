package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.OrderDao;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.util.CustomerBuilder;
import com.agungsetiawan.finalproject.util.OrderBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author awanlabs
 */
public class OrderServiceImplTest {
    
    OrderServiceImpl orderServiceImpl;
    OrderDao orderDao;
    
    @Before
    public void setUp() {
        orderDao= Mockito.mock(OrderDao.class);
        orderServiceImpl=new OrderServiceImpl(orderDao);
    }
    
    @Test
    public void saveTest(){
        Customer customer=new CustomerBuilder().id(1L).username("blinkawan").fullName("Agung Setiawan")
                                    .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                                    .phone("089667754239").build();
        
        Order orderToSave=new OrderBuilder().customer(customer).city("Semarang").date(new Date(2013, 6, 26))
                              .province("Jawa Tengah").receiver("Agung Setiawan").receiverEmail("blinkawan@gmail.com")
                              .receiverPhone("089667754239").shippingAddress("Semarang 2").status("baru")
                              .build();
        
        Order orderSaved=new OrderBuilder().id(1L).customer(orderToSave.getCustomer()).city(orderToSave.getCity())
                             .date(orderToSave.getDate()).province(orderToSave.getProvince())
                             .receiver(orderToSave.getReceiver()).receiverEmail(orderToSave.getReceiverEmail())
                             .receiverPhone(orderToSave.getReceiverPhone()).shippingAddress(orderToSave.getShippingAddress())
                             .status(orderToSave.getShippingAddress()).build();
        
        Mockito.when(orderDao.save(orderToSave)).thenReturn(orderSaved);
        
        Order actual=orderServiceImpl.save(orderToSave);
        
        Mockito.verify(orderDao,Mockito.times(1)).save(orderToSave);
        Mockito.verifyNoMoreInteractions(orderDao);
        
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertEquals(orderSaved.getId(), actual.getId());
        Assert.assertEquals("Agung Setiawan", actual.getCustomer().getFullName());
    }
    
    @Test
    public void findOneTest(){
        Order order=new OrderBuilder().id(1L).city("Semarang").date(new Date(2013, 6, 26))
                              .province("Jawa Tengah").receiver("Agung Setiawan").receiverEmail("blinkawan@gmail.com")
                              .receiverPhone("089667754239").shippingAddress("Semarang 2").status("baru")
                              .build();
        
        Mockito.when(orderDao.findOne(1L)).thenReturn(order);
        
        Order actual=orderServiceImpl.findOne(1L);
        
        Mockito.verify(orderDao,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(orderDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(order, actual);
        Assert.assertEquals("Semarang 2", actual.getShippingAddress());
    }
    
    @Test
    public void findByCustomerTest(){
        Customer customer=new CustomerBuilder().id(1L).username("blinkawan").fullName("Agung Setiawan")
                                    .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                                    .phone("089667754239").build();
        
        Order orderOne=new OrderBuilder().id(1L).customer(customer).city("Semarang").date(new Date(2013, 6, 26))
                              .province("Jawa Tengah").receiver("Agung Setiawan").receiverEmail("blinkawan@gmail.com")
                              .receiverPhone("089667754239").shippingAddress("Semarang 2").status("baru")
                              .build();
        
        Order orderTwo=new OrderBuilder().id(2L).customer(customer).city("Kendal").date(new Date(2013, 6, 27))
                              .province("Jawa Tengah").receiver("Hauril Maulida Nisfari")
                              .receiverEmail("aurielhaurilnisfari@yahoo.com")
                              .receiverPhone("089668237854").shippingAddress("Semarang 2").status("baru")
                              .build();
        
        List<Order> orders=new ArrayList<Order>();
        orders.add(orderOne);
        orders.add(orderTwo);
        
        Mockito.when(orderDao.findByCustomer("blinkawan")).thenReturn(orders);
        
        List<Order> actual=orderServiceImpl.findByCustomer("blinkawan");
        
        Mockito.verify(orderDao,Mockito.times(1)).findByCustomer("blinkawan");
        Mockito.verifyNoMoreInteractions(orderDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(orders, actual);
        Assert.assertEquals("Agung Setiawan", actual.get(0).getReceiver());
        Assert.assertEquals("Hauril Maulida Nisfari", actual.get(1).getReceiver());
    }
    
    @Test
    public void findAllTest(){
        Order orderOne=new OrderBuilder().id(1L).city("Semarang").date(new Date(2013, 6, 26))
                              .province("Jawa Tengah").receiver("Agung Setiawan").receiverEmail("blinkawan@gmail.com")
                              .receiverPhone("089667754239").shippingAddress("Semarang 2").status("baru")
                              .build();
        
        Order orderTwo=new OrderBuilder().id(2L).city("Kendal").date(new Date(2013, 6, 27))
                              .province("Jawa Tengah").receiver("Hauril Maulida Nisfari")
                              .receiverEmail("aurielhaurilnisfari@yahoo.com")
                              .receiverPhone("089668237854").shippingAddress("Semarang 2").status("baru")
                              .build();
        
        List<Order> orders=new ArrayList<Order>();
        orders.add(orderOne);
        orders.add(orderTwo);
        
        Mockito.when(orderDao.findAll()).thenReturn(orders);
        
        List<Order> actual=orderServiceImpl.findAll();
        
        Mockito.verify(orderDao,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(orderDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(orders, actual);
        Assert.assertEquals("aurielhaurilnisfari@yahoo.com", actual.get(1).getReceiverEmail());
        
    }
}