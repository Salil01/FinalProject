package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import com.agungsetiawan.finalproject.service.CartServiceInterface;
import com.agungsetiawan.finalproject.service.CustomerService;
import com.agungsetiawan.finalproject.service.OrderService;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author awanlabs
 */
@Controller
@SessionAttributes(value = "order")
public class CheckoutController {
    
    @Autowired
    private CartServiceInterface cart;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CustomerService customerService;

    public CheckoutController() {
    }

    public CheckoutController(CartServiceInterface cart, OrderService orderService, CustomerService customerService) {
        this.cart = cart;
        this.orderService = orderService;
        this.customerService = customerService;
    }
    
    @RequestMapping(value = "secured/checkout/1",method = RequestMethod.GET)
    public String checkout1(Model model){
        model.addAttribute("customer", customerService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("order", new Order());
        model.addAttribute("page", "checkout1.jsp");
        return "templateno";
    }
    
    @RequestMapping(value = "secured/checkout/2",method = RequestMethod.POST)
    public String checkout2(@Valid Order order, BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("page", "checkout1.jsp");
            model.addAttribute("showError", 1);
            model.addAttribute("customer", customerService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
            return "templateno";
        }
        model.addAttribute("customer", customerService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("page", "checkout2.jsp");
        model.addAttribute("order", order);
        model.addAttribute("books", cart.findAll().entrySet());
        model.addAttribute("total", cart.total());
        return "templateno";
    }
    
    @RequestMapping(value = "secured/checkout/finalize",method = RequestMethod.GET)
    public String finalize(Order order,Model model){
        System.out.println(order.getReceiver());
        BigDecimal subtotal,total=new BigDecimal(0);
        for(Map.Entry<Book,Integer> o:cart.findAll().entrySet()){
            OrderDetail detail=new OrderDetail();
            detail.setBook(o.getKey());
            detail.setAmount(o.getValue());
            detail.setSubTotal(o.getKey().getPrice().multiply(new BigDecimal(o.getValue())));
            detail.setPrice(o.getKey().getPrice());
            detail.setOrder(order);
            order.getOrderDetails().add(detail);
            
            subtotal=o.getKey().getPrice().multiply(new BigDecimal(o.getValue()));
            total=total.add(subtotal);
        }
        
        order.setTotal(total);
        order.setStatus("baru");
        order.setDate(new Date());
        order.setCustomer(customerService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()));
        
        orderService.save(order);
        model.addAttribute("name", customerService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getFullName());
        model.addAttribute("page", "checkoutsuccess.jsp");
        return "templateno";
    }
}
