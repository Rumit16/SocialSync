<%-- 
    Document   : linkedin
    Created on : Apr 28, 2015, 3:36:26 PM
    Author     : Rumit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- <script type="text/javascript" src="http://platform.linkedin.com/in.js">
  api_key: 789ofvnw6xfwnn
  authorize: true
 
 function OnLinkedInFrameworkLoad() {
  IN.Event.on(IN, "auth", OnLinkedInAuth);
  }
  
  function OnLinkedInAuth() {
  IN.API.Profile("me").result(ShowProfileData);
  }
  
  function ShowProfileData(profiles) {
  var member = profiles.values[0];
  var id=member.id;
  var firstName=member.firstName; 
  var lastName=member.lastName; 
  var photo=member.pictureUrl; 
  var headline=member.headline; 

//use information captured above
  }
 </script> -->

<!--
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>

<!-- 1. Include the LinkedIn JavaScript API and define a onLoad callback function

<script type="text/javascript" src="http://platform.linkedin.com/in.js">

    api_key: 789
    ofvnw6xfwnn

            scope: r_network, r_emailaddress, r_fullprofile, r_basicprofile, r_contactinfo

</script>

<script type="text/javascript">

    function searchClick() {

        alert($("#firstNameId").val() + ":" + $("#lastNameId").val());

        if (!IN.ENV.auth.oauth_token) {

            alert("You must login w/ LinkedIn to use the Search functionality!");

            return;

        }

        IN.API.PeopleSearch().fields("id", "firstName", "lastName", "emailAddress", "headline", "industry", "pictureUrl", "positions", "summary", "numConnections")

                .params({"first-name": $("#firstNameId").val(), "last-name": $("#lastNameId").val(), "count": 25})

                .result(function (result, metadata) {

                    setSearchResults(result.people.values);

                });

    }

    function setSearchResults(values) {

        alert("HEllo");
        var table = $("#resulttable");
        table.append('<tr><th>First Name</th><th>Last Name</th><th>Head Line</th><th>Industry</th><th>Picture</th><th>No Of Connections</th><th>Summary</th><th>Positions</th></tr>');
        for (i in values) {
            try {
                var person = values[i];
                var positionsStr = "<ul>";
                for (i in person.positions.values) {
                    positionsStr += "<li>" + person.positions.values[i].company.name + "</li>";
                }
                console.log(positionsStr);
                table.append('<tr><td>' + person.firstName + '</td><td>' + person.lastName + '</td><td>' + person.headline + '</td><td>' + person.industry + '</td><td><img src="' +
                        person.pictureUrl + '"/></td><td>' + person.numConnections + '</td><td>' + person.summary + '</td><td>' + positionsStr + '</ul></td></tr>')
            } catch (err) {
                alert(err);
            }
        }
    }

</script>

-->
<jsp:include page="include/header.jsp" />
<!-- user-navigation -->
<jsp:include page="include/user-navigation.jsp" />
<!-- site-navigation -->
<jsp:include page="include/site-navigation.jsp" />
<!DOCTYPE html>

<section>

    <!-- 1. plugin -->
    <script src="//platform.linkedin.com/in.js" type="text/javascript"></script>
    <script type="IN/MemberProfile" data-id="https://www.linkedin.com/pub/rumit-gajera/25/2b3/414" data-format="inline"></script>

    <script src="//platform.linkedin.com/in.js" type="text/javascript"></script>
    <script type="IN/JYMBII" data-format="inline"></script>
    <!--1. -->

</section>

<!-- footer -->
<jsp:include page="include/footer.jsp" />