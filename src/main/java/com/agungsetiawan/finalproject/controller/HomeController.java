package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CartService;
import com.agungsetiawan.finalproject.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 *
 * @author awanlabs
 */
@Controller
public class HomeController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private CartService cart;
    
    @ModelAttribute(value = "listCategory")
    public List<Category> listCategory(){
        return categoryService.findAll();
    }
    
    @ModelAttribute(value = "randomBooks")
    public List<Book> listBook(){
        return bookService.findAll();
    }
    
    @ModelAttribute(value = "cartSize")
    public Integer cartSize(){
        return cart.size();
    }
    
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("page", "grid.jsp");
        model.addAttribute("h2title", "New Book");
        model.addAttribute("books", bookService.findAll());
        return "template";
    }
       
}
