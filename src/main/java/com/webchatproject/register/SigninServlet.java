/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.register;

import Model.User;
import com.webchatproject.connectdatabase.ConnectDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HANG.VTT183524
 */
@WebServlet("/login")
public class SigninServlet extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = (String) request.getParameter("action");
        HttpSession session = request.getSession();
        
        if (action == null)
        {
            User user = checkCookie(request);
            if (user == null) 
                request.getRequestDispatcher("signin.html").forward(request, response);
            else {
                boolean checkInfor = user.checkInfor();
                if (checkInfor)
                {
                    session.setAttribute("user", user);
                    request.getRequestDispatcher("chat2.html").forward(request, response);
                }
                else
                {
                    request.getRequestDispatcher("signin.html").forward(request, response);
                }
            }
        }
        else
        {
            if (action.equalsIgnoreCase("logout"))
            {
                session.removeAttribute("user");
                Cookie[] cookie = request.getCookies();
                for (Cookie k : cookie)
                {
                    if (k.getName().equalsIgnoreCase("email"))
                    {
                        k.setMaxAge(0);
                        response.addCookie(k);
                    }
                    if (k.getName().equalsIgnoreCase("password"))
                    {
                        k.setMaxAge(0);
                        response.addCookie(k);
                    }
                }
                request.getRequestDispatcher("signin.html").forward(request, response);
            }
        }
    }
    
     private User checkCookie(HttpServletRequest request)
     {
         Cookie[] cookie = request.getCookies();
         User user = null;
         if (cookie == null)
         {
             return null;
         }
         else
         {
            String email = "", password = "";
            for (Cookie k : cookie)
            {
                if (k.getName().equalsIgnoreCase("email"))
                    email = k.getValue();
                if (k.getName().equalsIgnoreCase("password"))
                    password = k.getValue();
            }
            if (!email.isEmpty() && !password.isEmpty())
            {
                user = new User(email, password);
                
            }
                    
         
            
            return user;
     }
     }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = (String) request.getParameter("action");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        if (action == null)
        {
            String email = (String) request.getParameter("email");
            String password = (String) request.getParameter("password");
            
            boolean remember = request.getParameter("remember") != null;
            
        
//       boolean check = false;        
//        ConnectDatabase.connect();
//        check = ConnectDatabase.check("select * from User_Profile where email = '" + email + "' and password = '" + password + "' ;");
//        ConnectDatabase.closeConnect();

            User user = new User(email, password);
            boolean check = user.checkInfor();
       
        if (check == true) 
        {
            session.setAttribute("user", user);
            if (remember)
            {
                    Cookie ckEmail = new Cookie("email", email);
                    ckEmail.setMaxAge(3600);
                    response.addCookie(ckEmail);
                    
                    Cookie ckPassword = new Cookie("password", password);
                    ckPassword.setMaxAge(3600);
                    response.addCookie(ckPassword);
            }
            request.getRequestDispatcher("chat2.html").forward(request, response);
        }
        else  
        {
            request.getRequestDispatcher("error_signin.html").forward(request, response);
        }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
