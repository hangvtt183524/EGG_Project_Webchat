/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.chat;

import Model.User;
import com.webchatproject.connectdatabase.ConnectDatabase;
import com.webchatproject.room.StoreMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author HANG.VTT183524
 */

// Server handle when people chat
// 
@ServerEndpoint(value="/chatroomEndpoint/{chatroom}", configurator = ChatroomServerConfigurator.class)
public class ChatRoomEndpoint {
    //element in this map is (key = room_id; value = Set contain member's sessions of the room_id)
    static Map<String, Set<Session>> chatrooms = (Map<String, Set<Session>>)Collections.synchronizedMap(new HashMap<String, Set<Session>>());
    
    // get Set of Session that match with room_id passed by chatroomName argument
    public Set<Session> getChatroom (String chatroomName)
        {
            Set<Session> chatroom =  chatrooms.get(chatroomName);
            if (chatroom == null)
            {
                chatroom = Collections.synchronizedSet(new HashSet<Session>());
                chatrooms.put(chatroomName, chatroom);            
            }
            return chatroom;
        }
    // handle when websocket open
   @OnOpen
    public void handleOpen (EndpointConfig config, Session userSession, @PathParam("chatroom") String chatroom)
    {
        userSession.getUserProperties().put("user", config.getUserProperties().get("user"));
        
        
        ConnectDatabase connect = new ConnectDatabase();
        ResultSet rs = connect.executeSql("select member_id from Participant where room_id = " + Integer.parseInt(chatroom.substring(5)) + " ;");
        User user = (User) config.getUserProperties().get("user");
        
        try {
            while(rs.next())
            {
                if (Integer.parseInt(rs.getString("member_id")) == user.getUserId())
                {
                    userSession.getUserProperties().put("chatroom", chatroom);
                    Set<Session> chatroomUsers = getChatroom(chatroom);
                    chatroomUsers.add(userSession);
                    
                    break;
                }
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(ChatRoomEndpoint.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    // handle when people are chatting
    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException
    {
        User user = (User) userSession.getUserProperties().get("user");
        String chatroom = (String) userSession.getUserProperties().get("chatroom");
        
        String username = user.getUsername();
        String avatar = user.getAvatar();
        int user_id = user.getUserId();
        
        Set<Session> chatroomUsers = getChatroom(chatroom);
        
               // loop through all Session of member of this room
               Iterator<Session> irerator = chatroomUsers.iterator();
               Session current;

        if (message.substring(0, 16).equals("kick_out_member-"))
        {
        
        ConnectDatabase connect = new ConnectDatabase();
           ResultSet rs = connect.executeSql("select * from Chat_Room where room_id = " + Integer.parseInt(chatroom.substring(5)) + " ;");
           try 
           {
           if (rs.next() && Integer.parseInt(rs.getString("creator_id")) == user_id)
           {
               int kick_id = 0;
                   
               

               while (irerator.hasNext())
               {
                    current = irerator.next();
                    // send message to user if only they aren't message' sender
                    if (!current.equals(userSession))
                    {
                        kick_id = ((User) current.getUserProperties().get("user")).getUserId();
                        if (kick_id == Integer.parseInt(message.substring(16)))
                        {
                            connect.insertIntoDatabase("delete from Participant where room_id = " + Integer.parseInt(chatroom.substring(5)) + " and member_id = " + message.substring(16) + " ;");
                            chatroomUsers.remove(current);
                            current.getBasicRemote().sendText(buildJsonData_kickout("user-" + message.substring(16)));
                            break;
                        }
                    }
                }
                rs.close();
           }
            } 
            catch (SQLException ex) {
                Logger.getLogger(ChatRoomEndpoint.class.getName()).log(Level.SEVERE, null, ex);
            }
        connect.closeConnect();
        }
        else 
        {
             // form of message return to Client
        String messageReturn = "<div class=\"d-flex justify-content-start mb-4\">"
                             +     "<div class=\"img_cont_msg\">"
                             +         "<img src=\"" + avatar + "\" class=\"rounded-circle user_img_msg\">"
                             +     "</div>"
                             +     "<div class=\"msg_cotainer\">"
                             +         message
                             +     "</div>"
                             + "</div>";
        
        while (irerator.hasNext())
        {
            current = irerator.next();
            // send message to user if only they aren't message' sender
            if (!current.equals(userSession))
                 current.getBasicRemote().sendText(buildJsonData(messageReturn));
        }
        // store message into DB
        StoreMessage.store(user_id, Integer.parseInt(chatroom.substring(5)), message);
        }   
    }

    // handle when websocket close
    @OnClose
    public void handleClose(Session userSession)
    {
        String chatroom = (String) userSession.getUserProperties().get("chatroom");
        Set<Session> chatroomUsers = getChatroom(chatroom);
   
        chatroomUsers.remove(userSession);
        
    }
    
    @OnError
    public void handleError(Throwable t){
    
    }
    
    // format data in order to return to websocket
        private String buildJsonData(String messageReturn)
    {
        JsonObject jsonObject = Json.createObjectBuilder().add("message",messageReturn).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter))
        {
            jsonWriter.write(jsonObject);
        }
        return stringWriter.toString();
    }
        
        private String buildJsonData_kickout(String kickout_user)
    {
        JsonObject jsonObject = Json.createObjectBuilder().add("kickout",kickout_user).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter))
        {
            jsonWriter.write(jsonObject);
        }
        return stringWriter.toString();
    }
}
