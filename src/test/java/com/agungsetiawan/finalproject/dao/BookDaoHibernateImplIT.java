package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.type.IntegrationTest;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.samples.context.WebContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@org.junit.experimental.categories.Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes=WebAppConfigTest.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class BookDaoHibernateImplIT {
    
    @Autowired
    BookDao bookDao;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void saveTest() {
        Book book=new Book("REST Spring", "Akhtar", "Good Book", new BigDecimal(30), "rest-spring.jpg");
        Book bookPersisted=bookDao.save(book);
        
        assertNotNull(bookPersisted);
        assertNotNull(bookPersisted.getId());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void editTest(){
        Book book=bookDao.findOne(1L);
        book.setTitle("Java Super Power");
        Book bookUpdated=bookDao.edit(book);
        
        assertNotNull(bookUpdated);
        
        Book bookNewTitle=bookDao.findOne(1L);
        assertEquals("Java Super Power", bookNewTitle.getTitle());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void deleteTest(){
        Book book=bookDao.findOne(2L);
        Book bookDeleted=bookDao.delete(book);
        
        assertNotNull(bookDeleted);
        
        Book bookNull=bookDao.findOne(2L);
        assertNull(bookNull);
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findOneTest(){
        Book book=bookDao.findOne(1L);
        assertNotNull(book);
        assertEquals("Java Super", book.getTitle());
        assertEquals("Agung Setiawan", book.getAuthor());
        assertEquals("Java", book.getCategory().getName());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findAllTest(){
        List<Book> listBook=bookDao.findAll();
        assertNotNull(listBook);
        assertEquals(3, listBook.size());
        assertEquals("Spring MVC", listBook.get(1).getTitle());
        assertEquals("Java", listBook.get(1).getCategory().getName());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findByCategoryTest(){
        List<Book> listBook=bookDao.findByCategory(1L);
        assertNotNull(listBook);
        assertEquals(2, listBook.size());
        assertEquals("Spring MVC", listBook.get(1).getTitle());
        assertEquals("Java", listBook.get(1).getCategory().getName());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findByTitleTest(){
        List<Book> listBook=bookDao.findByTitle("spring");
        assertNotNull(listBook);
        assertNotNull(listBook.get(0).getId());
    }
}