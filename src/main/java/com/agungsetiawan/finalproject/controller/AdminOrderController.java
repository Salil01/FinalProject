package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.domain.Order;
import com.agungsetiawan.finalproject.exception.NotFoundException;
import com.agungsetiawan.finalproject.service.OrderService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author awanlabs
 */
@Controller
public class AdminOrderController {
    
    @Autowired
    OrderService orderService;

    public AdminOrderController() {
    }

    public AdminOrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @RequestMapping(value = "admin/order",method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("page", "order.jsp");
        return "admin/templateno";
    }
    
    @RequestMapping(value = "admin/order/detail/{id}",method = RequestMethod.GET)
    public String orderDetail(Model model,@PathVariable Long id) throws NotFoundException{
        Order order=orderService.findOne(id);
        if(order==null){
            throw new NotFoundException("Order Not Found");
        }
        
        Customer customer=order.getCustomer();
        model.addAttribute("customer", customer);
        model.addAttribute("order", order);
        model.addAttribute("details", order.getOrderDetails());
        model.addAttribute("page", "orderLengkap.jsp");
        return "admin/templateno";
    }
    
    @RequestMapping(value = "admin/order/edit",method = RequestMethod.POST)
    public String edit(@RequestParam Long id,@RequestParam String status,RedirectAttributes redirectAttributes){
        Order order=orderService.findOne(id);
        order.setStatus(status);
        orderService.edit(order);
        redirectAttributes.addFlashAttribute("title", order.getId());
        return "redirect:/admin/order?edit";
    }
}
