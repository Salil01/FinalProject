package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import com.agungsetiawan.finalproject.service.OrderService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import com.agungsetiawan.finalproject.util.CustomerBuilder;
import com.agungsetiawan.finalproject.util.OrderBuilder;
import com.agungsetiawan.finalproject.util.OrderDetailBuilder;
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
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
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
        orderService= Mockito.mock(OrderService.class);
        customerOrderController=new CustomerOrderController(orderService);
        view=Mockito.mock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(customerOrderController).setSingleView(view)
                .setViewResolvers(viewResolver()).build();
    }
    
    @Test
    public void allOrderTest() throws Exception{
        
        Customer customer=new CustomerBuilder().username("blinkawan").password("greatengineer")
                               .fullName("Agung Setiawan").email("blinkawan@gmail.com").address("Semarang")
                               .phone("089667754239").build();
        
        Order orderPertama=new OrderBuilder().id(1L).total(new BigDecimal(80000)).status("tuntas")
                                .shippingAddress("Bukit Umbul 3E, Banyumanik").receiver("Agung Setiawan")
                                .city("Semarang")
                                .province("Jawa Tengah").receiverEmail("blinkawan@gmail.com")
                                .receiverPhone("089667754239")
                                .date(new Date(2013,7,24)).customer(customer).build();
        
        Order orderKedua=new OrderBuilder().id(2L).total(new BigDecimal(90000)).status("baru")
                                .shippingAddress("Sapen, Boja").receiver("Hauril Maulida Nisfari")
                                .city("Kendal")
                                .province("Jawa Tengah").receiverEmail("aurielhaurilnisfari@yahoo.com")
                                .receiverPhone("089668237854")
                                .date(new Date(2013,7,24)).customer(customer).build();
        
        List<Order> orders=new ArrayList<Order>();
        orders.add(orderPertama);
        orders.add(orderKedua);
                
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_INVALID"));
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        Mockito.when(orderService.findByCustomer("blinkawan")).thenReturn(orders);
        
        mockMvc.perform(get("/secured/my/order"))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
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
        
        Mockito.verify(orderService,Mockito.times(1)).findByCustomer("blinkawan");
        Mockito.verifyNoMoreInteractions(orderService);
        
    }
    
    @Test
    public void detailOrderTest() throws Exception{
        Customer customer=new CustomerBuilder().username("blinkawan").password("greatengineer")
                               .fullName("Agung Setiawan").email("blinkawan@gmail.com").address("Semarang")
                               .phone("089667754239").build();
        
        Book bookPertama=new BookBuilder().id(1L).title("Java By Doing").author("Agung Setiawan")
                              .description("good book").price(new BigDecimal(70000))
                             .image("imagePertama").build();
        
        Book bookKedua=new BookBuilder().id(2L).title("Java Unit Testing").author("Akhtar")
                            .description("good book").price(new BigDecimal(60000))
                             .image("imageKedua").build();
        
        Order order=new OrderBuilder().id(1L).total(new BigDecimal(330000)).status("tuntas")
                         .shippingAddress("Bukit Umbul 3E, Banyumanik").receiver("Agung Setiawan")
                         .city("Semarang")
                         .province("Jawa Tengah").receiverEmail("blinkawan@gmail.com")
                         .receiverPhone("089667754239")
                         .date(new Date(2013,7,24)).customer(customer).build();
        
        OrderDetail detailPertama=new OrderDetailBuilder().id(1L).amount(3).subTotal(new BigDecimal(210000))
                                       .price(new BigDecimal(70000)).book(bookPertama).order(order).build();
        
        OrderDetail detailKedua=new OrderDetailBuilder().id(2L).amount(2).subTotal(new BigDecimal(120000))
                                       .price(new BigDecimal(60000)).book(bookKedua).order(order).build();
        
        order.getOrderDetails().add(detailPertama);
        order.getOrderDetails().add(detailKedua);
        
        Mockito.when(orderService.findOne(1L)).thenReturn(order);
        
        mockMvc.perform(get("/secured/my/order/detail/{orderId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
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
                                hasProperty("book", is(bookKedua)),
                                hasProperty("order", is(order))
                               )
                )));
        
        Mockito.verify(orderService, Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(orderService);
        
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}