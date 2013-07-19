package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Book;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public interface BookDao {
    public Book save(Book book);
    public Book edit(Book book);
    public Book delete(Book book);
    public Book findOne(Long id);
    public List<Book> findAll();
    public List<Book> findByCategory(Long id);
    public List<Book> findByTitle(String title);
    public List<Book> findRandom();
}
