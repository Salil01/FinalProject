package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.domain.OrderDetail;
import com.agungsetiawan.finalproject.service.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author awanlabs
 */
@Controller
public class CustomerOrderController {
    
    @Autowired
    private OrderService orderService;
    
    public CustomerOrderController(){
        
    }
    
    public CustomerOrderController(OrderService orderService){
        this.orderService=orderService;
    }
    
    @RequestMapping(value = "secured/my/order",method = RequestMethod.GET)
    public String allOrder(Model model){
        String customer= SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> orders=orderService.findByCustomer(customer);
        model.addAttribute("orders", orders);
        model.addAttribute("page", "customerorder.jsp");
        return "templateno";
    }
    
    @RequestMapping(value = "secured/my/order/detail/{orderId}")
    public String detailOrder(Model model,@PathVariable("orderId") Long orderId ){
        Order order=orderService.findOne(orderId);
        List<OrderDetail> details=order.getOrderDetails();
        for(OrderDetail d:details){
            System.out.println(d.getBook().getTitle());
            System.out.println(d.getSubTotal());
        }
        model.addAttribute("details", details);
        model.addAttribute("total", order.getTotal());
        model.addAttribute("page", "customerorderdetail.jsp");
        return "templateno";
    }
}
