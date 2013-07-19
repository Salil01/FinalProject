package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.type.IntegrationTest;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;
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
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfigTest.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class CustomerDaoHibernateImplIT {
    
    @Autowired
    private CustomerDao customerDao;

    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void saveTest(){
        Customer customer=new Customer("tromboso","password","Gajah Enggon","gajah@enggon.com","Wilwatikta","088999777666");
        Customer customerPersisted=customerDao.save(customer);
        
        assertNotNull(customerPersisted);
        assertNotNull(customerPersisted.getId());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findOneTest(){
        Customer customer=customerDao.findOne(1L);
        assertNotNull(customer);
        assertEquals("agung", customer.getUsername());
        assertEquals("blinkawan@gmail.com", customer.getEmail());
    }
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void findByUsernameTest(){
        Customer customer=customerDao.findByUsername("agung");
        assertNotNull(customer);
        assertEquals("Agung Setiawan", customer.getFullName());
        assertEquals("089667754239", customer.getPhone());
    }
}