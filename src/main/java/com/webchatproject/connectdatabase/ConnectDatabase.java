/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.connectdatabase;

/**
 *
 * @author HANG.VTT183524
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author HANG.VTT183524
 */
public class ConnectDatabase {
    private static final long serialVersionUID = 1L;
    
    public static Connection conn;
    public static Statement statement;
    
    
    public static void connect(){
        statement = null;
	try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envContext.lookup("jdbc/Web_chat");
            conn = dataSource.getConnection();
            statement = conn.createStatement();
        } 
        catch (SQLException |NamingException e) 
        {
            System.err.println(e.getMessage());
        }
    }
    
    public static void insertIntoDatabase(String sql) 
    {
        try
        {
            boolean b = statement.execute(sql);
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
}
    
    public static ResultSet executeSql(String sql)
    {
        ResultSet rs = null;
        try
        {
            rs = statement.executeQuery(sql);
        }
        catch(SQLException ex)
        {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public static boolean check(String sql)
    {
        boolean check = false;
		try {	         
	            ResultSet rs  = statement.executeQuery(sql);
	            
	            // check if info was in database or not
	            if (rs.next()) check = true;
                    rs.close();
	        } 
		catch (SQLException e) {
	            System.out.println(e.getMessage());
	            check = false;
	        }
		return check;
    }
    
    public static void closeConnect()
    {
        try {
            statement.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConnectDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
