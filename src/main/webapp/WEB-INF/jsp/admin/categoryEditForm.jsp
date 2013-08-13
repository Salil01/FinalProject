<%-- 
    Document   : categoryEditForm
    Created on : Aug 5, 2013, 3:54:30 PM
    Author     : awanlabs
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
                Tambah Kategori
            </div>
            <div id="content-reg">
                <hr>
                <div id="form">
                    <c:url value="/admin/category/edit" var="url"/>
                    <form:form method="post" modelAttribute="category" action="${url}">
                        <c:if test="${showError eq 1}">
                            <div id="errors">
                               <p><form:errors path="name" class="error"/></p>
                               <p><form:errors path="description" class="error"/></p>
                            </div>
                        </c:if>
                        <table>
                            <form:hidden path="id" value="${category.id}"/>
                            <tr>
                                <td>Nama</td>
                                <td><form:input path="name" value="${category.name}" /></td>
                            </tr>
                            <tr>
                                <td>Deskripsi</td>
                                <td><form:input path="description" value="${category.description}" /></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Simpan"/></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
