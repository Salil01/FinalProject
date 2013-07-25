package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.CustomerDao;
import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.util.CustomerBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author awanlabs
 */
public class CustomerServiceImplTest {
    
    CustomerServiceImpl customerServiceImpl;
    CustomerDao customerDao;
    
    @Before
    public void setUp() {
        customerDao= Mockito.mock(CustomerDao.class);
        customerServiceImpl=new CustomerServiceImpl(customerDao);
    }
    
    @Test
    public void saveTest(){
        Customer customerToSave=new CustomerBuilder().username("blinkawan").fullName("Agung Setiawan")
                                    .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                                    .phone("089667754239").build();
        
        Customer customerSaved=new CustomerBuilder().id(1L).username(customerToSave.getUsername())
                                   .fullName(customerToSave.getFullName()).email(customerToSave.getEmail())
                                   .password(customerToSave.getPassword()).address(customerToSave.getAddress())
                                   .phone(customerToSave.getPassword()).build();
        
        Mockito.when(customerDao.save(customerToSave)).thenReturn(customerSaved);
        
        Customer actual=customerServiceImpl.save(customerToSave);
        
        Mockito.verify(customerDao,Mockito.times(1)).save(customerToSave);
        Mockito.verifyNoMoreInteractions(customerDao);
        
        Assert.assertNotNull(actual);
        Assert.assertNotNull(actual.getId());
        Assert.assertEquals(customerSaved, actual);
        Assert.assertEquals("blinkawan", actual.getUsername());
    }
    
    @Test
    public void findOneTest(){
        Customer customer=new CustomerBuilder().id(1L).username("blinkawan").fullName("Agung Setiawan")
                                    .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                                    .phone("089667754239").build();
        
        Mockito.when(customerDao.findOne(1L)).thenReturn(customer);
        
        Customer actual=customerServiceImpl.findOne(1L);
        
        Mockito.verify(customerDao,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(customerDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(customer, actual);
        Assert.assertEquals("Agung Setiawan", actual.getFullName());
    }
    
    @Test
    public void findByUsername(){
        Customer customer=new CustomerBuilder().id(1L).username("blinkawan").fullName("Agung Setiawan")
                                    .email("blinkawan@gmail.com").password("greatengineer").address("Semarang")
                                    .phone("089667754239").build();
        
        Mockito.when(customerDao.findByUsername("blinkawan")).thenReturn(customer);
        
        Customer actual=customerServiceImpl.findByUsername("blinkawan");
        
        Mockito.verify(customerDao,Mockito.times(1)).findByUsername("blinkawan");
        Mockito.verifyNoMoreInteractions(customerDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(customer, actual);
        Assert.assertEquals("Agung Setiawan", actual.getFullName());
    }
}