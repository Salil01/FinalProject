package com.agungsetiawan.finalproject.util;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import java.math.BigDecimal;

/**
 *
 * @author awanlabs
 */
public class OrderDetailBuilder {
    private OrderDetail orderDetail;

    public OrderDetailBuilder() {
        orderDetail=new OrderDetail();
    }
    
    public OrderDetailBuilder id(Long id){
        orderDetail.setId(id);
        return this;
    }
    
    public OrderDetailBuilder amount(Integer amount){
        orderDetail.setAmount(amount);
        return this;
    }
    
    public OrderDetailBuilder subTotal(BigDecimal subTotal){
        orderDetail.setSubTotal(subTotal);
        return this;
    }
    
    public OrderDetailBuilder price(BigDecimal price){
        orderDetail.setPrice(price);
        return this;
    }
    
    public OrderDetailBuilder book(Book book){
        orderDetail.setBook(book);
        return this;
    }
    
    public OrderDetailBuilder order(Order order){
        orderDetail.setOrder(order);
        return this;
    }
    
    public OrderDetail build(){
        return orderDetail;
    }
}

