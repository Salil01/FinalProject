package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
public class SearchControllerTest {
    
    SearchController searchController;
    BookService bookService;
    View view;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        bookService= Mockito.mock(BookService.class);
        searchController=new SearchController(bookService);
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(searchController).setSingleView(view)
                 .setViewResolvers(viewResolver()).build();
    }
    
    @Test
    public void searchTest()throws Exception{
        Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("PHP in Nutshell")
                         .price(new BigDecimal(65000)).description("PHP book for intermediate")
                         .image("php-in-nutshell").build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Mockito.when(bookService.findByTitle("java")).thenReturn(books);
        
        mockMvc.perform(get("/public/search").param("key", "java"))
               .andExpect(status().isOk())
               .andExpect(view().name("template"))
               .andExpect(forwardedUrl("/WEB-INF/jsp/template.jsp"))
               .andExpect(model().attribute("page", "search.jsp"))
               .andExpect(model().attribute("h2title", "Pencarian Buku"))
               .andExpect(model().attribute("books", books));
        
        Mockito.verify(bookService,Mockito.times(1)).findByTitle("java");
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