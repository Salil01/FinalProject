package com.agungsetiawan.finalproject.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author awanlabs
 */
@Entity
@Table(name = "t_order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue
    private Long id;
    private Integer amount;
    @Column(name = "sub_total")
    private BigDecimal subTotal;
    
    private BigDecimal price;
    
    @OneToOne
    @JoinColumn(name="id_book")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name = "id_order")
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(Long id, Integer amount, BigDecimal subTotal, BigDecimal price, Book book, Order order) {
        this.id = id;
        this.amount = amount;
        this.subTotal = subTotal;
        this.price = price;
        this.book = book;
        this.order = order;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
