<%-- 
    Document   : socialMediaAccounts
    Created on : Feb 23, 2015, 5:51:48 PM
    Author     : Kevin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- header -->
<jsp:include page="include/header.jsp" />
<!-- user-navigation -->
<jsp:include page="include/user-navigation.jsp" />
<!-- site-navigation -->
<jsp:include page="include/site-navigation.jsp" />

<!-- individual page content -->
<section>
    <h1>Instagram</h1>
    
    <br><br>
    <h1>LinkedIn</h1>
    
    <br><br>
    <h1>Twitter</h1>
    <c:choose>
        <c:when test="${cookie.twitter_access_token.value == null}">
            <p>Twitter account not Linked</p>
            <form action="SocialMediaAccounts" method="post">
                <input type="hidden" name="accountToRegister" value="twitter">
                <input type="submit" value="Link Twitter Account">
            </form>
        </c:when>
        <c:otherwise>
            <p>Twitter account Linked</p>
            <form action="SocialMediaAccounts" method="post">
                <input type="hidden" name="accountToUnRegister" value="twitter">
                <input type="submit" value="UnLink Twitter Account">
            </form>
        </c:otherwise>
    </c:choose>

</section>

<!-- footer -->
<jsp:include page="include/footer.jsp" />
