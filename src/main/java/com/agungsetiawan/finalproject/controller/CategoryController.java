package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private BookService bookService;
    
    public CategoryController(){
        
    }
    
    public CategoryController(CategoryService categoryService,BookService bookService){
        this.categoryService=categoryService;
        this.bookService=bookService;
    }
    
    @RequestMapping(value = "public/category/{categoryId}",method = RequestMethod.GET)
    public String category(@PathVariable("categoryId") Long categoryId, Model model){
        
        List<Book> listBook=bookService.findByCategory(categoryId);
        Category category=categoryService.findOne(categoryId);
        
        model.addAttribute("page", "grid.jsp");
        model.addAttribute("books", listBook);
        model.addAttribute("h2title","Category : "+category.getName());
        
        return "template";
    }
}
