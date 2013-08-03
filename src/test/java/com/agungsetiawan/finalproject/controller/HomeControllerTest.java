package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/**
 *
 * @author awanlabs
 */
public class HomeControllerTest {
    
    BookService bookService;
    HomeController homeController;
    View view;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        bookService= Mockito.mock(BookService.class);
        homeController=new HomeController(bookService);
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(homeController)
                 .setSingleView(view).setViewResolvers(viewResolver()).build();
    }
    
    @Test
    public void indexTest() throws Exception{
        
         Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("PHP in Nutshell")
                         .price(new BigDecimal(65000)).description("PHP book for intermediate")
                         .image("php-in-nutshell").build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Mockito.when(bookService.findAll()).thenReturn(books);
        
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/template.jsp"))
                .andExpect(model().attribute("page", "grid.jsp"))
                .andExpect(model().attribute("h2title", "New Book"))
                .andExpect(model().attribute("books", books))
                .andExpect(model().attribute("books",hasSize(2)))
                .andExpect(model().attribute("books", hasItem(
                 allOf(
                        hasProperty("id", is(1L)),
                        hasProperty("title", is("Java in Nutshell")),
                        hasProperty("author", is("Agung Setiawan")),
                        hasProperty("description", is("Java book for intermediate")),
                        hasProperty("price", is(new BigDecimal(85000))),
                        hasProperty("image", is("java-in-nutshell"))
                     )
                    )))
                .andExpect(model().attribute("books", hasItem(
                allOf(
                        hasProperty("id", is(2L)),
                        hasProperty("title", is("PHP in Nutshell")),
                        hasProperty("author", is("Markosvey")),
                        hasProperty("description", is("PHP book for intermediate")),
                        hasProperty("price", is(new BigDecimal(65000))),
                        hasProperty("image", is("php-in-nutshell"))
                    )
                    )));
        
        Mockito.verify(bookService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(bookService);
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}