<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"  %>
<html>
<head>
	<title>Packtpub</title>
        <link rel="stylesheet" href="<c:url value="/css/style.css" />" type="text/css"/>
</head>
<body>
    <div id="container" >
    	<div id="header">
            
            <div id="menu">
            	<ul>
                        <li><a href="<c:url value="/" />">beranda</a></li>
            		<li><a href="">tentang kami</a></li>
            		<li><a href="<c:url value="/public/book/all" />">semua produk</a></li>
            		<li><a href="">blog</a></li>
            		<li><a href="">forum</a></li>
            		<li><a href="">hubungi kami</a></li>
            	</ul>
            </div> <!-- batas akhir menu -->

            <div id="banner">
                <sec:authorize access="authenticated" var="authenticated"/>
                <c:choose>
                    <c:when test="${authenticated}">
                        <form id="login">
                            <table>
                                <tr>
                                    <td colspan="3">Halo, <sec:authentication property="name"/> </td>
                                </tr>
                                <tr>
                                    <td><a href="<c:url value="/logout" />">Logout |</a></td>
                                    <td><a href="">My Account</a></td>
                                </tr>
                            </table>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <c:url value="/public/login" var="login"/>
                        <form id="login" method="post" action="${login}">
                            <table>
                                <tr>
                                    <td>Username:</td>
                                    <td><input type="text" name="username"/></td>
                                </tr>
                                <tr>
                                    <td>Password:</td>
                                    <td><input type="text" name="password"/></td>
                                    <td><input type="submit" value="Go"/></td>
                                </tr>
                                <tr>
                                    <td><a href="<c:url value="/public/registration" />">Register</a></td>
                                </tr>
                            </table>
                        </form>
                    </c:otherwise>
                </c:choose>

                <form id="search" method="get" action="<c:url value="/public/search" />">
                    <input type="text" name="key"/>
                    <input type="submit" value="Search"/>
                </form>

                <div id="packt">
                    <img src="<c:url value="/img/packt.jpg"/>"/>
                </div>
            </div> <!-- batas akhir banner -->

    	</div> <!-- batas akhir header -->

    	<div id="content">

            <div id="mainno">
                
                <jsp:include page="${page}"/>

            </div> <!-- batas akhir main -->

    	</div> <!-- batas akhir content -->

    	<div id="footer">
    		dikembangkan oleh Agung Setiawan - L2F008002
    	</div> <!-- batas akhir footer -->

    </div> <!-- batas akhir container -->
</body>
</html>