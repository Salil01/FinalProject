package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.type.IntegrationTest;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
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
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@org.junit.experimental.categories.Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfigTest.class)
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class CategoryDaoHibernateImplIT {
    
    @Autowired
    CategoryDao categoryDao;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void saveTest() {
        Category category=new Category("PHP", "PHP Book Category");
        Category categoryPersisted=categoryDao.save(category);
        assertNotNull(categoryPersisted);
        assertNotNull(categoryPersisted.getId());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void editTest(){
        Category category=categoryDao.findOne(1L);
        category.setName("Java Java");
        Category categoryUpdated=categoryDao.edit(category);
        
        assertNotNull(categoryUpdated);
        
        Category categoryNewName=categoryDao.findOne(1L);
        assertEquals("Java Java", categoryNewName.getName());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void deleteTest(){
        Category category=categoryDao.findOne(1L);
        Category categoryDeleted=categoryDao.delete(category);
        
        assertNotNull(categoryDeleted);
        
        Category categoryNull=categoryDao.findOne(1L);
        assertNull(categoryNull);
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findOneTest(){
        Category category=categoryDao.findOne(2L);
        
        assertNotNull(category);
        assertEquals(".NET", category.getName());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findAllTest(){
        List<Category> listCategory=categoryDao.findAll();
        
        assertNotNull(listCategory);
        assertEquals(2, listCategory.size());
        assertEquals("Java", listCategory.get(0).getName());
    }
}