package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.domain.Book;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author awanlabs
 */
@Repository
public class BookDaoHibernateImpl implements BookDao{
    
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public Book save(Book book) {
        sessionFactory.getCurrentSession().save(book);
        return book;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book edit(Book book) {
        sessionFactory.getCurrentSession().merge(book);
        return book;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book delete(Book book) {
        sessionFactory.getCurrentSession().delete(book);
        return book;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Book findOne(Long id) {
        return (Book) sessionFactory.getCurrentSession().get(Book.class, id);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findAll() {
        return sessionFactory.getCurrentSession().createQuery("select b from Book b").list();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findByCategory(Long id) {
        return sessionFactory.getCurrentSession().createQuery("select b from Book b where b.category.id=:id")
                .setParameter("id", id).list();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findByTitle(String title) {
        return sessionFactory.getCurrentSession().createQuery("select b from Book b where b.title like :title")
                .setParameter("title", "%"+title+"%").list();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Book> findRandom() {
        return sessionFactory.getCurrentSession().createQuery("SELECT b from Book b order by rand()").list();
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
