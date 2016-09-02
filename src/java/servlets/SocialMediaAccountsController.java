package servlets;

import accountHelpers.TwitterHelper;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Kevin
 */
@WebServlet("/SocialMediaAccounts")
public class SocialMediaAccountsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        */
        
        String url = "/socialMediaAccounts.jsp";

        if (request.getParameterMap().containsKey("accountToRegister")) {
            String acct = request.getParameter("accountToRegister");
            //Process facebook account link request
            if (acct.contains("facebook")) {

            } //Process instagram account link request
            else if (acct.equals("instagram")) {

            } //Process linkedIn account link request
            else if (acct.equals("linkedIn")) {

            } //Process twitter account link request
            else if (acct.equals("twitter")) {
                TwitterHelper th = new TwitterHelper(request.getServletContext(), request);
                String access_token = th.obtainBearerToken();
                Cookie cookie = new Cookie("twitter_access_token", access_token);
                cookie.setMaxAge(60 * 60 * 24 * 365);
                cookie.setPath("/");
                response.addCookie(cookie);
                log("twitter_access_token cookie added");
            }
        }
        
        if (request.getParameterMap().containsKey("accountToUnRegister")) {
            String acct = request.getParameter("accountToUnRegister");
            //Process facebook account link request
            if (acct.contains("facebook")) {

            } //Process instagram account link request
            else if (acct.equals("instagram")) {

            } //Process linkedIn account link request
            else if (acct.equals("linkedIn")) {

            } //Process twitter account link request
            else if (acct.equals("twitter")) {
                TwitterHelper th = new TwitterHelper(request.getServletContext(), request);
                Cookie[] cookies = request.getCookies();
                String access_token = null;
                for (Cookie cook: cookies){
                    if (cook.getName().equals("twitter_access_token")) {
                        access_token = cook.getValue();
                    }       
                }
                
                if ((access_token != null) && th.invalidateToken(access_token, request)) {
                    Cookie cookie = new Cookie("twitter_access_token", null);
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    log("twitter_access_token invalidated, account unlinked");
                }
            }
        }
        
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);
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

}
