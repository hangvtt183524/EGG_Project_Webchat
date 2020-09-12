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
public class AddMemberIntoRoom extends HttpServlet {
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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        String member_id = ((String) request.getParameter("member_id")).substring(5);
        String room_id = (String) session.getAttribute("room_id");
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select * from Participant where member_id = " + Integer.parseInt(member_id) + " and room_id = "+ Integer.parseInt(room_id) + ";");
        
        try {
            if (rs.next())
            {
              out.print(false);
            }
            else 
            {
                 boolean b = connect.insertIntoDatabase("insert into Participant values ("+ Integer.parseInt(member_id) + ", "+ Integer.parseInt(room_id) + ");");
                 out.print(member_id);
            }
            rs.close();
                
        } catch (SQLException ex) {
            Logger.getLogger(AddMemberIntoRoom.class.getName()).log(Level.SEVERE, null, ex);
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
