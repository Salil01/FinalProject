package com.agungsetiawan.finalproject.security;

import com.agungsetiawan.finalproject.type.IntegrationTest;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.*;
import com.jayway.restassured.authentication.FormAuthConfig;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
/**
 *
 * @author awanlabs
 */
@org.junit.experimental.categories.Category(IntegrationTest.class)
public class SecurityIT {
    
    @Test
    public void firstTest(){
        given().auth()
       .form("blinkawan", "greatengineer", new FormAuthConfig("http://localhost:8080/public/login", "username", "password"))
       .expect().statusCode(403).when().get("http://localhost:8080/admin");
    }
    
    @Test
    public void secondTest(){
        given().auth()
       .form("admin", "admin", new FormAuthConfig("http://localhost:8080/public/login", "username", "password"))
       .expect().statusCode(200).when().get("http://localhost:8080/admin");
    }
    
    @Test
    public void thirdTest(){
        given().auth().none()
        .expect().rootPath("http://localhost:8080/public/login/form")
        .when().get("http://localhost:8080/admin");
    }
}
