package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.domain.Book;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 *
 * @author awanlabs
 */
public class CartService implements Serializable,CartServiceInterface{
    private Map<Book,Integer> books=new HashMap<Book, Integer>();
    
    @Override
    public Map<Book,Integer> findAll(){
        return Collections.unmodifiableMap(this.books);
    }
    
    @Override
    public Book save(Book book){
        if(this.books.containsKey(book)){
            Integer quantity=this.books.get(book);
            quantity++;
            this.books.put(book, quantity);
            return book;
        }else{
            this.books.put(book, 1);
            return book;
        }
    }
    
    @Override
    public Book update(Book book, Integer newQuantity){
        if(newQuantity<1){
            this.books.remove(book);
        }else{
            this.books.put(book, newQuantity);
        }
        return book;
    }
    
    @Override
    public Book delete(Book book){
        this.books.remove(book);
        return book;
    }
    
    @Override
    public void clear(){
        this.books.clear();
    }
    
    @Override
    public Integer size(){
        Integer size=0;
        for(Integer i:this.books.values()){
            size+=i;
        }
        return size;
    }
    
    @Override
    public BigDecimal total(){
        BigDecimal subtotal,total=new BigDecimal(0);
       
        for(Entry<Book,Integer> o:findAll().entrySet()){
            subtotal=o.getKey().getPrice().multiply(new BigDecimal(o.getValue()));
            total=total.add(subtotal);
        }
        
        return total;
    }
    
    @Override
    public String toString(){
        return this.books.keySet().toString();
    }
}
