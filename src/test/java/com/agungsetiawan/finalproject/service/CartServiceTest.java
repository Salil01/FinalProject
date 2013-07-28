package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.util.BookBuilder;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author awanlabs
 */
public class CartServiceTest{
    
    private CartService cartService;
    private Map<Book,Integer> books;

    @Before
    public void setUp() {
        cartService=new CartService();
        books=new HashMap<Book, Integer>();
        cartService.setBooks(books);
    }
    
    @Test
    public void saveTest(){
        Book bookToSave=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book actual=cartService.save(bookToSave);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(bookToSave, actual);
        Assert.assertEquals("Java Testing", actual.getTitle());
        Assert.assertEquals(1, cartService.size(),0);
    }
    
    @Test
    public void saveSameBookTest(){
        Book bookToSave=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookAnotherToSave=new BookBuilder().id(2l).title("Java Web Service").price(new BigDecimal(80000))
                            .image("java-web-service.jpg").description("good book java web service")
                            .author("Agung Setiawan").build();
        
        Book actual=cartService.save(bookToSave);
        Book actualAgain=cartService.save(bookToSave);
        Book bookWS=cartService.save(bookAnotherToSave);
        
        Integer quantity=books.get(bookToSave);
        
        Assert.assertEquals(bookToSave, actual);
        Assert.assertEquals(actual, actualAgain);
        Assert.assertEquals(3, cartService.size(),0);
        Assert.assertEquals(2, quantity,0);
    }
    
    @Test
    public void updateTest(){
        Book bookToUpdate=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        cartService.save(bookToUpdate);
        Integer quantity=books.get(bookToUpdate);
        
        Assert.assertEquals(1, quantity,0);
        
        cartService.update(bookToUpdate, 5);
        quantity=books.get(bookToUpdate);
        
        Assert.assertEquals(5, quantity,0);
    }
    
    @Test
    public void updateTestBelowOne(){
        Book bookToUpdate=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        cartService.save(bookToUpdate);
        Integer quantity=books.get(bookToUpdate);
        
        Assert.assertEquals(1, quantity,0);
        
        cartService.update(bookToUpdate, 0);
        quantity=books.get(bookToUpdate);
        
        Assert.assertNull(quantity);
    }
    
    @Test
    public void deleteTest(){
        Book bookToSave=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookSaved=cartService.save(bookToSave);
        Integer quantity=books.get(bookSaved);
        
        Assert.assertEquals(1, quantity,0);
        
        Book actual=cartService.delete(bookSaved);
        Integer quantityAfterDelete=books.get(bookSaved);
        
        Assert.assertNull(quantityAfterDelete);
    }
    
    @Test
    public void clearTest(){
        Book bookToSave=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookToSaveAgain=new BookBuilder().id(2l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookToSaveAgainAgain=new BookBuilder().id(3l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        cartService.save(bookToSave);
        cartService.save(bookToSaveAgain);
        cartService.save(bookToSaveAgainAgain);
        
        Integer quantity=books.size();
        
        Assert.assertEquals(3, quantity,0);
        
        cartService.clear();
        
        quantity=books.size();
        
        Assert.assertEquals(0, quantity,0);
    }
    
    @Test
    public void sizeTest(){
        Book bookToSave=new BookBuilder().id(1L).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookToSaveAgain=new BookBuilder().id(2L).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        cartService.save(bookToSave);
        cartService.save(bookToSave);
        cartService.save(bookToSaveAgain);
        
        Integer quantity=cartService.size();
        
        Assert.assertEquals(3, quantity,0);
    }
    
    @Test
    public void totalTest(){
        Book bookToSave=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookToSaveAgain=new BookBuilder().id(2l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookToSaveAgainAgain=new BookBuilder().id(3l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        cartService.save(bookToSave);
        cartService.save(bookToSaveAgain);
        cartService.save(bookToSaveAgainAgain);
        BigDecimal total=cartService.total();
        
        Assert.assertEquals(new BigDecimal(210000), total);
    }
    
    @Test
    public void findAllTest(){
        Book bookToSave=new BookBuilder().id(1l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookToSaveAgain=new BookBuilder().id(2l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        Book bookToSaveAgainAgain=new BookBuilder().id(3l).title("Java Testing").price(new BigDecimal(70000))
                            .image("java-testing.jpg").description("good book java testing")
                            .author("Agung Setiawan").build();
        
        cartService.save(bookToSave);
        cartService.save(bookToSaveAgain);
        cartService.save(bookToSaveAgainAgain);
        
        Map<Book,Integer> actual=cartService.findAll();
        
        Assert.assertEquals(3, actual.size());
    }
}