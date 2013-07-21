package com.agungsetiawan.finalproject.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author awanlabs
 */
@Entity
@Table(name = "t_customer")
public class Customer implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty(message = "Username Harus diisi")
    private String username;
    
    @NotEmpty(message = "Password Harus diisi")
    private String password;
    
    @NotEmpty(message = "Nama Harus diisi")
    @Column(name = "full_name")
    private String fullName;
    
    @Email(message = "Gunakan format Email dengan benar")
    @NotEmpty(message = "Email Harus diisi")
    private String email;
    
    @NotEmpty(message = "Alamat Harus diisi")
    private String address;
    
    @NotEmpty(message = "Telepon Harus diisi")
    private String phone;
    
    @OneToMany(mappedBy = "customer")
    List<Order> listOrder=new ArrayList<Order>();
    
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    
    public Customer(){}
    
    public Customer(String username,String password,String fullName,String email,String address,String phone){
        this.username=username;
        this.password=password;
        this.fullName=fullName;
        this.email=email;
        this.address=address;
        this.phone=phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Order> getListOrder() {
        return listOrder;
    }

    public void setListOrder(List<Order> listOrder) {
        this.listOrder = listOrder;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
}
