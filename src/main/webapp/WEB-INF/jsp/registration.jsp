<%-- 
    Document   : registration
    Created on : Jul 14, 2013, 4:35:18 PM
    Author     : awanlabs
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="message">
            <c:if test="${message eq 1}">
                <div id="success">
                    Pendaftaran Berhasil
                </div>
            </c:if>
        </div>
        <div id="registration">
            <div id="header-reg">
                Registrasi
            </div>
            <div id="content-reg">
                <hr>
                <div id="form">
                    <form:form method="post" modelAttribute="customer" action="${pageContext.request.contextPath}/public/registration/save">
                        <c:if test="${showError eq 1}">
                            <div id="errors">
                               <p><form:errors path="fullName" class="error"/></p>
                               <p><form:errors path="username" class="error"/></p>
                               <p><form:errors path="password" class="error"/></p>
                               <p><form:errors path="email" class="error"/></p>
                               <p><form:errors path="address" class="error"/></p>
                               <p><form:errors path="phone" class="error"/></p>
                            </div>
                        </c:if>
                        <table>
                            <tr>
                                <td>Nama</td>
                                <td><form:input path="fullName"/></td>
                            </tr>
                            <tr>
                                <td>Username</td>
                                <td><form:input path="username"/></td>
                            </tr>
                            <tr>
                                <td>Password</td>
                                <td><form:password path="password"/></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td><form:input path="email"/></td>
                            </tr>
                            <tr>
                                <td>Alamat</td>
                                <td><form:input path="address"/></td>
                            </tr>
                            <tr>
                                <td>Nomor Telepon</td>
                                <td><form:input path="phone"/></td>
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
