package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.RoleDao;
import com.agungsetiawan.finalproject.domain.Role;
import com.agungsetiawan.finalproject.util.RoleBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 *
 * @author awanlabs
 */
public class RoleServiceImplTest {
    
    RoleDao roleDao;
    RoleServiceImpl roleServiceImpl;
    
    @Before
    public void setUp() {
        roleDao= Mockito.mock(RoleDao.class);
        roleServiceImpl=new RoleServiceImpl(roleDao);
    }
    
    @Test
    public void findOneTest(){
        Role role=new RoleBuilder().id(1L).name("ROLE_ADMIN").role(1).build();
        
        Mockito.when(roleDao.findOne(1L)).thenReturn(role);
        
        Role actual=roleServiceImpl.findOne(1L);
        
        Mockito.verify(roleDao,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(roleDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(role, actual);
        Assert.assertEquals("ROLE_ADMIN", actual.getName());
    }
}