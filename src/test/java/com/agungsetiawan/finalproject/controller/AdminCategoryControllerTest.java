package com.agungsetiawan.finalproject.controller;

import com.agungsetiawan.finalproject.domain.Category;
import com.agungsetiawan.finalproject.exception.NotFoundException;
import com.agungsetiawan.finalproject.service.CategoryService;
import com.agungsetiawan.finalproject.util.CategoryBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import org.hamcrest.text.IsEmptyString;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
/**
 *
 * @author awanlabs
 */
public class AdminCategoryControllerTest {
    
    AdminCategoryController adminCategoryController;
    CategoryService categoryService;
    View view;
    MockMvc mockMvc;

    @Before
    public void setUp() {
        categoryService= Mockito.mock(CategoryService.class);
        adminCategoryController=new AdminCategoryController(categoryService);
        view=Mockito.mock(View.class);
        mockMvc= MockMvcBuilders.standaloneSetup(adminCategoryController).setSingleView(view)
                .setValidator(validator()).setViewResolvers(viewResolver())
                .setHandlerExceptionResolvers(getSimpleMappingExceptionResolver()).build();
    }
    
    @Test
    public void indexTest() throws Exception{
        Category categoryOne=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        Category categoryTwo=new CategoryBuilder().id(2L).name("PHP").description("PHP Book Category").build();
        List<Category> categories=new ArrayList<Category>();
        categories.add(categoryOne);
        categories.add(categoryTwo);
        
        Mockito.when(categoryService.findAll()).thenReturn(categories);
        
        mockMvc.perform(get("/admin/category"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "category.jsp"))
                .andExpect(model().attribute("categories", categories))
                .andExpect(model().attribute("categories", hasSize(2)))
                .andExpect(model().attribute("categories", hasItem( 
                  allOf(   
                    hasProperty("id",is(1L)),
                    hasProperty("name",is("Java")),
                    hasProperty("description", is("Java Book Category"))
                  )
                )))
                .andExpect(model().attribute("categories", hasItem( 
                  allOf(   
                    hasProperty("id",is(2L)),
                    hasProperty("name",is("PHP")),
                    hasProperty("description", is("PHP Book Category"))
                  )
                )));
        
        Mockito.verify(categoryService,Mockito.times(1)).findAll();
        Mockito.verifyNoMoreInteractions(categoryService);
    }
    
