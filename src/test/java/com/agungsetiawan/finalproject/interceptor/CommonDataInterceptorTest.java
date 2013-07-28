package com.agungsetiawan.finalproject.interceptor;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import com.agungsetiawan.finalproject.util.CategoryBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import static org.hamcrest.Matchers.*;
/**
 *
 * @author awanlabs
 */
public class CommonDataInterceptorTest {
    
    private CommonDataInterceptor commonDataInterceptor;
    private BookService bookService;

    @Before
    public void setUp() {
        bookService= Mockito.mock(BookService.class);
        commonDataInterceptor=new CommonDataInterceptor(bookService);
    }
    
    @Test
    public void postHandleTest() throws Exception{
        Book bookOne=new BookBuilder().id(1L).title("Java Testing").author("Agung Setiawan")
                            .price(new BigDecimal(70000)).description("Good book about Java Testing Technique")
                            .image("java-testing.jpg").category(new CategoryBuilder().build()).build();
        
        Book bookTwo=new BookBuilder().id(2L).title("Java Security").author("Agung Setiawan")
                            .price(new BigDecimal(80000)).description("Good book about Java Security Technique")
                            .image("java-security.jpg").category(new CategoryBuilder().build()).build();
        
//        Book bookThree=new BookBuilder().id(3L).title("Java Security").author("Agung Setiawan")
//                            .price(new BigDecimal(80000)).description("Good book about Java Security Technique")
//                            .image("java-security.jpg").category(new CategoryBuilder().build()).build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
//        List<Book> booksTwo=new ArrayList<Book>();
//        booksTwo.add(bookThree);
        
        Mockito.when(bookService.findRandom()).thenReturn(books);
        
        ModelMap modelMap=new ModelMap();
        WebRequest webRequest=Mockito.mock(WebRequest.class);
        
        commonDataInterceptor.postHandle(webRequest, modelMap);
        Assert.assertThat(modelMap, hasEntry("randomBooks",(Object) books));
    }
}