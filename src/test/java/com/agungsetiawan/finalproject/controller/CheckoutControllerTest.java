package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.service.CartServiceInterface;
import com.agungsetiawan.finalproject.service.CustomerService;
import com.agungsetiawan.finalproject.service.OrderService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import com.agungsetiawan.finalproject.util.CustomerBuilder;
import com.agungsetiawan.finalproject.util.OrderBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.text.IsEmptyString;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author awanlabs
 */
public class CheckoutControllerTest {
    
    CartServiceInterface cartService;
    OrderService orderService;
    CustomerService customerService;
    CheckoutController checkoutController;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        cartService= Mockito.mock(CartServiceInterface.class);
        orderService=Mockito.mock(OrderService.class);
        customerService=Mockito.mock(CustomerService.class);
        checkoutController=new CheckoutController(cartService, orderService, customerService);
        
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(checkoutController).setSingleView(view)
                .setValidator(validator()).setViewResolvers(viewResolver()).build();
    }
    
    @Test
    public void checkout1Test() throws Exception{
        Customer customer=new CustomerBuilder().id(1L).fullName("Agung Setiawan").username("blinkawan")
                              .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                              .build();
        
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        Mockito.when(customerService.findByUsername("blinkawan")).thenReturn(customer);
        
        mockMvc.perform(get("/secured/checkout/1"))
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("page", "checkout1.jsp"))
                .andExpect(model().attribute("order", hasProperty("id", nullValue())))
                .andExpect(model().attribute("order", hasProperty("total", nullValue())))
                .andExpect(model().attribute("order", hasProperty("status",IsEmptyString.isEmptyOrNullString() )))
                .andExpect(model().attribute("order", hasProperty("shippingAddress", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("order", hasProperty("receiver", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("order", hasProperty("city", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("order", hasProperty("province", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("order", hasProperty("receiverEmail", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("order", hasProperty("receiverPhone", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("order", hasProperty("date", nullValue())))
                .andExpect(model().attribute("order", hasProperty("customer", nullValue())))
                .andExpect(model().attribute("customer", hasProperty("id", is(1L))))
                .andExpect(model().attribute("customer", hasProperty("fullName", is("Agung Setiawan"))))
                .andExpect(model().attribute("customer", hasProperty("username", is("blinkawan"))))
                .andExpect(model().attribute("customer", hasProperty("email", is("blinkawan@gmail.com"))))
                .andExpect(model().attribute("customer", hasProperty("address", is("Semarang"))));
        
        Mockito.verify(customerService,Mockito.times(1)).findByUsername("blinkawan");
        Mockito.verifyNoMoreInteractions(customerService);
    }
    
    @Test
    public void checkout2FailTest() throws Exception{
        Order order=new Order();
        
        Customer customer=new CustomerBuilder().id(1L).fullName("Agung Setiawan").username("blinkawan")
                              .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                              .build();
        
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        Mockito.when(customerService.findByUsername("blinkawan")).thenReturn(customer);
        
        mockMvc.perform(post("/secured/checkout/2")
               .param("shippingAddress", "Jalan Senopati 80")
               .param("city", "Bandung")
               .param("province", "Jawa Barat")
               .param("receiverPhone", "089668237854")
               .sessionAttr("order", order))
               .andExpect(status().isOk())
               .andExpect(view().name("templateno"))
               .andExpect(model().attribute("page", "checkout1.jsp"))
               .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
               .andExpect(model().attributeHasFieldErrors("order", "receiver"))
               .andExpect(model().attributeHasFieldErrors("order", "receiverEmail"))
               .andExpect(model().attribute("order", hasProperty("id", nullValue())))
               .andExpect(model().attribute("order", hasProperty("shippingAddress", is("Jalan Senopati 80"))))
               .andExpect(model().attribute("order", hasProperty("city", is("Bandung"))))
               .andExpect(model().attribute("order", hasProperty("province", is("Jawa Barat"))))
               .andExpect(model().attribute("order", hasProperty("receiverPhone", is("089668237854"))))
               .andExpect(model().attribute("customer", customer))
               .andExpect(model().attribute("customer", hasProperty("username", is("blinkawan"))));
        
        Mockito.verify(customerService,Mockito.times(1)).findByUsername("blinkawan");
        Mockito.verifyNoMoreInteractions(customerService);
        Mockito.verifyZeroInteractions(cartService);
    }
    
    @Test
    public void checkout2Test() throws Exception{
        
        Customer customer=new CustomerBuilder().id(1L).fullName("Agung Setiawan").username("blinkawan")
                              .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                              .build();
        
        Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("PHP in Nutshell")
                         .price(new BigDecimal(65000)).description("PHP book for intermediate")
                         .image("php-in-nutshell").build();
        
        Map<Book,Integer> map=new HashMap<Book, Integer>();
        map.put(bookOne, 1);
        map.put(bookTwo, 2);
        
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        Mockito.when(customerService.findByUsername("blinkawan")).thenReturn(customer);
        Mockito.when(cartService.findAll()).thenReturn(map);
        Mockito.when(cartService.total()).thenReturn(new BigDecimal(215000));
        
        Order order=new OrderBuilder().shippingAddress("Jalan Senopati 80").city("Bandung").province("Jawa Barat")
                        .receiverPhone("089668237854").receiverEmail("aurielhaurilnisfari@yahoo.com")
                        .receiver("Hauril Maulida Nisfari").build();
        
        mockMvc.perform(post("/secured/checkout/2")
               .param("receiver", "Hauril Maulida Nisfari")
               .param("shippingAddress", "Jalan Senopati 80")
               .param("city", "Bandung")
               .param("province", "Jawa Barat")
               .param("receiverPhone", "089668237854")
               .param("receiverEmail", "aurielhaurilnisfari@yahoo.com")
               .sessionAttr("order", order))
               .andExpect(model().attribute("page", "checkout2.jsp"))
               .andExpect(view().name("templateno"))
               .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
               .andExpect(model().attribute("order",hasProperty("receiver",is("Hauril Maulida Nisfari"))))
               .andExpect(model().attribute("order",hasProperty("receiverEmail", is("aurielhaurilnisfari@yahoo.com"))))
               .andExpect(model().attribute("order", hasProperty("id", nullValue())))
               .andExpect(model().attribute("order", hasProperty("shippingAddress", is("Jalan Senopati 80"))))
               .andExpect(model().attribute("order", hasProperty("city", is("Bandung"))))
               .andExpect(model().attribute("order", hasProperty("province", is("Jawa Barat"))))
               .andExpect(model().attribute("order", hasProperty("receiverPhone", is("089668237854"))))
               .andExpect(model().attribute("customer", customer))
               .andExpect(model().attribute("customer", hasProperty("username", is("blinkawan"))))
               .andExpect(model().attribute("books", map.entrySet()))
               .andExpect(model().attribute("total", new BigDecimal(215000)));
        
        Mockito.verify(customerService,Mockito.times(1)).findByUsername("blinkawan");
        Mockito.verify(cartService,Mockito.times(1)).findAll();
        Mockito.verify(cartService,Mockito.times(1)).total();
        Mockito.verifyNoMoreInteractions(customerService);
        Mockito.verifyNoMoreInteractions(cartService);
        
    }
    
    @Test
    public void finalizeOrder() throws Exception{
        Customer customer=new CustomerBuilder().id(1L).fullName("Agung Setiawan").username("blinkawan")
                              .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                              .build();
        
        Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("PHP in Nutshell")
                         .price(new BigDecimal(65000)).description("PHP book for intermediate")
                         .image("php-in-nutshell").build();
        
        Map<Book,Integer> map=new HashMap<Book, Integer>();
        map.put(bookOne, 1);
        map.put(bookTwo, 2);
        
        List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication auth=new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        
        Order orderToSave=new OrderBuilder().shippingAddress("Jalan Senopati 80").city("Bandung").province("Jawa Barat")
                        .receiverPhone("089668237854").receiverEmail("aurielhaurilnisfari@yahoo.com")
                        .receiver("Hauril Maulida Nisfari").build();
        
        Order orderSaved=new OrderBuilder().id(1L).shippingAddress("Jalan Senopati 80").city("Bandung").province("Jawa Barat")
                        .receiverPhone("089668237854").receiverEmail("aurielhaurilnisfari@yahoo.com")
                        .receiver("Hauril Maulida Nisfari").build();
        
        Mockito.when(customerService.findByUsername("blinkawan")).thenReturn(customer);
        Mockito.when(cartService.findAll()).thenReturn(map);
        Mockito.when(orderService.save(orderToSave)).thenReturn(orderSaved);
        
        mockMvc.perform(get("/secured/checkout/finalize"))
               .andExpect(view().name("templateno"))
               .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
               .andExpect(model().attribute("name", "Agung Setiawan"))
               .andExpect(model().attribute("page", "checkoutsuccess.jsp"))
               .andExpect(model().attribute("order", hasProperty("status", is("baru"))))
               .andExpect(model().attribute("order", hasProperty("customer", is(customer))))
               .andExpect(model().attribute("order", hasProperty("total", is(new BigDecimal(215000)))));
    }
    
    private LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}