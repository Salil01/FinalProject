package com.agungsetiawan.finalproject.interceptor;

import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CartServiceInterface;
import com.agungsetiawan.finalproject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

/**
 *
 * @author awanlabs
 */
public class CommonDataInterceptor implements WebRequestInterceptor{
    
    @Autowired
    private CartServiceInterface cartService;
    @Autowired
    private BookService bookService;
    @Autowired
    private CategoryService categoryService;

    @Override
    public void preHandle(WebRequest wr) throws Exception {
    }

    @Override
    public void postHandle(WebRequest wr, ModelMap model) throws Exception {
        model.addAttribute("listCategory", categoryService.findAll());
        model.addAttribute("randomBooks", bookService.findRandom());
        model.addAttribute("cartSize", cartService.size());
    }

    @Override
    public void afterCompletion(WebRequest wr, Exception excptn) throws Exception {
    }
    
}
