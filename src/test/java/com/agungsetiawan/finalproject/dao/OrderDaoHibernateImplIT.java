package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import com.agungsetiawan.finalproject.type.IntegrationTest;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@org.junit.experimental.categories.Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfigTest.class)
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class OrderDaoHibernateImplIT {
    
    @Autowired
    private OrderDao orderDao;
    
    @Autowired
    private BookDao bookDao;
    
    @Autowired
    private CustomerDao customerDao;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void saveTest() {
        Book book=bookDao.findOne(1L);
        Book book2=bookDao.findOne(2L);
        Customer customer=customerDao.findOne(1L);
        
        Order order=new Order();
        
        OrderDetail detail=new OrderDetail();
        detail.setBook(book);
        detail.setAmount(2);
        detail.setSubTotal(new BigDecimal(detail.getAmount()).multiply(book.getPrice()));
        detail.setOrder(order);
        
        OrderDetail detail2=new OrderDetail();
        detail2.setAmount(2);
        detail2.setSubTotal(new BigDecimal(detail2.getAmount()).multiply(book2.getPrice()));
        detail2.setOrder(order);
        
        order.setDate(new Date());
        order.setCustomer(customer);
        order.setStatus("new");
        order.getOrderDetails().add(detail);
        order.getOrderDetails().add(detail2);
        order.setTotal(detail.getSubTotal().add(detail2.getSubTotal()));
        order.setCity("Surabaya");
        order.setProvince("Jawa Timur");
        order.setReceiver("Brawijaya");
        order.setReceiverEmail("brawijaya@gmail.com");
        order.setReceiverPhone("089808980898");
        order.setShippingAddress("Jl Kawi 80, Kauman, Surabaya");
        
        Order orderPersisted=orderDao.save(order);
        assertNotNull(orderPersisted);
        assertNotNull(orderPersisted.getId());
        assertEquals(new BigDecimal("130.00"), order.getTotal());
        assertEquals("agung", order.getCustomer().getUsername());
        
        Order orderFound=orderDao.findOne(3L);
        List<OrderDetail> listOrderDetail=orderFound.getOrderDetails();
        
        assertEquals(new BigDecimal("130.00"), orderFound.getTotal());
        assertEquals("agung", orderFound.getCustomer().getUsername());
        assertNotNull(listOrderDetail);
        assertEquals(2, listOrderDetail.size());
        assertEquals("Java Super", listOrderDetail.get(0).getBook().getTitle());
        
        List<Order> listOrder=orderDao.findAll();
        assertEquals(3, listOrder.size());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findOneTest(){
        Order orderFound=orderDao.findOne(1L);
        List<OrderDetail> listOrderDetail=orderFound.getOrderDetails();
        
        assertEquals(new BigDecimal("130.00"), orderFound.getTotal());
        assertEquals("agung", orderFound.getCustomer().getUsername());
        assertNotNull(listOrderDetail);
        assertEquals(2, listOrderDetail.size());
        assertEquals("Java Super", listOrderDetail.get(0).getBook().getTitle());
    }
    
    @Test
    public void findOneNotFoundTest(){
        Order orderNull=orderDao.findOne(3L);
        assertNull(orderNull);
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findByCustomerTest(){
        Customer customer=customerDao.findOne(1L);
        List<Order> order=orderDao.findByCustomer(customer.getUsername());
        
        assertNotNull(order);
        assertEquals(2, order.size());
        assertEquals("agung", order.get(0).getCustomer().getUsername());
    }
}