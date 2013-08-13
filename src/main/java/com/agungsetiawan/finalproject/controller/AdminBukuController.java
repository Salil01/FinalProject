package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.exception.NotFoundException;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CategoryService;
import com.agungsetiawan.finalproject.util.CategoryEditor;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author awanlabs
 */
@Controller
//@SessionAttributes("book")
public class AdminBukuController implements HandlerExceptionResolver {
    
    @Autowired
    BookService bookService;
    
    @Autowired
    CategoryService categoryService;

    public AdminBukuController() {
    }

    public AdminBukuController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
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
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("page","bookForm.jsp");
        return "admin/templateno";
    }

    @RequestMapping(value = "admin/book/add",method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("book") Book book,@RequestParam(required = false) MultipartFile fileUpload
                    ,HttpServletRequest request, BindingResult result,Model model
                    , RedirectAttributes redirectAttributes) throws IOException{
        
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("showError", 1);
            model.addAttribute("page","bookForm.jsp");
            
            return "admin/templateno";
        }else{
            if(fileUpload!=null){
             fileUpload.transferTo(new File(request.getSession().getServletContext().getRealPath("/")+"/img/"+fileUpload.getOriginalFilename()));
             bookService.save(book);
             redirectAttributes.addFlashAttribute("title", book.getTitle());
            }
            return "redirect:/admin/book?save";
        }
    }
    
    @RequestMapping(value = "admin/book/edit/{id}",method = RequestMethod.GET)
    public String editForm(Model model,@PathVariable Long id) throws NotFoundException{
        Book book=bookService.findOne(id);
        if(book==null){
            throw new NotFoundException("Book Not Found");
        }
        
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("book", book);
        model.addAttribute("page", "bookEditForm.jsp");
        return "admin/templateno";
    }
    
    @RequestMapping(value = "admin/book/edit",method = RequestMethod.POST)
    public String edit(@Valid Book book,BindingResult bindingResult,Model model
                        ,RedirectAttributes redirectAttributes){
        
        if(bindingResult.hasErrors()){
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("showError", 1);
            model.addAttribute("page", "bookEditForm.jsp");
            return "admin/templateno";
        }else{
            bookService.edit(book);
            redirectAttributes.addFlashAttribute("title", book.getTitle());
            return "redirect:/admin/book?edit";
        }        
    }
    
    @RequestMapping(value = "admin/book/delete/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes) throws NotFoundException{
        Book book=bookService.findOne(id);
        if(book==null){
            System.out.println("NULL NULL NULL");
            throw new NotFoundException("Book Not Found");
        }
        bookService.delete(book);
        redirectAttributes.addFlashAttribute("title", book.getTitle());
        return "redirect:/admin/book?delete";
    }
    
    @InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder){
        binder.registerCustomEditor(Category.class, new CategoryEditor(this.categoryService));
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest hsr, HttpServletResponse hsr1, Object o, Exception exception) {
        Map<Object,Object> model=new HashMap<Object, Object>();
        if(exception instanceof IOException){
            model.put("showError", 1);
            model.put("error", "Choose file to upload");
            model.put("page","bookForm.jsp");
            model.put("book", new Book());
            model.put("categories", categoryService.findAll());
        }  
        return new ModelAndView("admin/templateno",(Map) model);
    }
}
