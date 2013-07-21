<%-- 
    Document   : customerorderdetail
    Created on : Jul 21, 2013, 2:18:00 PM
    Author     : awanlabs
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="registration">
            <div id="header-reg">
                Detail Order
            </div>
            <div id="content-reg">
                <table id="cart">
                    <thead>
                        <tr>
                            <th>Judul</th>
                            <th>Jumlah</th>
                            <th>Harga</th>
                            <th>Subtotal</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${details}" var="detail">
                            <tr>
                                <td>${detail.book.title}</td>
                                <td>${detail.amount}</td>
                                <td>${detail.price}
                                <td>${detail.subTotal}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <div id="total">
                    Total : <c:out value="${total}"/>
                </div>
            </div>
        </div>
    </body>
</html>
