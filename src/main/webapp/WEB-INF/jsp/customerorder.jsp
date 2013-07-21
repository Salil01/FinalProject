<%-- 
    Document   : myorder
    Created on : Jul 21, 2013, 1:30:20 PM
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
        <div id="registration">
            <div id="header-reg">
                My Order
            </div>
            <div id="content-reg">
                
                <c:if test="${empty orders}">
                    <p>Anda belum pernah melakukan order sebelumnya</p>
                </c:if>
                
                <c:if test="${not empty orders}">
                <table id="cart">
                    <thead>
                        <tr>
                            <th>Id Order</th>
                            <th>Tanggal Order</th>
                            <th>Status</th>
                            <th>Total</th>
                            <th>Detail</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orders}" var="order">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.date}</td>
                                <td>${order.status}</td>
                                <td>${order.total}</td>
                                <td><a href="<c:url value="/secured/my/order/detail/${order.id}" />">Lihat Detail</a></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                </c:if>
            </div>
        </div>
    </body>
</html>
