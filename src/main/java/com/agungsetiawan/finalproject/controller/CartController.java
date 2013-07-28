package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CartServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private CartServiceInterface cart;
    
    @Autowired
    private BookService bookService;
    
    public CartController(CartServiceInterface cart,BookService bookService){
        this.cart=cart;
        this.bookService=bookService;
    }
    
    public CartController(){
        
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
        Book bookSaved=cart.save(book);
        model.addAttribute("id", bookSaved.getId());
        return "redirect:/public/cart";
    }
    
    @RequestMapping(value = "public/cart/remove/{bookId}",method = RequestMethod.GET)
    public String removeBook(@PathVariable("bookId") Long bookId ,Model model){
        Book book=bookService.findOne(bookId);
        Book bookDeleted=cart.delete(book);
        model.addAttribute("id", bookDeleted.getId());
        return "redirect:/public/cart";
    }
    
    @RequestMapping(value = "public/cart/update",method = RequestMethod.POST)
    public String update(@RequestParam(value = "bookId") Long bookId,@RequestParam(value = "quantity") Integer quantity,Model model){
        Book book=bookService.findOne(bookId);
        Book bookUpdated=cart.update(book, quantity);
        model.addAttribute("id", bookUpdated.getId());
        return "redirect:/public/cart";
    }
    
    @RequestMapping(value = "public/cart/clear",method = RequestMethod.GET)
    public String clear(){
        cart.clear();
        return "redirect:/public/cart";
    }
}
