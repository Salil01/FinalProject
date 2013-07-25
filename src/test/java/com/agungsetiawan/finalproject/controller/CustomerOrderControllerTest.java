package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.config.WebAppConfig;
import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import com.agungsetiawan.finalproject.service.CustomUserDetailsService;
import com.agungsetiawan.finalproject.service.OrderService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import static org.hamcrest.Matchers.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
/**
 *
 * @author awanlabs
 */

public class CustomerOrderControllerTest {
    
    CustomerOrderController customerOrderController;
    OrderService orderService;
    MockMvc mockMvc;
    View view;
    
    @Before
    public void setUp() {
        orderService= EasyMock.createMock(OrderService.class);
        customerOrderController=new CustomerOrderController(orderService);
        view=EasyMock.createMock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(customerOrderController).setSingleView(view).build();
    }
    
    @Test
    public void allOrderTest() throws Exception{
        Customer customer=new Customer("blinkawan", "greatengineer", "Agung Setiawan", "blinkawan@gmail.com",
                                       "Semarang", "089667754239");
        List<Order> orders=new ArrayList<Order>();
        Order orderPertama=new Order(1L, new BigDecimal(80000), "tuntas", "Bukit Umbul 3E, Banyumanik", "Agung Setiawan",
                                     "Semarang", "Jawa Tengah", "blinkawan@gmail.com", "089667754239", new Date(2013,7,24), customer);
        Order orderKedua=new Order(2L, new BigDecimal(90000), "baru", "Sapen, Boja", "Hauril Maulida Nisfari",
                                     "Kendal", "Jawa Tengah", "aurielhaurilnisfari@yahoo.com", "089668237854", new Date(2013,7,24), customer);
        orders.add(orderPertama);
        orders.add(orderKedua);
        
        
        
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        EasyMock.expect(orderService.findByCustomer("blinkawan")).andReturn(orders);
        EasyMock.replay(orderService);
        
        mockMvc.perform(get("/secured/my/order"))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(model().attribute("page", "customerorder.jsp"))
                .andExpect(model().attribute("orders", orders))
                .andExpect(model().attribute("orders", hasSize(2)))
                .andExpect(model().attribute("orders", hasItem(
                                allOf(
                                        hasProperty("id",is(1L)),
                                        hasProperty("total", is(new BigDecimal(80000))),
                                        hasProperty("status", is("tuntas")),
                                        hasProperty("shippingAddress", is("Bukit Umbul 3E, Banyumanik")),
                                        hasProperty("receiver", is("Agung Setiawan")),
                                        hasProperty("city", is("Semarang")),
                                        hasProperty("province", is("Jawa Tengah")),
                                        hasProperty("receiverEmail", is("blinkawan@gmail.com")),
                                        hasProperty("receiverPhone", is("089667754239")),
                                        hasProperty("date", is(new Date(2013,7,24))),
                                        hasProperty("customer", is(customer))
                                      )
                       )))
                .andExpect(model().attribute("orders", hasItem(
                                allOf(
                                        hasProperty("id",is(2L)),
                                        hasProperty("total", is(new BigDecimal(90000))),
                                        hasProperty("status", is("baru")),
                                        hasProperty("shippingAddress", is("Sapen, Boja")),
                                        hasProperty("receiver", is("Hauril Maulida Nisfari")),
                                        hasProperty("city", is("Kendal")),
                                        hasProperty("province", is("Jawa Tengah")),
                                        hasProperty("receiverEmail", is("aurielhaurilnisfari@yahoo.com")),
                                        hasProperty("receiverPhone", is("089668237854")),
                                        hasProperty("date", is(new Date(2013,7,24))),
                                        hasProperty("customer", is(customer))
                                      )
                       )));
    }
    
    @Test
    public void detailOrderTest() throws Exception{
        Customer customer=new Customer("blinkawan", "greatengineer", "Agung Setiawan", "blinkawan@gmail.com",
                                       "Semarang", "089667754239");
        Book bookPertama=new Book("Java By Doing", "Agung Setiawan", "good book", new BigDecimal(70000), "imagePertama");
        Book bookKedua=new Book("Java Unit Testing", "Akhtar", "good book", new BigDecimal(60000), "imageKedua");
        
        Order order=new Order(1L, new BigDecimal(330000), "tuntas", "Bukit Umbul 3E, Banyumanik", "Agung Setiawan",
                                     "Semarang", "Jawa Tengah", "blinkawan@gmail.com", "089667754239", new Date(2013,7,24), customer);
        
        OrderDetail detailPertama=new OrderDetail(1L,3,new BigDecimal(210000),new BigDecimal(70000),bookPertama,order);
        OrderDetail detailKedua=new OrderDetail(2L,2,new BigDecimal(120000),new BigDecimal(60000),bookKedua,order);
       
        order.getOrderDetails().add(detailPertama);
        order.getOrderDetails().add(detailKedua);
        
        EasyMock.expect(orderService.findOne(1L)).andReturn(order);
        EasyMock.replay(orderService);
        
        mockMvc.perform(get("/secured/my/order/detail/{orderId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(model().attribute("page", "customerorderdetail.jsp"))
                .andExpect(model().attribute("total", new BigDecimal(330000)))
                .andExpect(model().attribute("details", order.getOrderDetails()))
                .andExpect(model().attribute("details", hasSize(2)))
                .andExpect(model().attribute("details", hasItem(
                         allOf(
                                hasProperty("id", is(1L)),
                                hasProperty("amount", is(3)),
                                hasProperty("subTotal", is(new BigDecimal(210000))),
                                hasProperty("price", is(new BigDecimal(70000))),
                                hasProperty("book", is(bookPertama)),
                                hasProperty("order", is(order))
                               )
                )))
                .andExpect(model().attribute("details", hasItem(
                         allOf(
                                hasProperty("id", is(2L)),
                                hasProperty("amount", is(2)),
                                hasProperty("subTotal", is(new BigDecimal(120000))),
                                hasProperty("price", is(new BigDecimal(60000))),
                                hasProperty("book", is(bookPertama)),
                                hasProperty("order", is(order))
                               )
                )));
        
    }
}