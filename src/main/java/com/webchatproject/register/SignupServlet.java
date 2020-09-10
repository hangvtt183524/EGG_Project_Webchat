/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.register;

import com.webchatproject.connectdatabase.ConnectDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HANG.VTT183524
 */
@WebServlet("/signupServlet")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        String result = "";
        
        String firstname = (String) request.getParameter("firstname");
        String lastname = (String) request.getParameter("lastname");
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("password");
        String confPassword = (String) request.getParameter("confPassword");
        
        
        
        if (firstname == null || firstname.equals(""))
            result = "1";
        else if (lastname == null || lastname.equals(""))
            result = "2";
        else if (email == null || email.equals(""))
            result = "3";
        else if (password == null || password.equals(""))
            result = "4";
        else if (confPassword == null || confPassword.equals(""))
             result = "5";
        else if (!password.equals(confPassword))
             result = "6";
        else
        {
            ConnectDatabase connect = new ConnectDatabase();
            boolean check = connect.check("select * from User_Profile where email = '" + email + "' ;");
            
        if (check == true)
        {
            result = "7";
        }
        else 
        {
                
                boolean b = connect.insertIntoDatabase("insert into User_Profile values ('"+ firstname + "', '" + lastname + "', '"+ password + "', '"
                        + email + "', null, null);");
        }
        connect.closeConnect();
        }
        
        out.print(result);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
