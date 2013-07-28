package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CartServiceInterface;
import com.agungsetiawan.finalproject.service.CategoryService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
        categoryService=EasyMock.createMock(CategoryService.class);
        bookService=EasyMock.createMock(BookService.class);
        categoryController =new CategoryController(categoryService, bookService);
        view=EasyMock.createMock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(categoryController).setSingleView(view).build();
       
    }
    
    @Test
    public void categoryTest() throws Exception{
        List<Book> books=new ArrayList<Book>();
        Book book=new Book("Java tes", "Petar", "good book", new BigDecimal(30), "image");  
        Book book2=new Book("Java tes2", "Petar2", "good book2", new BigDecimal(32), "image2");
        books.add(book);
        books.add(book2);
        
        Category category=new Category("category", "description");
        
        EasyMock.expect(bookService.findByCategory(2L)).andReturn(books);
        EasyMock.replay(bookService);
        
        EasyMock.expect(categoryService.findOne(2L)).andReturn(category);
        EasyMock.replay(categoryService);
        
        mockMvc.perform(get("/public/category/{categoryId}",2L))
        .andExpect(status().isOk())
        .andExpect(view().name("template"))
        .andExpect(model().attribute("page", is("grid.jsp")))
        .andExpect(model().attribute("h2title", "Category : category"))
        .andExpect(model().attribute("books", hasSize(2)))
        .andExpect(model().attribute("books", hasItem(
                allOf(
                       hasProperty("title", is("Java tes")),
                       hasProperty("author", is("Petar")),
                       hasProperty("description", is("good book")),
                       hasProperty("price", is(new BigDecimal(30))),
                       hasProperty("image", is("image"))
                      )
          )))
        .andExpect(model().attribute("books", hasItem(
                allOf(
                       hasProperty("title", is("Java tes2")),
                       hasProperty("author", is("Petar2")),
                       hasProperty("description", is("good book2")),
                       hasProperty("price", is(new BigDecimal(32))),
                       hasProperty("image", is("image2"))
                      )
          )));
                
    }
}