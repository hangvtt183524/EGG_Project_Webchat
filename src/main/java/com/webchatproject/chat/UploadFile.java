/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.chat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author HANG.VTT183524
 */
@WebServlet("/uploadFile")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
                 maxFileSize=1024*1024*10,      // 10MB
                 maxRequestSize=1024*1024*50,
                 location="/D:\\Code\\Servlet\\WebChatProject\\src\\main\\webapp") 
public class UploadFile extends HttpServlet {
    @Override
    protected synchronized void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        int length;
        String room_id = (String) request.getSession().getAttribute("room_id");
        //get file from ajax, user can upload many file in the same time
        for (Part part : request.getParts()) {
        String fileName = extractFileName(part);
        // refines the fileName in case it is an absolute path
        if (fileName != null && fileName.length() > 0) {
            length = fileName.length();
            // if fileName ends with png, jpg, jiff --> image
            if (fileName.substring(length - 3).equals("png") || fileName.substring(length - 3).equals("jpg") || fileName.substring(length - 4).equals("jiff"))
                {
                    part.write("store\\image\\con-img\\" + room_id + "-"+ fileName);
                    out.print("<img src=\"store/image/con-img/" + room_id + "-" + fileName + "\">");
                }
            // store files aren't image
            else 
                {
                    part.write("store\\file\\" + room_id + "-" + fileName); 
                    out.print("<div class=\"message-file\">"+"<u>"+ room_id + "-" + fileName +"</u>"+ "</div>");
                }
            
            
            }
        
            
        }
        
        
    }
    /**
     * Extracts file name from HTTP header content-disposition
     */
    // get file-name
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
}
