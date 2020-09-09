/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.room;

import Model.User;
import com.webchatproject.connectdatabase.ConnectDatabase;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HANG.VTT183524
 */
public class CreateNewRoom extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        String roomName = (String) request.getParameter("roomName");
        String room_id = null;
        
        HttpSession session = request.getSession();
        User creator = (User) session.getAttribute("user");
        int user_id = creator.getUserId();
        
        ConnectDatabase connect = new ConnectDatabase();
        connect.insertIntoDatabase("insert into Chat_Room values ('" + roomName + "', " + user_id + ", null);");
        
        ResultSet rs = connect.executeSql("select top 1 * from Chat_Room where creator_id = '" + user_id + "' order by room_id desc;");
        try {
            if (rs.next())
            { 
                room_id = rs.getString("room_id");
                connect.insertIntoDatabase("insert into Participant values ('" + user_id + "', " + room_id + ");");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(CreateNewRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connect.closeConnect();
          
        out.print(room_id);
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
