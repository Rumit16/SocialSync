<%-- 
    Document   : register
    Created on : Mar 13, 2015, 3:35:37 PM
    Author     : Kevin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="include/header.jsp" />
<section style="width:100%; margin: 0; padding:5; overflow:hidden;"
    <h1>SocialSync</h1>
    <h2>Connecting Your Social Networks...</h2>
    <form action="Register" method="post">
        <input type="hidden" name="action" value="register">
        <label>First Name</label>
        <input type="text" name="firstName" value="${firstName}"><br>
        <label>Last Name</label>
        <input type="text" name="lastName" value="${lastName}"><br>
        <label>Email (This will be your UserID</label>
        <input type="email" name="email" value="${email}"><br>
        <label>Verify Email</label>
        <input type="email" name="verifyEmail"><br>
        <label>Password</label>
        <input type="password" name="password"><br>
        <label>Verify Password</label>
        <input type="password" name="verifyPassword"><br>
        <input type="submit" value="Register"><br>
    </form>
</section>
<jsp:include page="include/footer.jsp" />