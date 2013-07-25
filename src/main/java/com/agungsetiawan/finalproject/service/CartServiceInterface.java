package com.agungsetiawan.finalproject.service;

import com.agungsetiawan.finalproject.domain.Book;
import java.math.BigDecimal;
import java.util.Map;

/**
 *
 * @author awanlabs
 */
public interface CartServiceInterface {
    public Map<Book,Integer> findAll();
    public Book save(Book book);
    public Book update(Book book, Integer newQuantity);
    public Book delete(Book book);
    public void clear();
    public Integer size();
    public BigDecimal total();
}
