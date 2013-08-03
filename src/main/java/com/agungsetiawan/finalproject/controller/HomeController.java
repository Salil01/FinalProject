package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author awanlabs
 */
@Controller
public class HomeController {
    
    @Autowired
    private BookService bookService;

    public HomeController() {
    }

    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("page", "grid.jsp");
        model.addAttribute("h2title", "New Book");
        model.addAttribute("books", bookService.findAll());
        return "template";
    }
       
}
