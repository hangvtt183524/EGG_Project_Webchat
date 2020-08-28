/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.webchatproject.chat;

import Model.User;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * @author HANG.VTT183524
 */
public class ChatroomServerConfigurator extends ServerEndpointConfig.Configurator{
    public void modifyHandshake (ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response)
    {
       sec.getUserProperties().put("user", (User)((HttpSession) request.getHttpSession()).getAttribute("user"));
        
    }
}
