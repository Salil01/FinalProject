package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Book;
import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.service.BookService;
import com.agungsetiawan.finalproject.service.CategoryService;
import com.agungsetiawan.finalproject.util.BookBuilder;
import com.agungsetiawan.finalproject.util.CategoryBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.text.IsEmptyString;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author awanlabs
 */
public class AdminBukuControllerTest {
    
    AdminBukuController adminBukuController;
    BookService bookService;
    CategoryService categoryService;
    MockMvc mockMvc;
    View view;

    @Before
    public void setUp() {
        bookService= Mockito.mock(BookService.class);
        categoryService=Mockito.mock((CategoryService.class));
        adminBukuController=new AdminBukuController(bookService, categoryService);
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(adminBukuController).setSingleView(view)
                .setViewResolvers(viewResolver()).setValidator(validator()).build();
    }
    
    @Test
    public void indexTest() throws Exception{
         Book bookOne=new BookBuilder().id(1L).author("Agung Setiawan").title("Java in Nutshell")
                         .price(new BigDecimal(85000)).description("Java book for intermediate")
                         .image("java-in-nutshell").build();
        Book bookTwo=new BookBuilder().id(2L).author("Markosvey").title("PHP in Nutshell")
                         .price(new BigDecimal(65000)).description("PHP book for intermediate")
                         .image("php-in-nutshell").build();
        
        List<Book> books=new ArrayList<Book>();
        books.add(bookOne);
        books.add(bookTwo);
        
        Mockito.when(bookService.findAll()).thenReturn(books);
        
        mockMvc.perform(get("/admin/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("books", books))
                .andExpect(model().attribute("page", "book.jsp"));
        
        Mockito.verify(bookService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(bookService);
        Mockito.verifyZeroInteractions(categoryService);
    }
    
    @Test
    public void addFormTest() throws Exception{
        Category categoryOne=new CategoryBuilder().id(1L).name("Java").description("Java Books Category").build();
        Category categoryTwo=new CategoryBuilder().id(2L).name(".NET").description(".NET Books Category").build();
        List<Category> categories=new ArrayList<Category>();
        categories.add(categoryOne);
        categories.add(categoryTwo);
        
        Mockito.when(categoryService.findAll()).thenReturn(categories);
        
        mockMvc.perform(get("/admin/book/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "bookForm.jsp"))
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("book", hasProperty("id", nullValue())))
                .andExpect(model().attribute("book", hasProperty("title", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("author", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("description", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("price", nullValue())))
                .andExpect(model().attribute("book", hasProperty("image", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("book", hasProperty("category", nullValue())));
        
        Mockito.verify(categoryService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(categoryService);
        Mockito.verifyZeroInteractions(bookService);
    }
    
    
    public void addFailTest()throws Exception{        
        
        mockMvc.perform(fileUpload("/admin/book/add")
                .param("title", "Java Effective")
                .param("author", "Josuha Bloch")
                .param("description", "Good Java Book")
                .param("price", "90000")
                .param("category", "1")
                .param("image", "java-effective")
                .param("fileUpload", "image"))
                .andExpect(status().is(302))
                .andExpect(view().name("admin/template"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno"))
                .andExpect(model().attributeHasFieldErrors("book", "title"));
    }
    
    private LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
    
    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}