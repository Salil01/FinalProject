package com.agungsetiawan.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author awanlabs
 */
@Controller
public class LoginController {
    
    @RequestMapping(value = "public/login/form")
    public String loginForm(Model model){
        model.addAttribute("page", "login.jsp");
        return "templateno";
    }
}
