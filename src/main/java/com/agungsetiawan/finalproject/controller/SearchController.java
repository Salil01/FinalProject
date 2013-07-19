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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author awanlabs
 */
@Controller
public class SearchController {
    @Autowired
    private CartService cart;
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private BookService bookService;
    
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
    
    @RequestMapping(value = "public/search",method = RequestMethod.GET)
    public String search(@RequestParam("key") String key,Model model){
        model.addAttribute("page", "search.jsp");
        model.addAttribute("h2title", "Pencarian Buku");
        List<Book> books=bookService.findByTitle(key);
        model.addAttribute("books", books);
        return "template";
    }
}
