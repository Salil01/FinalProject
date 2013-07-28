package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.config.WebAppConfig;
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
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
        bookService=EasyMock.createMock(BookService.class);
        bookDetailController=new BookDetailController(bookService);
        view=EasyMock.createMock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(bookDetailController).setSingleView(view).build();
    }
    
    @Test
    public void bookDetailTest() throws Exception{
        List<Book> books=new ArrayList<Book>();
        Book book=new Book("Java tes", "Petar", "good book", new BigDecimal(30), "image");  
        Book book2=new Book("Java tes2", "Petar2", "good book2", new BigDecimal(32), "image2");
        books.add(book);
        books.add(book2);
               
        EasyMock.expect(bookService.findOne(2L)).andReturn(book2);
        EasyMock.replay(bookService);
        
        mockMvc.perform(get("/public/book/detail/{bookId}",2L))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
                .andExpect(model().attribute("page", "detail.jsp"))
                .andExpect(model().attribute("book", book2))
                .andExpect(model().attribute("book", hasProperty("title", is("Java tes2"))))
                .andExpect(model().attribute("book", hasProperty("author", is("Petar2"))))
                .andExpect(model().attribute("book", hasProperty("description", is("good book2"))))
                .andExpect(model().attribute("book", hasProperty("price", is(new BigDecimal(32)))))
                .andExpect(model().attribute("book", hasProperty("image", is("image2"))));
    }
}