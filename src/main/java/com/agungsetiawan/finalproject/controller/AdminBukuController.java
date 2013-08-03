package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.service.BookService;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author awanlabs
 */
@Controller
public class AdminBukuController {
    
    @Autowired
    BookService bookService;

    public AdminBukuController() {
    }

    public AdminBukuController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @RequestMapping(value = "admin/book")
    public String index(Model model){
        model.addAttribute("books", bookService.findAll());
        model.addAttribute("page", "book.jsp");
        return "admin/templateno";
    }
    
    @RequestMapping(value = "admin/book/add",method = RequestMethod.GET)
    public String addForm(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("page","bookForm.jsp");
        return "admin/templateno";
    }
    private String saveDirectory = "C:\\upload\\";
    @RequestMapping(value = "admin/book/add",method = RequestMethod.POST)
    public String add(@ModelAttribute("book") Book book,@RequestParam MultipartFile fileUpload,Model model,HttpServletRequest request) throws IOException{
        
        
        if(fileUpload!=null){
//            fileUpload.transferTo(new File(saveDirectory+fileUpload.getOriginalFilename()));
             fileUpload.transferTo(new File(request.getSession().getServletContext().getRealPath("/")+"/img/"+fileUpload.getOriginalFilename()));
            bookService.save(book);
        }
        model.addAttribute("page", "beranda.jsp");
        return "admin/templateno";
    }
}
