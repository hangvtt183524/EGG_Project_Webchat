/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.webchatproject.connectdatabase.ConnectDatabase;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HANG.VTT183524
 */
public class User {
    private String email;
    private String password;
    private String user_id;
    private String username;
    
    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
    
    public boolean checkInfor()
    {
        boolean check = false;
        
        ConnectDatabase.connect();
        check = ConnectDatabase.check("select * from User_Profile where email = '" + this.email + "' and password = '" + this.password + "' ;");
        ConnectDatabase.closeConnect();
        
        if (check == true) getInformFromDB();
        
        return check;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    private void getInformFromDB()
    {
        ConnectDatabase.connect();
        ResultSet rs = ConnectDatabase.executeSql("select * from User_Profile where email = '" + this.email + "' ;");
        
        try {
            while (rs.next())
            {
                this.user_id = rs.getString("user_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                
                this.username = firstname + " " + lastname;
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConnectDatabase.closeConnect();
    }
    
    public String getUsername()
    {
        return this.username;
    }
}

