package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.exception.NotFoundException;
import com.agungsetiawan.finalproject.service.CategoryService;
import javax.validation.Valid;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author awanlabs
 */
@Controller
@SessionAttributes("category")
public class AdminCategoryController {
    
    @Autowired
    CategoryService categoryService;

    public AdminCategoryController() {
    }

    public AdminCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @RequestMapping(value = "admin/category",method = RequestMethod.GET)
    public String index(Model model){
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("page", "category.jsp");
        return "admin/templateno";
    }
    
    @RequestMapping(value = "admin/category/add",method = RequestMethod.GET)
    public String addForm(Model model){
        model.addAttribute("category", new Category());
        model.addAttribute("page", "categoryForm.jsp");
        return "admin/templateno";
    }
    
    @RequestMapping(value = "admin/category/add",method = RequestMethod.POST)
    public String add(@Valid Category category, BindingResult bindingResult, RedirectAttributes redirectAttributes
                        ,Model model){
        
        if(bindingResult.hasErrors()){
            model.addAttribute("showError", 1);
            model.addAttribute("page", "categoryForm.jsp");
            return "admin/templateno";
        }else{
            categoryService.save(category);
            redirectAttributes.addFlashAttribute("title", category.getName());
            return "redirect:/admin/category?save";
        }
    }
    
    @RequestMapping(value = "admin/category/edit/{id}",method = RequestMethod.GET)
    public String editForm(Model model,@PathVariable Long id) throws NotFoundException{
        Category category=categoryService.findOne(id);
        if(category==null){
            throw new NotFoundException();
        }
        
        model.addAttribute("category", category);
        model.addAttribute("page", "categoryEditForm.jsp");
        return "admin/templateno";
    }
    
    @RequestMapping(value = "admin/category/edit",method = RequestMethod.POST)
    public String edit(@Valid Category category,BindingResult bindingResult,Model model
                        ,RedirectAttributes redirectAttributes){
        
        if(bindingResult.hasErrors()){
            model.addAttribute("showError", 1);
            model.addAttribute("page", "categoryEditForm.jsp");
            return "admin/templateno";
        }else{
            categoryService.edit(category);
            redirectAttributes.addFlashAttribute("title", category.getName());
            return "redirect:/admin/category?edit";
        }
    }
    
    @RequestMapping(value = "admin/category/delete/{id}",method = RequestMethod.GET)
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes) throws NotFoundException{
        Category category=categoryService.findOne(id);
        if(category==null){
            throw new NotFoundException("Category Not Found");
        }
        
        try{
            categoryService.delete(category);
        }catch(Exception ex){
            throw new ConstraintViolationException("Category cannot be deleted", null, null);
        }
        
        redirectAttributes.addFlashAttribute("title", category.getName());
        return "redirect:/admin/category?delete";
    }
}
