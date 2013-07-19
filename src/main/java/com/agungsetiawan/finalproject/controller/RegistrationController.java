package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Customer;
import com.agungsetiawan.finalproject.service.CustomerService;
import javax.validation.Valid;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author awanlabs
 */
@Controller
public class RegistrationController {
    
    @Autowired
    private CustomerService customerService;
    
    @RequestMapping(value = "public/registration",method = RequestMethod.GET)
    public String registration(Model model,@RequestParam(value = "message",required = false) String message){
        model.addAttribute("page", "registration.jsp");
        model.addAttribute("customer", new Customer());
        model.addAttribute("message", message);
        return "templateno";
    }
    
    @RequestMapping(value = "public/registration/save",method = RequestMethod.POST)
    public String save(@Valid Customer customer, BindingResult result,Model model){
        if(result.hasErrors()){
            model.addAttribute("page", "registration.jsp");
            model.addAttribute("showError", 1);
            return "templateno";
        }else{
            customer.setPassword(DigestUtils.md5Hex(customer.getPassword()));
            customerService.save(customer);
            return "redirect:/public/registration?message=1";
        }
        
    }
}
