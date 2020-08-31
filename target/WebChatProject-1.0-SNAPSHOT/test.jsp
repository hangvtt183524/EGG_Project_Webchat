<%-- 
    Document   : test
    Created on : Aug 28, 2020, 8:43:05 PM
    Author     : HANG.VTT183524
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="Model.User"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            var listSocketConnected = new Map();
            var roomName;
            var address;
            var websocket;
            function startChat(name)
            {
            roomName = name;
            
            websocket = listSocketConnected.get(roomName);
            
            if(websocket == undefined)  
            {
                address = "ws://localhost:8080/WebChatProject/chatroomEndpoint/" + roomName;
                websocket = new WebSocket(address);
                listSocketConnected.set(roomName, websocket);
            }
            
            websocket.onmessage = function processMessage(message)
            {
                var jsonData = JSON.parse(message.data);
                if (jsonData.message !== null) 
                    messagesTextArea.value += jsonData.message + "\n";
            };
        }
            function sendMessage()
            {
                websocket.send(messageText.value);
                messageText.value = "";
                  
            }
        </script>
    </head>
    <body>
        <h1>Hello World!</h1>
        <div id="list-room">
            <button name="1" onclick="startChat(this.name)" id="1">Room 1</button>
            <button name="2" onclick="startChat(this.name)" id="2">Room 2</button>
            <button name="3" onclick="startChat(this.name)" id="3">Room 3</button>
        </div>

        <br>
        <textarea id="messagesTextArea" readonly="readonly" rows="10" cols="50"></textarea>
        <input type="text" id="messageText" size="50">
        <input type="button" value="Send" onclick="sendMessage();">
        <br>
        <div id="check"></div>
        <a href="login?action=Logout">Logout</a>
    </body>
</html>