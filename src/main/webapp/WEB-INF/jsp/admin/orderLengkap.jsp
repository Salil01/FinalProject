<%-- 
    Document   : orderLengkap
    Created on : Aug 6, 2013, 11:06:16 AM
    Author     : awanlabs
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="checkout">
            <div id="header-check">
                Rincian Order
            </div>
            <div id="content-check">
                <hr>
                <div id="kiri">
                    <h3>Alamat Tagihan</h3>
                    <p>${customer.fullName}</p>
                    <p>${customer.address}</p>
                    <p>${customer.phone}</p>
                    <p>${customer.email}</p>
                </div>
                
                <div id="kanan">
                    <h3>Alamat Pengiriman</h3>
                    <p>${order.receiver}</p>
                    <p>${order.shippingAddress}</p>
                    <p>${order.city}, ${order.province}</p>
                    <p>${order.receiverEmail}</p>
                    <p>${order.receiverPhone}</p>
                </div>
                
                <div id="show-cart">
                    <table id="cart">
                      <thead>
                          <tr>
                              <th>Judul</th>
                              <th>Gambar</th>
                              <th style="width: 15%">Jumlah</th>
                              <th>Harga</th>
                              <th>Subtotal</th>
                          </tr>
                      </thead>
                      <tbody>
                          <c:forEach items="${details}" var="detail">
                          <tr>
                              <td>${detail.book.title}</td>
                              <td><img height="41" width="34" src="<c:url value="/img/${detail.book.image}.jpg" />" /></td>
                              <td>${detail.amount}</td>
                              <td>${detail.price}</td>
                              <td>${detail.subTotal}</td>
                          </tr>
                          </c:forEach>
                      </tbody>
                    </table>  

                    <div id="total">
                        Total : <c:out value="${order.total}"/>
                    </div>
                    
                    <c:if test="${showError eq 1}">
                            <div id="errors">
                               <p><form:errors path="status" class="error"/></p>
                            </div>
                        </c:if>
                    
                    <div id="status">
                        <c:url value="/admin/order/edit" var="url"/>
                        <form method="post" action="${url}">
                            <input type="hidden" name="id" value="${order.id}"/>
                            Status : <input type="text" name="status" value="${order.status}"/>
                            <input type="submit" value="Update"/>
                        </form>
                    </div>
                    
                </div>
            </div>
        </div>
    </body>
</html>
