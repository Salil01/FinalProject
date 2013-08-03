package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private BookService bookService;

    public SearchController() {
    }
    
    public SearchController(BookService bookService) {
        this.bookService = bookService;
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
