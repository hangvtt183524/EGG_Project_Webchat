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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HANG.VTT183524
 */
public class SigninServlet extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("password");
        
//       boolean check = false;        
//        ConnectDatabase.connect();
//        check = ConnectDatabase.check("select * from User_Profile where email = '" + email + "' and password = '" + password + "' ;");
//        ConnectDatabase.closeConnect();

        User user = new User(email, password);
        boolean check = user.checkInfor();
        
        HttpSession session = request.getSession();
        
        if (check == true) 
        {
            session.setAttribute("user", user);
            request.getRequestDispatcher("test.jsp").forward(request, response);
        }
        else  
        {
            request.getRequestDispatcher("error_signin.html").forward(request, response);
        } 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
