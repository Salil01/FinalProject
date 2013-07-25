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
    CartServiceInterface cartService;
    CategoryService categoryService;
    BookService bookService;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        cartService= EasyMock.createMock(CartServiceInterface.class);
        categoryService=EasyMock.createMock(CategoryService.class);
        bookService=EasyMock.createMock(BookService.class);
        bookDetailController=new BookDetailController(cartService, categoryService, bookService);
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
        
        List<Category> categories=new ArrayList<Category>();
        Category category=new Category("category", "description");
        Category category2=new Category("category2", "description2");
        categories.add(category);
        categories.add(category2);
        
        EasyMock.expect(bookService.findRandom()).andReturn(books);        
        EasyMock.expect(bookService.findOne(2L)).andReturn(book2);
        EasyMock.replay(bookService);
        
        EasyMock.expect(categoryService.findAll()).andReturn(categories);
        EasyMock.replay(categoryService);
        
        EasyMock.expect(cartService.size()).andReturn(3);
        EasyMock.replay(cartService);
        
        mockMvc.perform(get("/public/book/detail/{bookId}",2L))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
                .andExpect(model().attribute("page", "detail.jsp"))
                .andExpect(model().attribute("book", book2))
                .andExpect(model().attribute("book", hasProperty("title", is("Java tes2"))))
                .andExpect(model().attribute("book", hasProperty("author", is("Petar2"))))
                .andExpect(model().attribute("book", hasProperty("description", is("good book2"))))
                .andExpect(model().attribute("book", hasProperty("price", is(new BigDecimal(32)))))
                .andExpect(model().attribute("book", hasProperty("image", is("image2"))))
                .andExpect(model().attribute("cartSize", 3))
                .andExpect(model().attribute("listCategory", hasSize(2)))
                .andExpect(model().attribute("listCategory", hasItem(
                    allOf(
                            hasProperty("name", is("category")),
                            hasProperty("description",is("description"))
                         )
                 )))
                .andExpect(model().attribute("listCategory", hasItem(
                    allOf(
                            hasProperty("name", is("category2")),
                            hasProperty("description",is("description2"))
                         )
                 )))
                .andExpect(model().attribute("randomBooks", hasSize(2)))
                .andExpect(model().attribute("randomBooks", hasItem(
                 allOf(
                        hasProperty("title", is("Java tes")),
                        hasProperty("author", is("Petar")),
                        hasProperty("description", is("good book")),
                        hasProperty("price", is(new BigDecimal(30))),
                        hasProperty("image", is("image"))
                     )
                    )))
                .andExpect(model().attribute("randomBooks", hasItem(
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