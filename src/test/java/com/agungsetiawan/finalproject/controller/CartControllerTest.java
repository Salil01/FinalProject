package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CartServiceInterface;
import com.agungsetiawan.finalproject.util.BookBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.View;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
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
    
    @Before
    public void setUp(){
        cartService= Mockito.mock(CartServiceInterface.class);
        bookService=Mockito.mock(BookService.class);
        cartController=new CartController(cartService, bookService);
        view=Mockito.mock(View.class);
        mockMvc=MockMvcBuilders.standaloneSetup(cartController).setSingleView(view)
                .setViewResolvers(viewResolver()).setHandlerExceptionResolvers(exceptionResolver()).build();
    }
    
    @Test
    public void showCartTest() throws Exception{
        
        Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("PHP in Nutshell")
                         .price(new BigDecimal(65000)).description("PHP book for intermediate")
                         .image("php-in-nutshell").build();
        
        Map<Book,Integer> map=new HashMap<Book, Integer>();
        map.put(bookOne, 1);
        map.put(bookTwo, 2);
        
        Mockito.when(cartService.total()).thenReturn(new BigDecimal(215000));
        Mockito.when(cartService.findAll()).thenReturn(map);
        
        mockMvc.perform(get("/public/cart"))
                .andExpect(status().isOk())
                .andExpect(view().name("template"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/template.jsp"))
                .andExpect(model().attribute("page", "cart.jsp"))
                .andExpect(model().attribute("total", new BigDecimal(215000)))
                .andExpect(model().attribute("books", map.entrySet()));
        
        Mockito.verify(cartService,Mockito.times(1)).total();
        Mockito.verify(cartService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(cartService);
    }
    
    @Test
    public void addBookTest() throws Exception{
        
        Book bookToAdd=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Book bookAdded=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Mockito.when(bookService.findOne(1L)).thenReturn(bookToAdd);
        Mockito.when(cartService.save(bookToAdd)).thenReturn(bookAdded);
        
        mockMvc.perform(post("/public/cart/add/{bookId}",1L))
                .andExpect(view().name("redirect:/public/cart"))
                .andExpect(redirectedUrl("/public/cart?id=1"))
                .andExpect(status().is(302))
                .andExpect(model().attribute("id", 1L));
        
        Mockito.verify(bookService,Mockito.times(1)).findOne(1L);
        Mockito.verify(cartService,Mockito.times(1)).save(bookToAdd);
        Mockito.verifyNoMoreInteractions(bookService);
        Mockito.verifyNoMoreInteractions(cartService);
    }
//    
    @Test
    public void removeBookTest() throws Exception{
        Book bookToRemove=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Book bookRemoved=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Mockito.when(bookService.findOne(1L)).thenReturn(bookToRemove);
        Mockito.when(cartService.delete(bookToRemove)).thenReturn(bookRemoved);
        
        mockMvc.perform(get("/public/cart/remove/{bookId}", 1L))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/public/cart"))
                .andExpect(redirectedUrl("/public/cart?id=1"))
                .andExpect(model().attribute("id", 1L));
        
        Mockito.verify(bookService,Mockito.times(1)).findOne(1L);
        Mockito.verify(cartService,Mockito.times(1)).delete(bookToRemove);
        Mockito.verifyNoMoreInteractions(bookService);
        Mockito.verifyNoMoreInteractions(cartService);
    }
    
    @Test
    public void removeBookNotFound() throws Exception{
        Mockito.when(bookService.findOne(1L)).thenReturn(null);
        
        mockMvc.perform(get("/public/cart/remove/{bookId}",1L))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/404.jsp"));
        
        Mockito.verify(bookService,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(bookService);
    }
    
    @Test
    public void updateTest() throws Exception{
        Book bookToUpdate=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Book bookUpdated=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        
        Mockito.when(bookService.findOne(1L)).thenReturn(bookToUpdate);
        Mockito.when(cartService.update(bookToUpdate, 3)).thenReturn(bookUpdated);
        
        mockMvc.perform(post("/public/cart/update").param("bookId", "1").param("quantity", "3"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/public/cart"))
                .andExpect(redirectedUrl("/public/cart?id=1"))
                .andExpect(model().attribute("id", 1L));
        
        Mockito.verify(bookService,Mockito.times(1)).findOne(1L);
        Mockito.verify(cartService,Mockito.times(1)).update(bookToUpdate, 3);
        Mockito.verifyNoMoreInteractions(bookService);
        Mockito.verifyNoMoreInteractions(cartService);
    }
    
    @Test
    public void clearTest() throws Exception{
        
        mockMvc.perform(get("/public/cart/clear"))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/public/cart"))
                .andExpect(redirectedUrl("/public/cart"));
        
        Mockito.verify(cartService,Mockito.times(1)).clear();
        Mockito.verifyNoMoreInteractions(cartService);
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