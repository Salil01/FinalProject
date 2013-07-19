package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.dao.CategoryDao;
import com.agungsetiawan.finalproject.domain.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@Service
@Transactional
public class CategoryServiceImpl implements CategoryService{
    
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public Category save(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public Category edit(Category category) {
        return categoryDao.edit(category);
    }

    @Override
    public Category delete(Category category) {
        return categoryDao.delete(category);
    }

    @Override
    public Category findOne(Long id) {
        return categoryDao.findOne(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
    
}
