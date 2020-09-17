/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.room;

import com.webchatproject.connectdatabase.ConnectDatabase;

/**
 *
 * @author HANG.VTT183524
 */
// store message from user into table Messenger on DB
public class StoreMessage {
    public static void store(int user_id, int room_id, String message)
    {
        ConnectDatabase connect = new ConnectDatabase();
        connect.insertIntoDatabase("insert into Messenger values ("+ room_id + ", "+ user_id + ", '" + message + "', null);");
        
        connect.closeConnect();
    }
}
