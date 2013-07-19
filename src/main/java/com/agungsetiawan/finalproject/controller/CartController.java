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
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author awanlabs
 */
@Controller
public class CartController {
    @Autowired
    private CartService cart;
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private CategoryService categoryService;
    
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
    
    @RequestMapping(value = "public/cart",method = RequestMethod.GET)
    public String showCart(Model model){
        model.addAttribute("page", "cart.jsp");
        model.addAttribute("books", cart.findAll().entrySet());
        model.addAttribute("total", cart.total());
        return "template";
    }
    
    @RequestMapping(value = "public/cart/add/{bookId}",method = RequestMethod.POST)
    public String addBook(@PathVariable("bookId") Long bookId, Model model){
        Book book=bookService.findOne(bookId);
        cart.save(book);
        return "redirect:/public/cart";
    }
    
    @RequestMapping(value = "public/cart/remove/{bookId}",method = RequestMethod.GET)
    public String removeBook(@PathVariable("bookId") Long bookId ,Model model){
        Book book=bookService.findOne(bookId);
        cart.delete(book);
        return "redirect:/public/cart";
    }
    
    @RequestMapping(value = "public/cart/update",method = RequestMethod.POST)
    public String update(@RequestParam(value = "bookId") Long bookId,@RequestParam(value = "quantity") Integer quantity){
        Book book=bookService.findOne(bookId);
        cart.update(book, quantity);
        return "redirect:/public/cart";
    }
    
    @RequestMapping(value = "public/cart/clear",method = RequestMethod.GET)
    public String clear(){
        cart.clear();
        return "redirect:/public/cart";
    }
}
