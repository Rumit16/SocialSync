/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Kevin
 */

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        //Default url direct should be login page
        String url = "login.jsp";
        
        //Other direct options based on attributes of session or request
        //Gets the session
        HttpSession session = request.getSession();
        if (session.getAttribute("userID") != null) {
            //send to FeedViewController
            url = "/feed";
        } else if (request.getParameterMap().containsKey("userID") && 
                request.getParameterMap().containsKey("password")) {
                
            //To-Do connect to authentication method (ex parse.com db)
            /*
                if (//identity verified) {
                    url = "/index.jsp"; 
                } else {
                    //To-Do throw sign-in error
                }
            */
        } else if (request.getParameterMap().containsKey("register")) {
            url = "/register";
        } 
            
        //dispatch to correct page based on parameters
        getServletContext()
                .getRequestDispatcher(url)
                .forward(request, response);

    }

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
