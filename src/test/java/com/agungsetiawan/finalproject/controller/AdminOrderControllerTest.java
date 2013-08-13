package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.exception.NotFoundException;
import com.agungsetiawan.finalproject.service.OrderService;
import com.agungsetiawan.finalproject.util.CustomerBuilder;
import com.agungsetiawan.finalproject.util.OrderBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 *
 * @author awanlabs
 */
public class AdminOrderControllerTest {
    
    AdminOrderController adminOrderController;
    OrderService orderService;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        orderService= Mockito.mock(OrderService.class);
        adminOrderController=new AdminOrderController(orderService);
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(adminOrderController).setSingleView(view)
                .setViewResolvers(viewResolver())
                .setHandlerExceptionResolvers(getSimpleMappingExceptionResolver()).build();
    }
    
    @Test
    public void indexTest() throws Exception{
        Customer customer=new CustomerBuilder().username("blinkawan").password("greatengineer")
                               .fullName("Agung Setiawan").email("blinkawan@gmail.com").address("Semarang")
                               .phone("089667754239").build();
        Order orderOne=new OrderBuilder().id(1L).total(new BigDecimal(80000)).status("tuntas")
                                .shippingAddress("Bukit Umbul 3E, Banyumanik").receiver("Agung Setiawan")
                                .city("Semarang")
                                .province("Jawa Tengah").receiverEmail("blinkawan@gmail.com")
                                .receiverPhone("089667754239")
                                .date(new Date(2013,7,24)).customer(customer).build();
        Order orderTwo=new OrderBuilder().id(2L).total(new BigDecimal(90000)).status("baru")
                                .shippingAddress("Sapen, Boja").receiver("Hauril Maulida Nisfari")
                                .city("Kendal")
                                .province("Jawa Tengah").receiverEmail("aurielhaurilnisfari@yahoo.com")
                                .receiverPhone("089668237854")
                                .date(new Date(2013,7,24)).customer(customer).build();
        
        List<Order> orders=new ArrayList<Order>();
        orders.add(orderOne);
        orders.add(orderTwo);
        
        Mockito.when(orderService.findAll()).thenReturn(orders);
        
        mockMvc.perform(get("/admin/order"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "order.jsp"))
                .andExpect(model().attribute("orders", orders))
                .andExpect(model().attribute("orders",hasSize(2)))
                .andExpect(model().attribute("orders", hasItem(  
                    allOf(
                        hasProperty("receiver", is("Agung Setiawan")),
                        hasProperty("status",is("tuntas"))
                    )
                )))
                .andExpect(model().attribute("orders", hasItem(  
                    allOf(
                        hasProperty("receiver", is("Hauril Maulida Nisfari")),
                        hasProperty("status",is("baru"))
                    )
                )));
        
        Mockito.verify(orderService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(orderService);
        
    }
    
    @Test
    public void orderDetailNotFoundTest() throws Exception{
        
        Mockito.when(orderService.findOne(1L)).thenReturn(null);
        
        mockMvc.perform(get("/admin/order/detail/{id}",1L))
                .andExpect(view().name("404s"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/404s.jsp"));
                
    }
    
    @Test
    public void orderDetailTest() throws Exception{
        Customer customer=new CustomerBuilder().username("blinkawan").password("greatengineer")
                               .fullName("Agung Setiawan").email("blinkawan@gmail.com").address("Semarang")
                               .phone("089667754239").build();
        Order orderOne=new OrderBuilder().id(1L).total(new BigDecimal(80000)).status("tuntas")
                                .shippingAddress("Bukit Umbul 3E, Banyumanik").receiver("Agung Setiawan")
                                .city("Semarang")
                                .province("Jawa Tengah").receiverEmail("blinkawan@gmail.com")
                                .receiverPhone("089667754239")
                                .date(new Date(2013,7,24)).customer(customer).build();
        
        Mockito.when(orderService.findOne(1L)).thenReturn(orderOne);
        
        mockMvc.perform(get("/admin/order/detail/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "orderLengkap.jsp"))
                .andExpect(model().attribute("customer", customer))
                .andExpect(model().attribute("order", orderOne))
                .andExpect(model().attribute("details", orderOne.getOrderDetails()));
    }
    
    @Test
    public void editTest() throws Exception{
        Customer customer=new CustomerBuilder().username("blinkawan").password("greatengineer")
                               .fullName("Agung Setiawan").email("blinkawan@gmail.com").address("Semarang")
                               .phone("089667754239").build();
        Order orderOne=new OrderBuilder().id(1L).total(new BigDecimal(80000)).status("tuntas")
                                .shippingAddress("Bukit Umbul 3E, Banyumanik").receiver("Agung Setiawan")
                                .city("Semarang")
                                .province("Jawa Tengah").receiverEmail("blinkawan@gmail.com")
                                .receiverPhone("089667754239")
                                .date(new Date(2013,7,24)).customer(customer).build();
        
         Mockito.when(orderService.findOne(1L)).thenReturn(orderOne);
         
         mockMvc.perform(post("/admin/order/edit").param("id", "1").param("status", "selesai"))
                 .andExpect(status().is(302))
                 .andExpect(view().name("redirect:/admin/order?edit"))
                 .andExpect(redirectedUrl("/admin/order?edit"))
                 .andExpect(flash().attribute("title", 1L));
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put(NotFoundException.class.getName(), "404s");
        exceptionMappings.put(ConstraintViolationException.class.getName(), "409");
//        exceptionMappings.put("java.lang.Exception", "404");
//        exceptionMappings.put("java.lang.RuntimeException", "404");

        exceptionResolver.setExceptionMappings(exceptionMappings);
//        exceptionResolver.setDefaultErrorView("404s");
        exceptionResolver.setOrder(0);
//        Properties statusCodes = new Properties();
//
//        statusCodes.put("404", "404");
//        statusCodes.put("error/error", "500");
//
//        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }
}