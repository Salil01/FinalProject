<%-- 
    Document   : cart
    Created on : Jul 12, 2013, 2:14:31 PM
    Author     : awanlabs
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Cart</title>
    </head>
    <body>
        <h2>Cart</h2>
        <c:if test="${empty books}">
            <p>Keranjang belanja Anda masih kosong</p>
        </c:if>
            
        <c:if test="${not empty books}">
                <table id="cart">
                  <thead>
                      <tr>
                          <th>Judul</th>
                          <th>Gambar</th>
                          <th style="width: 15%">Jumlah</th>
                          <th>Update</th>
                          <th>Hapus</th>
                          <th>Harga</th>
                          <th>Subtotal</th>
                      </tr>
                  </thead>
                  <tbody>
                      <c:forEach items="${books}" var="b">
                      <form method="post" action="<c:url value="/public/cart/update" />">
                      <input type="hidden" name="bookId" value="${b.key.id}"/>
                      <tr>
                          <td>${b.key.title}</td>
                          <td><img src="<c:url value="/img/${b.key.image}_small.jpg" />" /></td>
                          <td><input class="quantity" type="text" name="quantity" value="${b.value}" /></td>
                          <td><input type="submit" value="Update" /></td>
                          <td><a href="<c:url value="/public/cart/remove/${b.key.id}" />"><img src="<c:url value="/img/delete.png" />"/></a></td>
                          <td>${b.key.price}</td>
                          <td>${b.key.price*b.value}</td>
                      </tr>
                      </form>
                      </c:forEach>
                  </tbody>
                </table>  
            
                <div id="total">
                    Total : <c:out value="${total}"/>
                </div>
                <div id="button-button">
                    <a href="<c:url value="/" />">Belanja Lagi</a>
                    <a href="<c:url value="/public/cart/clear"/>">Kosongkan Keranjang</a>
                    <a href="<c:url value="/secured/checkout/1" />">Selesai Belanja</a>
                </div>
        </c:if>
    </body>
</html>
