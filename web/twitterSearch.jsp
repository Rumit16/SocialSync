<%-- 
    Document   : feedView
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
    <form action="FeedView">
        <input type="hidden" name="site" value="twitter">
        <table class="twitterSearch">
            <tr>
                <th class="twitterSearch">Input Search Query (User, Hashtag, or Subject)</td>
                <td><input type="text" name="searchQuery" value="${searchQuery}"></td>
            </tr>
            <tr class="twitterSearch">
                <th class="twitterSearch">Input Number of results you want (default is 10)</th>
                <td><input type="number" name="numberOfResults" min="1" max="100" value="${numberOfResults}"></td>
            </tr>
            <tr class="twitterSearch">
                <th class="twitterSearch"></th>
                <td><input type="submit" value="submit"></td>
            </tr>
        </table>
        <br>
    </form>

    <br>

    <c:if test="${posts != null}">

        <c:forEach var="post" items="${posts}">
            <article class="tweet">
                <h3 class="tweetUserImage"><img src="${post.userImageURL}"></h3>
                <h3 class="tweetName">${post.name}</h3>
                <h3 class="tweetScreenName">@${post.screenName}</h3>
                <h3 class="tweetTime">${post.timeStamp}</h3>
                <h3 class="tweetText">${post.text}</h3>
            </article>
            <br>
        </c:forEach>
    </c:if>
</section>

<!-- footer -->
<jsp:include page="include/footer.jsp" />
