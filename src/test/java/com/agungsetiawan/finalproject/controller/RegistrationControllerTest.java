package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Role;
import com.agungsetiawan.finalproject.service.CustomerService;
import com.agungsetiawan.finalproject.service.RoleService;
import com.agungsetiawan.finalproject.util.CustomerBuilder;
import com.agungsetiawan.finalproject.util.RoleBuilder;
import org.apache.commons.codec.digest.DigestUtils;
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

/**
 *
 * @author awanlabs
 */
public class RegistrationControllerTest {
    
    RoleService roleService;
    CustomerService customerService;
    RegistrationController registrationController;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        roleService= Mockito.mock(RoleService.class);
        customerService=Mockito.mock(CustomerService.class);
        registrationController=new RegistrationController(customerService, roleService);
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(registrationController).setSingleView(view)
                 .setViewResolvers(viewResolver()).setValidator(validator()).build();
    }
    
    @Test
    public void registration() throws Exception{
        mockMvc.perform(get("/public/registration").param("message", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
                .andExpect(model().attribute("page", "registration.jsp"))
                .andExpect(model().attribute("message", ""))
                .andExpect(model().attribute("customer", hasProperty("id",nullValue())))
                .andExpect(model().attribute("customer", hasProperty("fullName",IsEmptyString.isEmptyOrNullString())));
    }
    
    @Test
    public void saveFailTest() throws Exception{
        mockMvc.perform(post("/public/registration/save")
                .param("password", "greatengineer")
                .param("email", "blinkawan@gmail.com")
                .param("address", "Semarang 90")
                .param("phone", "089667754239"))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
                .andExpect(model().attribute("page", "registration.jsp"))
                .andExpect(model().attribute("showError", 1))
                .andExpect(model().attributeHasFieldErrors("customer", "fullName"))
                .andExpect(model().attributeHasFieldErrors("customer", "username"))
                .andExpect(model().attribute("customer", hasProperty("id",nullValue())))
                .andExpect(model().attribute("customer", hasProperty("email",is("blinkawan@gmail.com"))));
        
    }
    
    @Test
    public void saveTest() throws Exception{
        Role role=new RoleBuilder().id(2L).name("customer").build();
        
        Customer customerToSave=new CustomerBuilder().fullName("Agung Setiawan").username("blinkawan")
                              .email("blinkawan@gmail.com").password("greatengineer")
                              .address("Semarang").phone("089667754239")
                              .build();
        
        Customer customerSaved=new CustomerBuilder().id(1L).fullName("Agung Setiawan").username("blinkawan")
                              .email("blinkawan@gmail.com")
                              .password(DigestUtils.md5Hex("greatengineer"))
                              .address("Semarang 90").phone("089667754239")
                              .role(role).build();
        
        Mockito.when(roleService.findOne(2L)).thenReturn(role);
        Mockito.when(customerService.save(customerToSave)).thenReturn(customerSaved);
        
        mockMvc.perform(post("/public/registration/save")
                .param("fullName",customerToSave.getFullName())
                .param("username",customerToSave.getUsername())
                .param("password",customerToSave.getPassword())
                .param("email",customerToSave.getEmail())
                .param("address",customerToSave.getAddress())
                .param("phone",customerToSave.getPhone())
                .sessionAttr("customer", customerToSave))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/public/registration?message=1"))
                .andExpect(redirectedUrl("/public/registration?message=1&id=1"))
                .andExpect(model().attribute("id", 1L));
        
        Mockito.verify(roleService,Mockito.times(1)).findOne(2L);
        Mockito.verify(customerService,Mockito.times(1)).save(customerToSave);
        Mockito.verifyNoMoreInteractions(roleService);
        Mockito.verifyNoMoreInteractions(customerService);
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