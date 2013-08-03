package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CategoryService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import com.agungsetiawan.finalproject.util.CategoryBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import static org.hamcrest.Matchers.*;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
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
public class CategoryControllerTest {
    
    CategoryController categoryController;
    CategoryService categoryService;
    BookService bookService;
    View view;
    MockMvc mockMvc;
    
    @Before
    public void setUp() {
        categoryService=Mockito.mock(CategoryService.class);
        bookService=Mockito.mock(BookService.class);
        categoryController =new CategoryController(categoryService, bookService);
        view= Mockito.mock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(categoryController).setSingleView(view)
                .setViewResolvers(viewResolver()).build();
    }
    
    @Test
    public void categoryTest() throws Exception{
        Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("Spring in Nutshell")
                         .price(new BigDecimal(65000)).description("Spring book for intermediate")
                         .image("spring-in-nutshell").build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Category category=new CategoryBuilder().id(1L).name("Java").description("Java book category").build();
        
        Mockito.when(bookService.findByCategory(1L)).thenReturn(books);
        Mockito.when(categoryService.findOne(1L)).thenReturn(category);
        
        mockMvc.perform(get("/public/category/{categoryId}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/template.jsp"))
                .andExpect(model().attribute("page", is("grid.jsp")))
                .andExpect(model().attribute("h2title", "Category : Java"))
                .andExpect(model().attribute("books", hasSize(2)))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                               hasProperty("title", is("Java in Nutshell")),
                               hasProperty("author", is("Agung Setiawan")),
                               hasProperty("description", is("Java book for intermediate")),
                               hasProperty("price", is(new BigDecimal(85000))),
                               hasProperty("image", is("java-in-nutshell"))
                              )
                  )))
                .andExpect(model().attribute("books", hasItem(
                        allOf(
                               hasProperty("title", is("Spring in Nutshell")),
                               hasProperty("author", is("Markosvey")),
                               hasProperty("description", is("Spring book for intermediate")),
                               hasProperty("price", is(new BigDecimal(65000))),
                               hasProperty("image", is("spring-in-nutshell"))
                              )
                  )));
        
        Mockito.verify(bookService,Mockito.times(1)).findByCategory(1L);
        Mockito.verify(categoryService,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(bookService);
        Mockito.verifyNoMoreInteractions(categoryService);
                
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}