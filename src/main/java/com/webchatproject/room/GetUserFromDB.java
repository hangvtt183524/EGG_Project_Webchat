/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.room;

import com.webchatproject.connectdatabase.ConnectDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author HANG.VTT183524
 */
 @WebServlet("/getUserFromDB")
public class GetUserFromDB extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String name = (String) request.getParameter("name");

        String user_id = null;
        String username = null;
        String inform = "";
        
        if (!name.equals("") && name != null)
        {
            ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select * from User_Profile where firstname like '%" + name +"%' or lastname like '%" + name +"%' ;");
        
        try {
            while (rs.next())
            {
                username = rs.getString("firstname") + " " + rs.getString("lastname");
                user_id = rs.getString("user_id");
                inform += "<li><a id=\"" + user_id + "\" onclick=\"showResult(this)\">" + username + "</a></li>";
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetUserFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect.closeConnect();
        }
        out.print(inform);
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
        PrintWriter out = response.getWriter();

        String room_id = (String) request.getParameter("room_id");
        request.getSession().removeAttribute("room_id");
        request.getSession().setAttribute("room_id", room_id);
        String member_id;
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select * from Participant join User_Profile on Participant.member_id = User_Profile.user_id where Participant.room_id = "+ Integer.parseInt(room_id) + " ;");
        
        String inform = "";
        
        try {
            while (rs.next())
            {
                inform += "<li><a id=\""+ rs.getString("user_id") + "\"><i class=\"fa fa-circle text-success\"></i>" + rs.getString("firstname") + " " + rs.getString("lastname") + "</a></li>";
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetUserFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect.closeConnect();
        
        out.print(inform);
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
