/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import accountHelpers.InstagramHelper;
import accountHelpers.TwitterHelper;
import beans.Insta;
import beans.Tweet;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Kevin
 */
@WebServlet("/FeedView")
public class FeedViewController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String url = "/feedView.jsp";
        ArrayList<Object> posts = new ArrayList<>();

        if (request.getParameterMap().containsKey("site")) {
            String site = request.getParameter("site");
            if (site.equals("facebook")) {
                //Display only Facebook items
                request.setAttribute("facebook", "fb");

            } else if (site.equals("instagram")) {
                //Display only Instagram items

                ArrayList<Insta> instas = getInstagramPosts(request.getServletContext(), null, 0);
                if (!instas.isEmpty()) {
                    for (Insta insta : instas) {
                        posts.add(insta);
                    }
                }

                request.setAttribute("posts", posts);

            } else if (site.equals("linkedIn")) {
                //Display only LinkedIn items
                request.setAttribute("linkedIn", "li");

            } else if (site.equals("twitter")) {
                url = "/twitterSearch.jsp";
                //Display only Twitter items
                String access_token = getTwitterAccessToken(request);
                String searchQuery = "twitterapi";
                if (request.getParameterMap().containsKey("searchQuery")) {
                    searchQuery = request.getParameter("searchQuery");
                    request.setAttribute("searchQuery", searchQuery);
                }
                int numberOfResults = 10;
                if (request.getParameterMap().containsKey("numberOfResults")) {
                    switch (request.getParameter("numberOfResults")) {
                        case "":
                            numberOfResults = 10;
                            break;
                        default:
                            numberOfResults = Integer.parseInt(
                                    request.getParameter("numberOfResults"));
                            break;
                    }
                    request.setAttribute("numberOfResults", numberOfResults);
                }

                if (access_token != null) {
                    ArrayList<Tweet> tweets = getTweets(request, access_token, numberOfResults, searchQuery);
                    if (!tweets.isEmpty()) {
                        for (Tweet tweet : tweets) {
                            posts.add(tweet);
                        }
                    }
                    request.setAttribute("posts", posts);
                }
            }
        } else {
            //Display mixed feed

            String access_token = getTwitterAccessToken(request);
            if (access_token != null) {
                ArrayList<Tweet> tweets = getTweets(request, access_token, 0, null);
                if (!tweets.isEmpty()) {
                    for (Tweet tweet : tweets) {
                        posts.add(tweet);
                    }
                }
            }
            
            ArrayList<Insta> instas = getInstagramPosts(request.getServletContext(), null, 0);
                Random rand = new Random();
            
                if (!instas.isEmpty()) {
                    for (Insta insta : instas) {
                        posts.add(rand.nextInt(posts.size()), insta);
                    }
                }

            request.setAttribute("posts", posts);
        }

        //request.setAttribute("posts", posts);
        //dispatch to correct page based on parameters
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private ArrayList<Tweet> getTweets(HttpServletRequest request,
            String access_token, int numberOfResults, String searchQuery) throws UnsupportedEncodingException {
        ArrayList<Tweet> tweets = new ArrayList<>();

        TwitterHelper th = new TwitterHelper(request.getServletContext(), request);
        String result = null;

        if (searchQuery == null) {
            result = th.getTweets(null, access_token, 0, null);
        } else {
            switch (searchQuery.charAt(0)) {
                case '@':
                    result = th.getTweets("https://api.twitter.com/1.1/statuses/user_timeline.json",
                            access_token,
                            numberOfResults,
                            searchQuery.substring(1));
                    break;
                default:
                    result = th.getTweets("https://api.twitter.com/1.1/search/tweets.json?q="
                            + URLEncoder.encode(searchQuery, "UTF-8"), access_token,
                            numberOfResults, searchQuery);
                    break;
            }
        }

        if (result != null) {
            JSONArray jArr;
            JSONObject jObj;
            JSONObject jUser;

            if (result.charAt(0) == '[') {
                jArr = (JSONArray) JSONValue.parse(result);
            } else {
                jArr = (JSONArray) (((JSONObject) JSONValue.parse(result))
                        .get("statuses"));
                log("*** result: " + result);
            }
            for (int a = 0; a < jArr.size(); a++) {
                jObj = (JSONObject) jArr.get(a);
                jUser = (JSONObject) jObj.get("user");
                String timeStamp = (String) jObj.get("created_at");
                timeStamp = timeStamp.substring(0,
                        timeStamp.indexOf("+") - 1);
                tweets.add(new Tweet(
                        timeStamp,
                        (String) jUser.get("profile_image_url"),
                        (String) jUser.get("name"),
                        (String) jUser.get("screen_name"),
                        (String) jObj.get("text")));
            }
        }

        return tweets;
    }

    private String getTwitterAccessToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cook : cookies) {
                if ("twitter_access_token".equals(cook.getName())) {
                    return cook.getValue();
                }
            }
        }
        return null;
    }

    private ArrayList<Insta> getInstagramPosts(ServletContext context, String userId, int count) {
        ArrayList<Insta> instas = new ArrayList<>();

        InstagramHelper iH = new InstagramHelper();
        String result = iH.getInstagramPics(context, userId, count);

        JSONArray jArr = (JSONArray) (((JSONObject) JSONValue.parse(result))
                .get("data"));
        JSONObject jObj;
        JSONObject jObj2;

        String caption = ""; //caption.text
        long likes = 0; //likes.count
        String link; //link
        String userName; //user.username
        String fullName; //user.full_name
        String profilePictureURL; //user.profile_picture
        long createdTime; //created_time
        String mediaType; //type
        String mediaURL; //images.standard_resolution.url
        long mediaWidth; //images.standard_resolution.width
        long mediaHeight; //images.standard_resolution.height

        for (int a = 0; a < jArr.size(); a++) {
            jObj = (JSONObject) jArr.get(a);
            jObj2 = (JSONObject) jObj.get("caption");
            if (jObj2 != null) {
                caption = (String) jObj2.get("text");
            }
            jObj2 = (JSONObject) jObj.get("likes");
            likes = (long) jObj2.get("count");
            link = (String) jObj.get("link");
            jObj2 = (JSONObject) jObj.get("user");
            userName = (String) jObj2.get("username");
            fullName = (String) jObj2.get("full_name");
            profilePictureURL = (String) jObj2.get("profile_picture");
            createdTime = Long.parseLong((String) jObj.get("created_time"));
            mediaType = (String) jObj.get("type");
            switch (mediaType) {
                case "image":
                    jObj2 = (JSONObject) ((JSONObject) jObj.get("images"))
                            .get("standard_resolution");
                    break;
                default:
                    jObj2 = (JSONObject) ((JSONObject) jObj.get("videos"))
                            .get("standard_resolution");
                    break;
            }

            mediaURL = (String) jObj2.get("url");
            mediaWidth = (long) jObj2.get("width");
            mediaHeight = (long) jObj2.get("height");

            instas.add(new Insta(caption, likes, link, userName, fullName,
                    profilePictureURL, createdTime, mediaType, mediaURL, mediaWidth,
                    mediaHeight));
        }

        return instas;
    }
}
