<%-- 
    Document   : search
    Created on : Jul 14, 2013, 1:23:41 PM
    Author     : awanlabs
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>${h2title}</h2>

        <c:if test="${empty books}">
            <p>Pencarian tidak menemukan hasil</p>
        </c:if>
        
        <c:if test="${not empty books}">
            <c:forEach var="book" items="${books}"> 
            <div class="bookBox">
                <p class="title"><a href="<c:url value="/public/book/detail/${book.id}" />">${book.title}</></p>
                <img class="thumbnail" src="<c:url value="/img/${book.image}.jpg"/>"/></a>
                <p class="price">Our Price : $${book.price}</p>
                <img src="<c:url value="/img/buy-button.png"/>"/>
            </div>
        </c:forEach>
        </c:if>
    </body>
</html>

