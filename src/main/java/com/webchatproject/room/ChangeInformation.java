/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.room;

import Model.User;
import com.webchatproject.connectdatabase.ConnectDatabase;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author HANG.VTT183524
 */
@WebServlet("/changeInformation")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50,
                 location="/D:\\Code\\Servlet\\WebChatProject\\src\\main\\webapp")
public class ChangeInformation extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        
        String command = (String) request.getParameter("command");
        if (command == null) changeUserAvatar(request, response);
        else if (command.equals("not-avatar")) changeInformation(request, response);
        else 
        {
            //changeUserAvatar(request, response);
        }

    }
    
    private void changeInformation(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        String firstname = (String) request.getParameter("firstname");
        String lastname = (String) request.getParameter("lastname");
        String email = (String) request.getParameter("email");
        String password = (String) request.getParameter("password");
        
        ConnectDatabase connect = new ConnectDatabase();
        connect.insertIntoDatabase("update User_Profile set firstname = '" + firstname + "', lastname = '" + lastname + "', email = '" + email + "', password = '" + password + "' where user_id = " + user.getUserId()+ ";");
        
        user.setEmail(email);
        user.setPassword(password);
        user.setUsername(firstname + " " + lastname);
        
        connect.closeConnect();
        
        out.print(firstname + " " + lastname);
    }

    private void changeUserAvatar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        
        int length;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int user_id = user.getUserId();
        //get file from ajax, user can upload many file in the same time
        for (Part part : request.getParts()) {
        String fileName = extractFileName(part);
        // refines the fileName in case it is an absolute path
        if (fileName != null && fileName.length() > 0) {
            length = fileName.length();
            String tail1 = fileName.substring(length - 3);
            String tail2 = fileName.substring(length - 4);
            
            ConnectDatabase connect = new ConnectDatabase();
            
            // if fileName ends with png, jpg, jiff --> image
            File f = new File(user.getAvatar());
            f.delete();
            if (tail1.equals("png") || tail1.equals("jpg"))
                {
                    part.write("store\\image\\user-img\\" + user_id + "-avatar." + tail1);
                    connect.insertIntoDatabase("update User_Profile set avatar = 'store/image/user-img/" + user_id + "-avatar." + tail1 + "' where user_id = " + user.getUserId() + ";");
                    user.setAvatar("store/image/user-img/"+ user_id + "-avatar." + tail1);
                    out.print(user.getAvatar());
                }
            if (tail2.equals("jiff"))
            {
                part.write("store\\image\\user-img\\" + user_id + "-avatar." + tail2);
                connect.insertIntoDatabase("update User_Profile set avatar = 'store/image/user-img/" + user_id + "-avatar." + tail2 + "' where user_id = " + user.getUserId() + ";");
                user.setAvatar("store/image/user-img/"+ user_id + "-avatar." + tail2);
                out.print(user.getAvatar());
            }
            connect.closeConnect();
            }  
        }
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
