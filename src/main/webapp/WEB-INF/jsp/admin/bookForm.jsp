<%-- 
    Document   : bookForm
    Created on : Aug 3, 2013, 4:16:19 PM
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
                Tambah Buku
            </div>
            <div id="content-reg">
                <hr>
                <div id="form">
                    <c:url value="/admin/book/add" var="url"/>
                    <form:form method="post" modelAttribute="book" enctype="multipart/form-data" action="${url}">
                        <c:if test="${showError eq 1}">
                            <div id="errors">
                               <p><form:errors path="title" class="error"/></p>
                               <p><form:errors path="author" class="error"/></p>
                               <p><form:errors path="description" class="error"/></p>
                               <p><form:errors path="price" class="error"/></p>
                               <p><form:errors path="image" class="error"/></p>
                            </div>
                        </c:if>
                        <table>
                            <tr>
                                <td>Judul</td>
                                <td><form:input path="title"/></td>
                            </tr>
                            <tr>
                                <td>Penulis</td>
                                <td><form:input path="author"/></td>
                            </tr>
                            <tr>
                                <td>Deskripsi</td>
                                <td><form:textarea path="description"/></td>
                            </tr>
                            <tr>
                                <td>Harga</td>
                                <td><form:input path="price"/></td>
                            </tr>
                            <tr>
                                <td>Gambar</td>
                                <td><form:input path="image"/></td>
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
