package com.agungsetiawan.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author awanlabs
 */
@Controller
public class AdminHomeController {
    
    @RequestMapping(value = "admin")
    public String index(Model model){
        model.addAttribute("page", "beranda.jsp");
        return "admin/templateno";
    }
}
