<%-- 
    Document   : insta
    Created on : May 3, 2015, 2:52:01 PM
    Author     : Rumit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
          
<jsp:include page="include/header.jsp" />
<!-- user-navigation -->
<jsp:include page="include/user-navigation.jsp" />
<!-- site-navigation -->
<jsp:include page="include/site-navigation.jsp" />
<!DOCTYPE html>

<section>
    <h2>Gallery</h2>

    <div id="dump"></div>
		
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
		
		<script>
        $(document).ready(function(){
            $.ajax({
            type: 'GET',
            url: 'https://api.instagram.com/v1/media/popular?access_token=1999651154.c17e29d.2aa7bab26f624a9b96c4865fe71fd464',
            dataType: 'jsonp',
            success: function(resp) {
                console.log(resp);
                for(var i=0;i<resp.data.length;i++){
                    if(resp.data[i].type==="image") {
                        str = "<div class='singleItem'>";
                        str += "<img src='" + resp.data[i].images.standard_resolution.url + "' height='200' width='200'/>";
						str += "<p>"+resp.data[i].caption.text+"</p>";
						str += "<p style='text-align:right'>-"+resp.data[i].caption.from.username+"</p>";
                        str += "<p><strong>Likes : </strong></p>";
                        if (resp.data[i].likes.count > 3) {
                            for (var j = 0; j < 3; j++) {
                                str += "<p>" + resp.data[i].likes.data[j].username + "</p>";
                            }
							str+="<p>more "+(resp.data[i].likes.count-3)+" likes</p>";
                        }
						else if(resp.data[i].likes.count > 0) {
                            for (var j = 0; j < resp.data[i].likes.count; j++) {
                                str += "<p>" + resp.data[i].likes.data[j].username + "</p>";
                            }
							str+="<p>more "+(resp.data[i].likes.count-3)+" likes</p>";
                        }
                        else{
                            str+="<p>No Likes</p>";
                        }
                        str += "</p><p><strong>Comments : </strong></p>";
                        if (resp.data[i].comments.count > 3) {
                            str+="<ul>"
                            for (var j = 0; j < 3; j++) {
                                str += "<li>"+resp.data[i].comments.data[j].text+"<strong> - "+resp.data[i].comments.data[j].from.username+"</strong>"+"</li>";
                            }
                            str+="</ul>"
							str+="<p>more "+(resp.data[i].comments.count-3)+" comments</p>";
                        }
						else if (resp.data[i].comments.count > 0) {
                            str+="<ul>"
                            for (var j = 0; j < resp.data[i].comments.count; j++) {
                                str += "<li>"+resp.data[i].comments.data[j].text+"<strong> - "+resp.data[i].comments.data[j].from.username+"</strong>"+"</li>";
                            }
                            str+="</ul>"
                        }
                        else{
                            str+="<p>No Comments</p>";
                        }
                        str+="</div>";
                        $("#dump").append(str);
                    }
                }
            },
            error: function(){alert("Error");}
            })
        })
		</script>
                
</section>

<!--footer-->
<jsp:include page="include/site-navigation.jsp" />