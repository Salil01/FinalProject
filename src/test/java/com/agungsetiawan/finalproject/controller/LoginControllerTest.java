package com.agungsetiawan.finalproject.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
/**
 *
 * @author awanlabs
 */
public class LoginControllerTest {
    
    LoginController loginController;
    View view;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        loginController=new LoginController();
        view= Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(loginController)
                .setSingleView(view).setViewResolvers(viewResolver()).build();
    }
    
    @Test
    public void loginForm()throws Exception{
        mockMvc.perform(get("/public/login/form"))
               .andExpect(status().isOk())
               .andExpect(view().name("templateno"))
               .andExpect(forwardedUrl("/WEB-INF/jsp/templateno.jsp"))
               .andExpect(model().attribute("page", "login.jsp"));
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}