    @Test
    public void addFormTest() throws Exception{
        mockMvc.perform(get("/admin/category/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "categoryForm.jsp"))
                .andExpect(model().attribute("category", hasProperty("id", nullValue())))
                .andExpect(model().attribute("category", hasProperty("name", IsEmptyString.isEmptyOrNullString())))
                .andExpect(model().attribute("category", hasProperty("description", IsEmptyString.isEmptyOrNullString())));
        
        Mockito.verifyZeroInteractions(categoryService);
    }
    
    @Test
    public void addFailTest() throws Exception{
        mockMvc.perform(post("/admin/category/add")
                .param("name", "Java"))
//                .param("description", "Java Book Category"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("showError", 1))
                .andExpect(model().attribute("page", "categoryForm.jsp"))
                .andExpect(model().attribute("category", hasProperty("id", nullValue())))
                .andExpect(model().attribute("category", hasProperty("name", is("Java"))))
                .andExpect(model().attributeHasFieldErrors("category", "description"));
        
        Mockito.verifyZeroInteractions(categoryService);
    }
    
    @Test
    public void addFailTooLongDescriptionTest()throws Exception{
        mockMvc.perform(post("/admin/category/add")
                .param("name", "Java")
                .param("description", "Java Books Books Books Books Books Books Books Books Books"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("showError", 1))
                .andExpect(model().attribute("page", "categoryForm.jsp"))
                .andExpect(model().attribute("category", hasProperty("id", nullValue())))
                .andExpect(model().attribute("category", hasProperty("name", is("Java"))))
                .andExpect(model().attributeHasFieldErrors("category", "description"));
        
        Mockito.verifyZeroInteractions(categoryService);
    }
    
    @Test
    public void addTest()throws Exception{
        
        Category categoryToSave=new CategoryBuilder().name("Java").description("Java Book Category").build();
        Category categorySaved=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        
        Mockito.when(categoryService.save(categoryToSave)).thenReturn(categorySaved);
        
        mockMvc.perform(post("/admin/category/add")
                .param("name", "Java")
                .param("description", "Java Book Category")
                .sessionAttr("category", categoryToSave))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/admin/category?save"))
                .andExpect(redirectedUrl("/admin/category?save"))
                .andExpect(flash().attribute("title", "Java"));
        
        Mockito.verify(categoryService,Mockito.times(1)).save(categoryToSave);
        Mockito.verifyNoMoreInteractions(categoryService);
    }
    
    @Test
    public void editFormNotFoundTest() throws Exception{
        
        Mockito.when(categoryService.findOne(1L)).thenReturn(null);
        mockMvc.perform(get("/admin/category/edit/{id}",1L))
                .andExpect(view().name("404s"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/404s.jsp"));
    }
    
    @Test
    public void editForm() throws Exception{
        Category category=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        Mockito.when(categoryService.findOne(1L)).thenReturn(category);
        
        mockMvc.perform(get("/admin/category/edit/{id}",1L))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("page", "categoryEditForm.jsp"))
                .andExpect(model().attribute("category", category))
                .andExpect(model().attribute("category", hasProperty("id", is(1L))))
                .andExpect(model().attribute("category", hasProperty("name", is("Java"))))
                .andExpect(model().attribute("category", hasProperty("description", is("Java Book Category"))));
        
        Mockito.verify(categoryService,Mockito.times(1)).findOne(1L);
        Mockito.verifyNoMoreInteractions(categoryService);
    }
    
    @Test
    public void editFailTest() throws Exception{
        mockMvc.perform(post("/admin/category/edit")
                .param("name", "Java"))
//                .param("description", "Java Book Category"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("showError", 1))
                .andExpect(model().attribute("page", "categoryEditForm.jsp"))
                .andExpect(model().attribute("category", hasProperty("id", nullValue())))
                .andExpect(model().attribute("category", hasProperty("name", is("Java"))))
                .andExpect(model().attributeHasFieldErrors("category", "description"));
        
        Mockito.verifyZeroInteractions(categoryService);
    }
    
    @Test
    public void editFailTooLongDescriptionTest() throws Exception{
        mockMvc.perform(post("/admin/category/edit")
                .param("name", "Java")
                .param("description", "Java Books Books Books Books Books Books Books Books Books"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/templateno"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/admin/templateno.jsp"))
                .andExpect(model().attribute("showError", 1))
                .andExpect(model().attribute("page", "categoryEditForm.jsp"))
                .andExpect(model().attribute("category", hasProperty("id", nullValue())))
                .andExpect(model().attribute("category", hasProperty("name", is("Java"))))
                .andExpect(model().attributeHasFieldErrors("category", "description"));
        
        Mockito.verifyZeroInteractions(categoryService);
    }
    
    @Test
    public void editTest() throws Exception{
        Category categoryToEdit=new CategoryBuilder().id(1L).name("Java").description("Java Book Category").build();
        Category categoryEdited=new CategoryBuilder().id(1L).name("Java").description("Good Java Book Category").build();
        
        Mockito.when(categoryService.edit(categoryToEdit)).thenReturn(categoryEdited);
        
        mockMvc.perform(post("/admin/category/edit")
                .param("name", "Java")
                .param("description", "Java Book Category")
                .sessionAttr("category", categoryToEdit))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/admin/category?edit"))
                .andExpect(redirectedUrl("/admin/category?edit"))
                .andExpect(flash().attribute("title", "Java"));
        
        Mockito.verify(categoryService,Mockito.times(1)).edit(categoryToEdit);
        Mockito.verifyNoMoreInteractions(categoryService);
    }
    
    @Test
    public void deleteNotFoundTest() throws Exception{
        Mockito.when(categoryService.findOne(1L)).thenReturn(null);
        
        mockMvc.perform(get("/admin/category/delete/{id}",1L))
                .andExpect(view().name("404s"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/404s.jsp"));
    }
    
    @Test
    public void deleteTest() throws Exception{
        Category categoryToDelete=new CategoryBuilder().id(1L).name("Java")
                                  .description("Java Book Category").build();
        
        Category categoryDeleted=new CategoryBuilder().id(1L).name("Java")
                                  .description("Java Book Category").build();
        
        Mockito.when(categoryService.findOne(1L)).thenReturn(categoryToDelete);
        
        mockMvc.perform(get("/admin/category/delete/{id}",1L))
                .andExpect(status().is(302))
                .andExpect(view().name("redirect:/admin/category?delete"))
                .andExpect(redirectedUrl("/admin/category?delete"));
        
        Mockito.verify(categoryService, Mockito.times(1)).findOne(1L);
        Mockito.verify(categoryService,Mockito.times(1)).delete(categoryToDelete);
        Mockito.verifyNoMoreInteractions(categoryService);
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
    
    public SimpleMappingExceptionResolver getSimpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put(NotFoundException.class.getName(), "404s");
        exceptionMappings.put(ConstraintViolationException.class.getName(), "409");
//        exceptionMappings.put("java.lang.Exception", "404");
//        exceptionMappings.put("java.lang.RuntimeException", "404");

        exceptionResolver.setExceptionMappings(exceptionMappings);
//        exceptionResolver.setDefaultErrorView("404s");
        exceptionResolver.setOrder(0);
//        Properties statusCodes = new Properties();
//
//        statusCodes.put("404", "404");
//        statusCodes.put("error/error", "500");
//
//        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }
}