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

    <c:forEach var="post" items="${posts}">
        <c:choose>
            <c:when test="${post.type == 'Tweet'}">
                <article class="tweet">
                    <h3 class="tweetUserImage"><img src="${post.userImageURL}"></h3>
                    <h3 class="tweetName">${post.name}</h3>
                    <h3 class="tweetScreenName">@${post.screenName}</h3>
                    <h3 class="tweetTime">${post.timeStamp}</h3>
                    <h3 class="tweetText">${post.text}</h3>
                </article>
                <br>
            </c:when>
            <c:otherwise>
                <article class="insta">
                    <h3 class="instaLink"><a href="${post.link}">${post.link}</a></h3>
                    <h3 class="instaProfilePic"><img src="${post.profilePictureURL}" height="50" width="50">  ${post.userName}</h3>
                    <!--<h3 class="instaUsername"></h3>-->
                    <h3 class="instaMedia">
                        <c:choose>
                            <c:when test="${post.mediaType == 'image'}">
                                <img src="${post.mediaURL}" width="100%">
                            </c:when>
                            <c:otherwise>
                                <video width="100%" controls>
                                    <source src="${post.mediaURL}" type="video/mp4">
                                </video>
                            </c:otherwise>
                        </c:choose>
                    </h3>
                    <h3 class="instaLikes">${post.likes} Likes</h3>
                    <h3 class="instaCaption">${post.caption}</h3>
                </article>
                <br>
            </c:otherwise>
        </c:choose>
    </c:forEach>


</section>

<!-- footer -->
<jsp:include page="include/footer.jsp" />
