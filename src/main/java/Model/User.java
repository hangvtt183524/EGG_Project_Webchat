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
// this class contain information of user
public class User {
    private String email;
    private String password;
    private String user_id;
    private String username;
    private String avatar;
    
    // construct object when sign-in by email and password were inputed from keybroad
    public User(String email, String password)
    {
        this.email = email;
        this.password = password;
    }
    
    // when people submit, check if email and password are correct or not
    public boolean checkInfor()
    {
        boolean check = false;
        
        ConnectDatabase connect = new ConnectDatabase();
        check = connect.check("select * from User_Profile where email = '" + this.email + "' and password = '" + this.password + "' ;");
        connect.closeConnect();
        
        // if information is true, get the rest of information from DB
        if (check == true) getInformFromDB();
        
        return check;
    }
    
    public String getEmail()
    {
        return this.email;
    }
    
    private void getInformFromDB()
    {
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select * from User_Profile where email = '" + this.email + "' ;");
        
        try {
            while (rs.next())
            {
                this.user_id = rs.getString("user_id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                
                this.username = firstname + " " + lastname;
                this.avatar = rs.getString("avatar");
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        connect.closeConnect();
    }
    
    public String getUsername()
    {
        return this.username;
    }
    public int getUserId()
    {
        return Integer.parseInt(this.user_id);
    }
    public String getAvatar()
    {
        return this.avatar;
    }
    public String getPassword()
    {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    
}

