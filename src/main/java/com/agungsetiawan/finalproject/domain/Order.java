package com.agungsetiawan.finalproject.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author awanlabs
 */
@Entity
@Table(name = "t_order")
public class Order implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal total;
    private String status;
    
    @NotEmpty(message="Alamat Pengiriman Harus diisi")
    @Column(name = "shipping_address")
    private String shippingAddress;
    
    @NotEmpty(message = "Nama Penerima Harus diisi")
    private String receiver;
    
    @NotEmpty(message = "Kota Harus diisi")
    private String city;
    
    @NotEmpty(message = "Provinsi Harus diisi")
    private String province;
    
    @NotEmpty(message = "Email Harus diisi")
    @Email(message = "Gunakan Format Email dengan Benar")
    @Column(name = "receiver_email")
    private String receiverEmail;
    
    @NotEmpty(message = "Telepon Harus diisi")
    @Column(name = "receiver_phone")
    private String receiverPhone;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "username_customer")
    Customer customer;
    
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    List<OrderDetail> orderDetails=new ArrayList<OrderDetail>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }
    
    
}
