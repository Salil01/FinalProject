package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import java.math.BigDecimal;
import java.util.Properties;
import org.junit.Test;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import static org.hamcrest.Matchers.*;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
/**
 *
 * @author awanlabs
 */
public class BookDetailControllerTest {
    
    BookDetailController bookDetailController;
    BookService bookService;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        bookService= Mockito.mock(BookService.class);
        bookDetailController=new BookDetailController(bookService);
        view=Mockito.mock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(bookDetailController).setSingleView(view)
                .setViewResolvers(viewResolver()).setHandlerExceptionResolvers(exceptionResolver()).build();
    }
    
    @Test
    public void bookDetailTest() throws Exception{
        Book bookToBeFound=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Mockito.when(bookService.findOne(1L)).thenReturn(bookToBeFound);
        
        mockMvc.perform(get("/public/book/detail/{bookId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/template.jsp"))
                .andExpect(model().attribute("page", "detail.jsp"))
                .andExpect(model().attribute("book", bookToBeFound))
                .andExpect(model().attribute("book", hasProperty("title", is("Java in Nutshell"))))
                .andExpect(model().attribute("book", hasProperty("author", is("Agung Setiawan"))))
                .andExpect(model().attribute("book", hasProperty("description", is("Java book for intermediate"))))
                .andExpect(model().attribute("book", hasProperty("price", is(new BigDecimal(85000)))))
                .andExpect(model().attribute("book", hasProperty("image", is("java-in-nutshell"))));
        
        Mockito.verify(bookService,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(bookService);
    }
    
    @Test
    public void bookDetailNotFoundTest() throws Exception{
        Mockito.when(bookService.findOne(1L)).thenReturn(null);
        
        mockMvc.perform(get("/public/book/detail/{bookId}",1L))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/404.jsp"));
        
        Mockito.verify(bookService,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(bookService);
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    
    private SimpleMappingExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("com.agungsetiawan.finalproject.exception.NotFoundException", "404");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("404", "404");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }
}