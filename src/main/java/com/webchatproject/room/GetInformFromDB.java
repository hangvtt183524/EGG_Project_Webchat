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
import java.util.ArrayList;
import java.util.List;
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
 @WebServlet("/getInformFromDB")
public class GetInformFromDB extends HttpServlet {

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
        
        if (command.equals("search")) doingSearch(request, response);
        if (command.equals("member")) getMember(request, response);
        if (command.equals("room")) getRoom(request, response);
        if (command.equals("chat")) getMess(request, response);
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
    
    private void doingSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        StringBuilder result = new StringBuilder("");
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select user_id, firstname, lastname, avatar from User_Profile;");
        
        

        try {
            while (rs.next())
            {
                result.append("<li id=\"" + ("user-" + rs.getString("user_id")) + "\" onclick=\"addToRoom(this)\">"
                        + "<a><img src=\"" + rs.getString("avatar") + "\" class=\"rounded-circle user_img_msg\">" + rs.getString("firstname") + " " + rs.getString("lastname")
                        + "</a></li>");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetInformFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect.closeConnect();
        out.print(result.toString());
    }
    
    private void getMember(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();

        String room_id = (String) request.getSession().getAttribute("room_id");
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select * from Participant join User_Profile on Participant.member_id = User_Profile.user_id where Participant.room_id = "+ Integer.parseInt(room_id) + " ;");
        
        StringBuilder inform = new StringBuilder("");
        
        try {
            while (rs.next())
            {
                inform.append("<div class=\"people\" id=\"user-" + rs.getString("member_id") + "\">"
                             +     "<img src=\"" + rs.getString("avatar") + "\" class=\"rounded-circle my-avatar\">"
                             +     "<span class=\"name\" style=\"color:rgb(100, 100, 100);\">" + rs.getString("firstname") + " " + rs.getString("lastname") + "</span>"
                             +     "<button type=\"button\" class=\"close\" data-dismiss=\"people\">&times;</button>"
                             + "</div>");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetInformFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect.closeConnect();
        
        out.print(inform.toString());
    }
    
    private void getRoom(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user != null)
        {
            int user_id = user.getUserId();
        
        StringBuilder inform = new StringBuilder("");
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select Chat_Room.room_id, Chat_Room.title, Chat_Room.avatar from Participant join Chat_Room on Participant.room_id = Chat_Room.room_id where Participant.member_id = " + user_id + " ;"); 
        
        try {
            while (rs.next())
            {
                inform.append("<li class=\"chat\">"
                            + "<a style=\"text-decoration: none; color: rgb(100, 100, 100)\" onclick=\"startChat(this)\" id=\"" + ("room-" + rs.getString("room_id")) + "\">"
                            +     "<div style=\"display: flex;\">"
                            +         "<div style=\"height: 50px;\">"
                            +              "<img src=\"libs/image/" + rs.getString("avatar") +  "\" class=\"rounded-circle avatar\">"
                            +         "</div>"
                            +         "<div style=\"margin-left: 10px;\">"
                            +              "<div style=\"height: 30px;\">"
                            +                   "<span class=\"chat-name\">" + rs.getString("title") + "</span>"
                            +              "</div>"
                            +              "<div style=\"display: flex;\">"
                            +                   "<div style=\"height: 20px;\">"
                            +                        "<span></span>"
                            +                   "</div>"
                            +                   "<div style=\"height: 20px;\">"
                            +                        "<span></span>"
                            +                   "</div>"
                            +              "</div>"
                            +         "</div>"
                            +     "</div>"
                            +  "</a>"
                            +"</li>");
            }
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetInformFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connect.closeConnect();
        out.print(inform);
        }
    }
    
    private void getMess(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int user_id = user.getUserId();
        
        String room_id = (String) request.getSession().getAttribute("room_id");
        StringBuilder result = new StringBuilder("");
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select m.content, m.author_id, u.avatar from Messenger as m join User_Profile as u on m.author_id = u.user_id where m.room_id = " + room_id + " ;");
        
        try {
            while (rs.next())
            {
                if (Integer.parseInt(rs.getString("author_id")) == user_id)
                    result.append("<div class=\"d-flex justify-content-end mb-4\">"
                                  +     "<div class=\"msg_cotainer_send\">"
                                  +        rs.getString("content")
                                  +     "</div></div>");
                else result.append("<div class=\"d-flex justify-content-start mb-4\">"
                             +     "<div class=\"img_cont_msg\">"
                             +         "<img src=\"" + rs.getString("avatar") + "\" class=\"rounded-circle user_img_msg\">"
                             +     "</div>"
                             +     "<div class=\"msg_cotainer\">"
                             +         rs.getString("content")
                             +         "<span class=\"msg_time>8:40 AM, Today</span>"
                             +     "</div>"
                             + "</div>");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(GetInformFromDB.class.getName()).log(Level.SEVERE, null, ex);
        }
       connect.closeConnect();
        out.print(result.toString());
    }
}