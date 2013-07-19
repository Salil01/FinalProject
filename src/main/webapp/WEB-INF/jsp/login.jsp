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
        <div id="registration">
            <div id="header-reg">
                Login
            </div>
            <div id="content-reg">
                <hr>
                <div id="form">
                    <c:url value="/public/login" var="login"/>
                    <form method="post" action="${login}">
                        <c:if test="${param.error!=null}">
                            <div id="errors">
                               <p>Username atau Password salah</p>
                            </div>
                        </c:if>
                        <table>
                            <tr>
                                <td>Username</td>
                                <td><input type="text" name="username"/></td>
                            </tr>
                            <tr>
                                <td>Password</td>
                                <td><input type="password" name="password"/></td>
                            </tr>
                            <tr>
                                <td><input type="submit" value="Login"/></td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
