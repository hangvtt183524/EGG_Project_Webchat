/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.chat;

import Model.User;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
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
@ServerEndpoint(value="/chatroomEndpoint/{chatroom}", configurator = ChatroomServerConfigurator.class)
public class ChatRoomEndpoint {
    static Map<String, Set<Session>> chatrooms = (Map<String, Set<Session>>)Collections.synchronizedMap(new HashMap<String, Set<Session>>());
    
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
   @OnOpen
    public void handleOpen (EndpointConfig config, Session userSession, @PathParam("chatroom") String chatroom)
    {
        userSession.getUserProperties().put("user", config.getUserProperties().get("user"));
        userSession.getUserProperties().put("chatroom", chatroom);
        Set<Session> chatroomUsers = getChatroom(chatroom);
        chatroomUsers.add(userSession);
        
    }
    
    @OnMessage
    public void handleMessage(String message, Session userSession) throws IOException
    {
        User user = (User) userSession.getUserProperties().get("user");
        String chatroom = (String) userSession.getUserProperties().get("chatroom");
        
        String username = user.getUsername();
        
        Set<Session> chatroomUsers = getChatroom(chatroom);
        Iterator<Session> irerator = chatroomUsers.iterator();
        while (irerator.hasNext())
                irerator.next().getBasicRemote().sendText(buildJsonData(username, message, chatroom));
    }

    
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
    
        private String buildJsonData(String username, String message, String chatroom)
    {
        JsonObject jsonObject = Json.createObjectBuilder().add("message", username + " " + chatroom+": "+ message).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter))
        {
            jsonWriter.write(jsonObject);
        }
        return stringWriter.toString();
    }
}
