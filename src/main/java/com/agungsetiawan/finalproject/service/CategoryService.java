/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.domain.Category;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public interface CategoryService {
    public Category save(Category category);
    public Category edit(Category category);
    public Category delete(Category category);
    public Category findOne(Long id);
    public List<Category> findAll();
}
