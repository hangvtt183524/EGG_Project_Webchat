// list-room which this user connected
var listSocketConnected = new Map();

var room_id;
var address;
var websocket;
// text-input that user type message            
var messageText = document.getElementById("messageText");
var text;

// when user click on room-name in chat-list, websocket owning id which is passed via x-argument open
// first, room-name and room-img will match with room-name in chat-list 
// second, check in listSocketConnected if socket to this room exists or not. 
// If not, create a new WebSocket Object with this id


function startChat(x)
    {
        document.getElementById("insert-roomName").textContent = x.lastElementChild.lastElementChild.firstElementChild.firstElementChild.textContent;
        room_id = x.id;
        
        $('#div21').hide();
        //use ajax to send room-id to servlet, get information from DB, then match mutual
           var xhttp = new XMLHttpRequest() || ActiveXObject();
       //listen event status changing of request
       xhttp.onreadystatechange = function() 
        {
            //check if request successfully
                 if (this.readyState == 4 && this.status == 200) {
                            
        document.getElementsByClassName("rounded-circle my-avatar")[1].src = this.responseText;
        document.getElementsByClassName("rounded-circle my-avatar")[2].src = this.responseText;
        
               } 
        }
        //config request
             xhttp.open('GET', 'GetRoomAvatar?room_id=' + room_id, true);
     //send request
           xhttp.send();
    
        // get WebSocket Object mathc room_id if exists   
        websocket = listSocketConnected.get(room_id);
        // if not, create a new WebSocket Object
        if(websocket == undefined)  
            {
                address = "ws://localhost:8080/WebChatProject/chatroomEndpoint/" + room_id;
                websocket = new WebSocket(address);
                listSocketConnected.set(room_id, websocket);
            }
            // handle messages were received from server
        websocket.onmessage = function processMessage(message)
            {
                var jsonData = JSON.parse(message.data);
                if (jsonData.message !== null) 
                    {
                        // add message to chat-text that contain all message
                        document.getElementById("mess").innerHTML += jsonData.message;
                        
                    }
            };

           }
           
    // when user click on send-button, messages will be sended to server      
    function sendMessage()
    {
        text = document.getElementById("messageText").value;
        websocket.send(text);
        //add message to chat-text that contain all message
        document.getElementById("mess").innerHTML += "<div class=\"d-flex justify-content-end mb-4\">"
                                                    + "<div class=\"msg_cotainer_send\">"
                                                        + text 
                                                    + "</div>"
                                                + "</div>";
        // empty input-message
        document.getElementById("messageText").value = "";
    }


    // when people quit this room, Websocket will be closed
    function removeSocket()
    {
        websocket.close();
        websocket = null;
        // remove this WebSocket Object from list
        listSocketConnected.delete(room_id);
    }