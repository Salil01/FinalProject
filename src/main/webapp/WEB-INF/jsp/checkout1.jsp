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
        
        <p>Lengkapi informasi dibawah ini dengan benar untuk <b>mempercepat</b> proses pengiriman</p>
        
        <div id="checkout">
            <div id="header-check">
                Checkout
            </div>
            <div id="content-check">
                <hr>
                <div id="billing-address">
                    <h3>Alamat Tagihan</h3>
                    <p>Agung Setiawan</p>
                    <p>Bukit Umbul 3E, Sumurboto, Banyumanik</p>
                    <p>Semarang, Jawa Tengah</p>
                    <p>089667754239</p>
                    <p>blinkawan@gmail.com</p>
                </div>
        
                <div id="shipping-address">
                    <h3>Alamat Pengiriman</h3>
                    <form:form id="form" modelAttribute="order" action="${pageContext.request.contextPath}/secured/checkout/2">
                        <c:if test="${showError eq 1}">
                            <div id="errors">
                               <p><form:errors path="receiver" class="error"/></p>
                               <p><form:errors path="shippingAddress" class="error"/></p>
                               <p><form:errors path="city" class="error"/></p>
                               <p><form:errors path="province" class="error"/></p>
                               <p><form:errors path="receiverEmail" class="error"/></p>
                               <p><form:errors path="receiverPhone" class="error"/></p>
                            </div>
                        </c:if>
                        <table>
                            <tr>
                                <td>Nama Penerima</td>
                                <td><form:input path="receiver"/></td>
                            </tr>
                            <tr>
                                <td id="alamat">Alamat</td>
                                <td><form:textarea path="shippingAddress"/></td>
                            </tr>
                            <tr>
                                <td>Kota</td>
                                <td><form:input path="city"/></td>
                            </tr>
                            <tr>
                                <td>Provinsi</td>
                                <td><form:input path="province"/></td>
                            </tr>
                            <tr>
                                <td>Email</td>
                                <td><form:input path="receiverEmail"/></td>
                            </tr>
                            <tr>
                                <td>Telepon</td>
                                <td><form:input path="receiverPhone"/></td>
                            </tr>
                            <tr>
                                <td colspan="2"><input type="submit" value="Next >>"/></td>
                            </tr>
                        </table>
                    </form:form>
                </div>
            </div>
            
        </div>
    </body>
</html>
