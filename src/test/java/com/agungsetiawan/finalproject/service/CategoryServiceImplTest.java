package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.CategoryDao;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.util.CategoryBuilder;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mockito;

/**
 *
 * @author awanlabs
 */
public class CategoryServiceImplTest {
    
    CategoryServiceImpl categoryServiceImpl;
    CategoryDao categoryDao;

    @Before
    public void setUp() {
        categoryDao= Mockito.mock(CategoryDao.class);
        categoryServiceImpl=new CategoryServiceImpl(categoryDao);
    }
    
    @Test
    public void saveTest(){
        Category categoryToSave=new CategoryBuilder().name("Java").description("Java Book Category").build();
        Category categorySaved=new CategoryBuilder().id(1L).name(categoryToSave.getName())
                                   .description(categoryToSave.getDescription()).build();
        
        Mockito.when(categoryDao.save(categoryToSave)).thenReturn(categorySaved);
        
        Category actual=categoryServiceImpl.save(categoryToSave);
        
        Mockito.verify(categoryDao,Mockito.times(1)).save(categoryToSave);
        Mockito.verifyNoMoreInteractions(categoryDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(categorySaved, actual);
        Assert.assertEquals("Java", actual.getName());
        Assert.assertEquals(categorySaved.getId(), actual.getId());
    }
    
    @Test
    public void editTest(){
        Category categoryToEdit=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        Category categoryEdited=new CategoryBuilder().id(categoryToEdit.getId()).name("Java Testing")
                                    .description(categoryToEdit.getDescription()).build();
        
        Mockito.when(categoryDao.edit(categoryToEdit)).thenReturn(categoryEdited);
        
        Category actual=categoryServiceImpl.edit(categoryToEdit);
        
        Mockito.verify(categoryDao,Mockito.times(1)).edit(categoryToEdit);
        Mockito.verifyNoMoreInteractions(categoryDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(categoryEdited, actual);
        Assert.assertNotEquals(categoryToEdit.getName(), actual.getName());
    }
    
    @Test
    public void deleteTest(){
        Category categoryToDelete=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        
        Mockito.when(categoryDao.delete(categoryToDelete)).thenReturn(categoryToDelete);
        
        Category actual=categoryServiceImpl.delete(categoryToDelete);
        
        Mockito.verify(categoryDao,Mockito.times(1)).delete(categoryToDelete);
        Mockito.verifyNoMoreInteractions(categoryDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(categoryToDelete, actual);
        Assert.assertEquals("Java Book Category", actual.getDescription());
    }
    
    @Test
    public void findOneTest(){
        Category categoryJava=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        
        Mockito.when(categoryDao.findOne(1L)).thenReturn(categoryJava);
        
        Category actual=categoryServiceImpl.findOne(1L);
        
        Mockito.verify(categoryDao,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(categoryDao);
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(categoryJava, actual);
        Assert.assertEquals("Java", actual.getName());
    }
    
    @Test
    public void findAllTest(){
        Category categoryJava=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        Category categoryPhp=new CategoryBuilder().id(2L).name("Php").description("Php Book Category").build();
        List<Category> categories=new ArrayList<Category>();
        categories.add(categoryJava);
        categories.add(categoryPhp);
        
        Mockito.when(categoryDao.findAll()).thenReturn(categories);
        
        List<Category> actual=categoryServiceImpl.findAll();
        
        Mockito.verify(categoryDao,Mockito.times(1)).findAll();
        
        Assert.assertNotNull(actual);
        Assert.assertEquals(categories, actual);
        Assert.assertEquals("Java", actual.get(0).getName());
        Assert.assertEquals("Php", actual.get(1).getName());
    }
}