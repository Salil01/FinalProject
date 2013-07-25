package com.agungsetiawan.finalproject.util;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import java.math.BigDecimal;

/**
 *
 * @author awanlabs
 */
public class BookBuilder {
    private Book book;

    public BookBuilder() {
        book=new Book();
    }
    
    public BookBuilder id(Long id){
        book.setId(id);
        return this;
    }
    
    public BookBuilder title(String title){
        book.setTitle(title);
        return this;
    }
    
    public BookBuilder author(String author){
        book.setAuthor(author);
        return this;
    }
    
    public BookBuilder description(String description){
        book.setDescription(description);
        return this;
    }
    
    public BookBuilder price(BigDecimal price){
        book.setPrice(price);
        return this;
    }
    
    public BookBuilder image(String image){
        book.setImage(image);
        return this;
    }
    
    public BookBuilder category(Category category){
        book.setCategory(category);
        return this;
    }
    
    public Book build(){
        return book;
    }
}
