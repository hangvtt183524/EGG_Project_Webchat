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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author HANG.VTT183524
 */
// this class handle when a user want to quit from a chat-room or admin want to delete a conversation
public class OutRoom extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        
        String command = (String) request.getParameter("command");
        
        if (command.equals("quit")) quitRoom(request, response);
        if (command.equals("delete")) deleteRoom(request, response);
    }
   
    private void quitRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        
        User user = (User) session.getAttribute("user");
        int user_id = user.getUserId();
        String room_id = (String) session.getAttribute("room_id");
        
        // check is user who wanted to quit is admin of this room or not. 
        // if true, don't allow quit. Because only admin could delete a room. If admin quit before delete room, that room will be never deleted by someone
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select creator_id from Chat_Room where room_id = " + room_id + " ;");
        
        try {
            if (rs.next())
            {
                if (user_id == Integer.parseInt(rs.getString("creator_id")))
                    out.print(false);
                else 
            {
                // if this user isn't admin, delete him from DB
                connect.insertIntoDatabase("delete from Participant where room_id = " + room_id + " and member_id = " + user_id + " ;");
                out.print(room_id);
                
            }
            }
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(OutRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect.closeConnect();
    }
    
    private void deleteRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        
        User user = (User) session.getAttribute("user");
        int user_id = user.getUserId();
        String room_id = (String) session.getAttribute("room_id");
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select creator_id from Chat_Room where room_id = " + room_id + " ;");
        
        // delete if user who click delete-button is admin of this room
        try {
            if (rs.next())
            {
                if (user_id == Integer.parseInt(rs.getString("creator_id")))
                {
                    connect.insertIntoDatabase("delete from Participant where room_id = " + room_id + " ;");
                    connect.insertIntoDatabase("delete from Messenger where room_id = " + room_id + " ;");
                    connect.insertIntoDatabase("delete from Chat_Room where room_id = " + room_id + " ;");
                    out.print(room_id);
                }
                else out.print(false);
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(OutRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect.closeConnect();
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
