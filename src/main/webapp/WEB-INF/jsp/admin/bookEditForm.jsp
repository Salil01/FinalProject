<%-- 
    Document   : bookEditForm
    Created on : Aug 5, 2013, 11:54:50 AM
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
                Ubah Buku
            </div>
            <div id="content-reg">
                <hr>
                <div id="form">
                    <c:url value="/admin/book/edit" var="url"/>
                    <form:form method="post" modelAttribute="book" enctype="multipart/form-data" action="${url}">
                        <c:if test="${showError eq 1}">
                            <div id="errors">
                               <p><form:errors path="title" class="error"/></p>
                               <p><form:errors path="author" class="error"/></p>
                               <p><form:errors path="description" class="error"/></p>
                               <p><form:errors path="price" class="error"/></p>
                               <p><form:errors path="category" class="error"/></p>
                               <p><form:errors path="image" class="error"/></p>
                               <p><span class="error">${error}</span></p>
                            </div>
                        </c:if>
                        <form:hidden path="id" value="${book.id}"/>
                        <table>
                            <tr>
                                <td>Judul</td>
                                <td><form:input path="title" value="${book.title}" /></td>
                            </tr>
                            <tr>
                                <td>Penulis</td>
                                <td><form:input path="author" value="${book.author}" /></td>
                            </tr>
                            <tr>
                                <td style="vertical-align: top">Deskripsi</td>
                                <td><form:textarea path="description" cols="35" rows="15" value="${book.description}" /></td>
                            </tr>
                            <tr>
                                <td>Harga</td>
                                <td><form:input path="price" value="${book.price}" /></td>
                            </tr>
                            <tr>
                                <td>Kategori</td>
                                <td><form:select path="category" cssStyle="width:300px">
                                        <c:forEach items="${categories}" var="category">
                                            <c:choose>
                                                <c:when test="${book.category.id==category.id}">
                                                  <form:option value="${category.id}" label="${category.name}" selected="true" />
                                                </c:when>

                                                <c:otherwise>
                                                    <form:option value="${category.id}" label="${category.name}"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </form:select>
                                    </td>
                            </tr>
                            <tr>
                                <td>Gambar</td>
                                <td><form:input path="image" value="${book.image}" /></td>
                            </tr>
                            <tr>
                                <td></td>
                                <td><input type="file" name="fileUpload" /></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Daftar"/></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
