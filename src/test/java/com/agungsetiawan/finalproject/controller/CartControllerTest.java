package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CartServiceInterface;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
/**
 *
 * @author awanlabs
 */
public class CartControllerTest {
    
    CartController cartController;
    CartServiceInterface cartService;
    BookService bookService;
    View view;
    MockMvc mockMvc;
    
    List<Book> books;
    List<Category> categories;
    Book book;
    Book book2;
    
    @Before
    public void setUp(){
        cartService= EasyMock.createMock(CartServiceInterface.class);
        bookService=EasyMock.createMock(BookService.class);
        cartController=new CartController(cartService, bookService);
        view=EasyMock.createMock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(cartController).setSingleView(view).build();
        
        books=new ArrayList<Book>();
        book=new Book("Java tes", "Petar", "good book", new BigDecimal(30), "image");  
        book2=new Book("Java tes2", "Petar2", "good book2", new BigDecimal(32), "image2");
        books.add(book);
        books.add(book2);
    }
    
    @Test
    public void showCartTest() throws Exception{
        Map<Book,Integer> mapBooks=new HashMap<Book, Integer>();
        mapBooks.put(book, 2);
        mapBooks.put(book2, 2);
        
        EasyMock.expect(cartService.findAll()).andReturn(mapBooks);
        EasyMock.expect(cartService.total()).andReturn(new BigDecimal(124));
        EasyMock.replay(cartService);
        
        mockMvc.perform(get("/public/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
//                .andExpect(forwardedUrl("/WEB-INF/jsp/template.jsp"))
                .andExpect(model().attribute("page", "cart.jsp"))
                .andExpect(model().attribute("total", new BigDecimal(124)))
                .andExpect(model().attribute("books", mapBooks.entrySet()));
    }
    
    @Test
    public void addBookTest() throws Exception{
        Book bookSaved=new Book("Java By Doing", "Agung Setiawan", "Good Java Book", new BigDecimal(40), "java.jpg");
        bookSaved.setId(5L);
        
        EasyMock.expect(bookService.findOne(1L)).andReturn(book);
        EasyMock.replay(bookService);
        
        EasyMock.expect(cartService.save(book)).andReturn(bookSaved);
        EasyMock.replay(cartService);
        
        mockMvc.perform(post("/public/cart/add/1"))
                .andExpect(view().name("redirect:/public/cart"))
//                .andExpect(redirectedUrl("/public/cart"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 5L));
    }
    
    @Test
    public void removeBookTest() throws Exception{
        Book bookx=new Book("Java By Doing", "Agung Setiawan", "Good Java Book", new BigDecimal(40), "java.jpg");
        bookx.setId(5L);
        Book bookDeleted=new Book("Java By Doing", "Agung Setiawan", "Good Java Book", new BigDecimal(40), "java.jpg");
        bookDeleted.setId(5L);
        
        EasyMock.expect(bookService.findOne(2L)).andReturn(bookx);
        EasyMock.replay(bookService);
        
        EasyMock.expect(cartService.delete(bookx)).andReturn(bookDeleted);
        EasyMock.replay(cartService);
        
        mockMvc.perform(get("/public/cart/remove/{bookId}", 2L))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/public/cart"))
                .andExpect(model().attribute("id", 5L));
    }
    
    @Test
    public void updateBookTest() throws Exception{
        Book bookToUpdate=new Book("Java By Doing", "Agung Setiawan", "Good Java Book", new BigDecimal(40), "java.jpg");
        bookToUpdate.setId(5L);
        Book bookUpdated=new Book("Java By Doing Updated", "Agung Setiawan", "Good Java Book", new BigDecimal(40), "java.jpg");
        bookUpdated.setId(5L);
        
        EasyMock.expect(bookService.findOne(5L)).andReturn(bookToUpdate);
        EasyMock.expect(cartService.update(bookToUpdate, 2)).andReturn(bookUpdated);
        EasyMock.replay(bookService);
        EasyMock.replay(cartService);
        
        mockMvc.perform(post("/public/cart/update").param("bookId", "5").param("quantity", "2"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/public/cart"))
                .andExpect(model().attribute("id", 5L));
    }
    
    @Test
    public void clearTest() throws Exception{
        EasyMock.expect(cartService.size()).andReturn(2);
        cartService.clear();
        EasyMock.expectLastCall();
        EasyMock.replay(cartService);
        
        mockMvc.perform(get("/public/cart/clear"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/public/cart"));
    }
}