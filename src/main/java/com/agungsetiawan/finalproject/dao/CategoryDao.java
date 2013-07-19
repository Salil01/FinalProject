package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Category;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public interface CategoryDao {
    public Category save(Category category);
    public Category edit(Category category);
    public Category delete(Category category);
    public Category findOne(Long id);
    public List<Category> findAll();
}
