
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

        <c:forEach var="book" items="${books}"> 
            <div class="bookBox">
                <p class="title"><a href="<c:url value="/public/book/detail/${book.id}" />">${book.title}</></p>
                <img width="129" class="thumbnail" src="<c:url value="/img/${book.image}.jpg"/>"/></a>
                <p class="price">Our Price : Rp. ${book.price}</p>
                <img src="<c:url value="/img/buy-button.png"/>"/>
            </div>
        </c:forEach>
    </body>
</html>
