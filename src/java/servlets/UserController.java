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
@WebServlet(urlPatterns={"/Login","/Register","/User"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        log("****usercontroller");
        
        
        //Default redirect should be to feedViewController for a signed in user
        String url = "/register.jsp";
        HttpSession session = request.getSession();
        
        String action = request.getParameter("action");
        log("**" + action);
        
        //In the case that user is not logged in, send to login page
        if (session.getAttribute("userID") == null) {
            //normally
            url = "/register.jsp";
            
            //for first purposes, redirect to socialMediaAccounts page
            //session.setAttribute("userID", "kc_testUser");
            //url = "/SocialMediaAccounts";
        } 
        //Process user login request
        else if (request.getParameterMap().containsKey("login")) {
                
            //To-Do connect to authentication method (ex parse.com db)
            /*
                if (//identity verified) {
                    url = "/index.jsp"; 
                } else {
                    //To-Do throw sign-in error
                }
            */
        } 
        //Process user register request
        if (action != null && action.equals("register")) {
            log("*****register");
            //request.setAttribute("userID", "a");
            /*
            String firstName;
            String lastName;
            String email;
            String verifyEmail;
            String password;
            String verifyPassword;

            //Checks to make sure all parameters are present
            if (request.getParameterMap().containsKey("firstName")) {
                firstName = request.getParameter("firstName");
            } else {
                //Throw registration error for blank first name
            }
            if (request.getParameterMap().containsKey("lastName")) {
                lastName = request.getParameter("lastName");
            } else {
                //Throw registration error for blank last name
            }
            if (request.getParameterMap().containsKey("email")) {
                email = request.getParameter("email");

                /*
                 if (//email already exists in db) {
                 //Throw registration error for existing account associated with provided email
                 }
                 *//*
                if (request.getParameterMap().containsKey("verifyEmail")) {
                    verifyEmail = request.getParameter("verifyEmail");
                    if (!email.equals(verifyEmail)) {
                        //Throw registration error for non-matching emails 
                    }
                } else {
                    //Throw registration error for blank verify email
                }
            } else {
                //Throw registration error for blank email
            }
            if (request.getParameterMap().containsKey("password")) {
                password = request.getParameter("password");
                if (request.getParameterMap().containsKey("verifyPassword")) {
                    verifyPassword = request.getParameter("verifyPassword");
                    if (!password.equals(verifyPassword)) {
                        //Throw registration error for non-matching passwords 
                    }
                } else {
                    //Throw registration error for blank verify email
                }
            } else {
                //Throw registration error for blank verify email
            }
            */
            //To-Do create new user for db
            
            //Upon successful registration, send to socialMediaAccounts page
            url = "/SocialMediaAccounts";
        }

        //dispatch to correct page based on parameters
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
