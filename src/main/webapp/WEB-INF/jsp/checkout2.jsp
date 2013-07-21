<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="checkout">
            <div id="header-check">
                Rincian Order
            </div>
            <div id="content-check">
                <hr>
                <div id="kiri">
                    <h3>Alamat Tagihan</h3>
                    <p>${customer.fullName}</p>
                    <p>${customer.address}</p>
                    <p>${customer.phone}</p>
                    <p>${customer.email}</p>
                </div>
                
                <div id="kanan">
                    <h3>Alamat Pengiriman</h3>
                    <p>${order.receiver}</p>
                    <p>${order.shippingAddress}</p>
                    <p>${order.city}, ${order.province}</p>
                    <p>${order.receiverEmail}</p>
                    <p>${order.receiverPhone}</p>
                </div>
                
                <div id="show-cart">
                    <table id="cart">
                      <thead>
                          <tr>
                              <th>Judul</th>
                              <th>Gambar</th>
                              <th style="width: 15%">Jumlah</th>
                              <th>Update</th>
                              <th>Hapus</th>
                              <th>Harga</th>
                              <th>Subtotal</th>
                          </tr>
                      </thead>
                      <tbody>
                          <c:forEach items="${books}" var="b">
                          <form method="post" action="<c:url value="/public/cart/update" />">
                          <input type="hidden" name="bookId" value="${b.key.id}"/>
                          <tr>
                              <td>${b.key.title}</td>
                              <td><img src="<c:url value="/img/${b.key.image}_small.jpg" />" /></td>
                              <td><input class="quantity" type="text" name="quantity" value="${b.value}" /></td>
                              <td><input type="submit" value="Update" /></td>
                              <td><a href="<c:url value="/public/cart/remove/${b.key.id}" />"><img src="<c:url value="/img/delete.png" />"/></a></td>
                              <td>${b.key.price}</td>
                              <td>${b.key.price*b.value}</td>
                          </tr>
                          </form>
                          </c:forEach>
                      </tbody>
                    </table>  

                    <div id="total">
                        Total : <c:out value="${total}"/>
                    </div>
                </div>
                    
                <div id="finalize-order" >
                    <a href="<c:url value="/secured/checkout/finalize"/>">Finalize Order</a>
                </div>
                
            </div>
        </div>
    </body>
</html>
