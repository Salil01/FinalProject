package com.agungsetiawan.finalproject.util;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public class CategoryBuilder {
    private Category category;

    public CategoryBuilder() {
        category=new Category();
    }
    
    public CategoryBuilder id(Long id){
        category.setId(id);
        return this;
    }
    
    public CategoryBuilder name(String name){
        category.setName(name);
        return this;
    }
    
    public CategoryBuilder description(String description){
        category.setDescription(description);
        return this;
    }
    
    public CategoryBuilder books(List<Book> books){
        category.setBookList(books);
        return this;
    }
    
    public Category build(){
        return category;
    }
}
