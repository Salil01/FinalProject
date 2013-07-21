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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author awanlabs
 */
@Controller
public class BookDetailController {
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
    
    @RequestMapping(value = "/public/book/detail/{bookId}",method = RequestMethod.GET)
    public String bookDetail(@PathVariable("bookId") Long bookId, Model model){
        Book book=bookService.findOne(bookId);
        model.addAttribute("page", "detail.jsp");
        model.addAttribute("book", book);
        return "template";
    }
}
