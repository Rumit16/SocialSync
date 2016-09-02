<%-- 
    Document   : login
    Created on : Mar 13, 2015, 3:35:37 PM
    Author     : Kevin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<jsp:include page="include/header.jsp" />
    <h1>SocialSync</h1>
    <h2>Connecting Your Social Networks...</h2>
    <form action="Login" method="post">
        <label>UserID</label>
        <input type="text" name="userID"><br>
        <label>Password</label>
        <input type="password" name="password"><br>
        <input type="submit" value="login"><br>
        <input type="hidden" name="action" value="login">
    </form>
    <a href="">forgot password</a><br>
    <a href="">Sign Up</a>
<jsp:include page="include/footer.jsp" />