<%-- 
    Document   : category
    Created on : Aug 5, 2013, 3:01:19 PM
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
            
            <div id="message">
                <c:if test="${param.save !=null}">
                    <div id="success">
                        Kategori <span style="color: #fa9600">${title}</span> Berhasil disimpan
                    </div>
                </c:if>
                
                <c:if test="${param.edit !=null}">
                    <div id="success">
                        Kategori <span style="color: #fa9600">${title}</span> Berhasil diubah
                    </div>
                </c:if>
                
                <c:if test="${param.delete !=null}">
                    <div id="success">
                        Kategori <span style="color: #fa9600">${title}</span> Berhasil dihapus
                    </div>
                </c:if>
            </div>
            
            <div id="header-reg">
                Manajemen Kategori
            </div>
            <div id="content-reg">
                <a href="<c:url value="/admin/category/add" />" class="button"><img src="<c:url value="/img/add.png" />"/>Tambah kategori</a>
                
                <div id="table-wrapper">
                    <table class="admin-table">
                        <thead>
                            <tr>
                                <td>Nama</td>
                                <td>Deskripsi</td>
                                <td>Aksi</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${categories}" var="category">
                            <tr>
                                <td>${category.name}</td>
                                <td>${category.description}</td>
                                <td><a href="<c:url value="/admin/category/edit/${category.id}" />"><img src="<c:url value="/img/pen.png" />"/> Edit</a> |
                                    <a onclick="return confirm('Yakin Hapus?')" href="<c:url value="/admin/category/delete/${category.id}" />"><img src="<c:url value="/img/trash.png" />"/> Delete</a></td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
</html>
