package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import java.util.List;
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
public class AllBookController {
    
    @Autowired
    private BookService bookService;
    
    public void setBookService(BookService bookService){
        this.bookService=bookService;
    }
    
    @RequestMapping(value = "public/book/all",method = RequestMethod.GET)
    public String allBook(Model model){
        List<Book> books=bookService.findRandom();
        model.addAttribute("books", books);
        model.addAttribute("page", "grid.jsp");
        return "templateno";
    }
}
