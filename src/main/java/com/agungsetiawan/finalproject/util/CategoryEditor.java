package com.agungsetiawan.finalproject.util;

import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.CategoryService;
import java.beans.PropertyEditorSupport;

/**
 *
 * @author awanlabs
 */
public class CategoryEditor extends PropertyEditorSupport{
    CategoryService categoryService;

    public CategoryEditor(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @Override
    public void setAsText(String text){
        Category category=categoryService.findOne(Long.valueOf(text));
        setValue(category);
    }
}
