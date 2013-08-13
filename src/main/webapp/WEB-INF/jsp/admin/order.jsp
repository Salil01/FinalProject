<%-- 
    Document   : order
    Created on : Aug 5, 2013, 9:29:18 PM
    Author     : awanlabs
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="registration">
            
            <div id="message">
                <c:if test="${param.edit !=null}">
                    <div id="success">
                        Order dengan id <span style="color: #fa9600">${title}</span> Berhasil diubah Status
                    </div>
                </c:if>
            </div>
            
            <div id="header-reg">
                Order
            </div>
            <div id="content-reg">                
                <div id="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <td>Id</td>
                                <td>Tanggal</td>
                                <td>Pelanggan</td>
                                <td>Status</td>
                                <td>Aksi</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="order">
                            <tr>
                                <td>${order.id}</td>
                                <td>${order.date}</td>
                                <td>${order.customer.fullName}</td>
                                <td>${order.status}</td>
                                <td><a href="<c:url value="/admin/order/detail/${order.id}" />"><img src="<c:url value="/img/pen.png" />"/> Lihat Detail</a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
