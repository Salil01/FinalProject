package com.agungsetiawan.finalproject.util;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author awanlabs
 */
public class OrderBuilder {
    private Order order;

    public OrderBuilder() {
        order=new Order();
    }
    
    public OrderBuilder id(Long id){
        order.setId(id);
        return this;
    }
    
    public OrderBuilder total(BigDecimal total){
        order.setTotal(total);
        return this;
    }
    
    public OrderBuilder status(String status){
        order.setStatus(status);
        return this;
    }
    
    public OrderBuilder shippingAddress(String shippingAddress){
        order.setShippingAddress(shippingAddress);
        return this;
    }
    
    public OrderBuilder receiver(String receiver){
        order.setReceiver(receiver);
        return this;
    }
    
    public OrderBuilder city(String city){
        order.setCity(city);
        return this;
    }
    
    public OrderBuilder province(String province){
        order.setProvince(province);
        return this;
    }
    
    public OrderBuilder receiverEmail(String receiverEmail){
        order.setReceiverEmail(receiverEmail);
        return this;
    }
    
    public OrderBuilder receiverPhone(String receiverPhone){
        order.setReceiverPhone(receiverPhone);
        return this;
    }
    
    public OrderBuilder date(Date date){
        order.setDate(date);
        return this;
    }
    
    public OrderBuilder customer(Customer customer){
        order.setCustomer(customer);
        return this;
    }
    
    public OrderBuilder details(List<OrderDetail> details){
        order.setOrderDetails(details);
        return this;
    }
    
    public Order build(){
        return order;
    }
}
