var listSocketConnected = new Map();
var room_id;
var address;
var websocket;
            
var messageArea = document.getElementById("chat123");
var text;
            
function startChat(x)
    {
        // room_id = x.id;
            
        // websocket = listSocketConnected.get(room_id);
        // if(websocket == undefined)  
        //     {
        //         address = "ws://localhost:8080/WebChatProject/chatroomEndpoint/" + room_id;
        //         websocket = new WebSocket(address);
        //         listSocketConnected.set(room_id, websocket);
        //     }
            
        // websocket.onmessage = function processMessage(message)
        //     {
        //         var jsonData = JSON.parse(message.data);
        //         if (jsonData.message !== null) 
        //             {
        //                 document.getElementById("chat123").innerHTML += "<div class=\"d-flex justify-content-start mb-4\">"
        //                                             + "<div class=\"msg_cotainer\">"
        //                                                 + jsonData.message 
        //                                             + "</div>"
        //                                         + "</div>";
                        
        //             }
        //     };
            
        document.getElementById("insert-roomName").textContent = "asfgsdfgs";
        document.getElementById("test").src = "so7.png";
            
    //     var xhttp = new XMLHttpRequest() || ActiveXObject();
    //     //Bat su kien thay doi trang thai cuar request
    //     xhttp.onreadystatechange = function() 
    //     {
    //         //Kiem tra neu nhu da gui request thanh cong
    //              if (this.readyState == 4 && this.status == 200) {
    //                   document.getElementById("list-participants").innerHTML += this.responseText;
    //             } 
    //     }
    //     //cau hinh request
    //         xhttp.open('POST', 'GetUserFromDB', true);
    // //cau hinh header cho request
    //         xhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    // //gui request
    //         xhttp.send('room_id='+x.id);
           }
           
           
            function sendMessage()
            {
                websocket.send(messageText.value);
                text = messageText.value;
                document.getElementById("chat123").innerHTML += "<div class=\"d-flex justify-content-end mb-4\">"
                                                    + "<div class=\"msg_cotainer_send\">"
                                                        + text 
                                                    + "</div>"
                                                + "</div>";
                messageText.value = "";
                }