package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
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
public class BookDetailController {
    
    @Autowired
    private BookService bookService;
    
    public BookDetailController(){
        
    }
    
    public BookDetailController(BookService bookService){
        this.bookService=bookService;
    }
    
    @RequestMapping(value = "/public/book/detail/{bookId}",method = RequestMethod.GET)
    public String bookDetail(@PathVariable("bookId") Long bookId, Model model){
        Book book=bookService.findOne(bookId);
        model.addAttribute("page", "detail.jsp");
        model.addAttribute("book", book);
        return "template";
    }
}
