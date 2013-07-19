/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.BookDao;
import com.agungsetiawan.finalproject.domain.Book;
import java.math.BigDecimal;
import org.easymock.EasyMock;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author awanlabs
 */
public class BookServiceImplTest {
    
    BookServiceImpl bookServiceImpl;
    BookDao bookDao;
    
    @Before
    public void setUp(){
        bookServiceImpl=new BookServiceImpl();
        bookDao= EasyMock.createMock(BookDao.class);
        bookServiceImpl.setBookDao(bookDao);
    }
    
    @Test
    public void saveTest() {
        Book book=new Book("Java tes", null, null, new BigDecimal(30), null);
        
        Book book2=new Book("Java tes", null, null, new BigDecimal(30), null);
        book2.setId(1L);
        
        EasyMock.expect(bookDao.save(book)).andReturn(book2);
        EasyMock.replay(bookDao);
        
        Book bookPersisted=bookServiceImpl.save(book);
        assertNotNull(bookPersisted);
        assertNotNull(bookPersisted.getId());
    }
    
    @Test
    public void editTest(){
        Book book=new Book("Java tes", null, null, new BigDecimal(30), null);
        
        Book book2=new Book("Java tes edited", null, null, new BigDecimal(30), null);
        book2.setId(1L);
        
        EasyMock.expect(bookDao.edit(book)).andReturn(book2);
        EasyMock.replay(bookDao);
        
        Book bookEdited=bookServiceImpl.edit(book);
        assertNotNull(bookEdited);
        assertNotNull(bookEdited.getId());
    }
    
    @Test
    public void deleteTest(){
        Book book=new Book("Java tes", null, null, new BigDecimal(30), null);
        
        Book book2=new Book("Java tes deleted", null, null, new BigDecimal(30), null);
        book2.setId(1L);
        
        EasyMock.expect(bookDao.delete(book)).andReturn(book2);
        EasyMock.replay(bookDao);
        
        Book bookDeleted=bookServiceImpl.delete(book);
        assertNotNull(bookDeleted);
    }
}