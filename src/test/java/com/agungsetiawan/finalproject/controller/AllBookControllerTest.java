package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.junit.Test;
import static org.junit.Assert.*;
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
public class AllBookControllerTest {
    
    AllBookController allBookController;
    BookService bookService;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        allBookController=new AllBookController();
        bookService=EasyMock.createMock(BookService.class);
        allBookController.setBookService(bookService);
        view= EasyMock.createMock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(allBookController).setSingleView(view).build();
    }
    
    @Test
    public void allBookTest() throws Exception{
        
        List<Book> books=new ArrayList<Book>();
        Book book=new Book("Java tes", "Petar", "good book", new BigDecimal(30), "image");  
        Book book2=new Book("Java tes2", "Petar2", "good book2", new BigDecimal(32), "image2");
        books.add(book);
        books.add(book2);
        
        EasyMock.expect(bookService.findRandom()).andReturn(books);
        EasyMock.replay(bookService);
        
        mockMvc.perform(get("/public/book/all"))
                .andExpect(status().isOk())
                .andExpect(view().name("templateno"))
                .andExpect(model().attribute("page", "grid.jsp"))
                .andExpect(model().attribute("books", books))
                .andExpect(model().attribute("books",hasSize(2)))
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