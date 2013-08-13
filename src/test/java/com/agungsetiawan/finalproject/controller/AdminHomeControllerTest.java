package com.agungsetiawan.finalproject.controller;

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

/**
 *
 * @author awanlabs
 */
public class AdminHomeControllerTest {
    
    AdminHomeController adminHomeController;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        adminHomeController=new AdminHomeController();
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(adminHomeController).setSingleView(view)
                .setViewResolvers(viewResolver()).build();
    }
    
    @Test
    public void indexTest() throws Exception{
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "beranda.jsp"));
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}