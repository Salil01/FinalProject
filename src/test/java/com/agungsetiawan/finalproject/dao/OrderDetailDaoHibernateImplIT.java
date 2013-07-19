package com.agungsetiawan.finalproject.dao;

import com.agungsetiawan.finalproject.config.WebAppConfigTest;
import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import com.agungsetiawan.finalproject.type.IntegrationTest;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author awanlabs
 */
@Category(IntegrationTest.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = WebAppConfigTest.class)
@Transactional
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, TransactionDbUnitTestExecutionListener.class})
public class OrderDetailDaoHibernateImplIT {
    
    @Autowired
    private OrderDetailDao orderDetailDao;
    
    @Autowired
    private BookDao bookDao;
    
    @Test
    @DatabaseSetup("classpath:sampleData.xml")
    public void saveTest() {
        Book book=bookDao.findOne(2L);
        
        OrderDetail detail=new OrderDetail();
        detail.setBook(book);
        detail.setAmount(2);
        detail.setSubTotal(book.getPrice().multiply(new BigDecimal(detail.getAmount())));
        detail.setOrder(new Order());
        
        OrderDetail detailPersisted=orderDetailDao.save(detail);
        assertNotNull(detailPersisted);
        assertNotNull(detailPersisted.getId());
//        System.out.println(detailPersisted.getId());
    }
}