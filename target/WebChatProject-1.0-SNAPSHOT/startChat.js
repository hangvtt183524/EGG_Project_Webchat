var listSocketConnected = new Map();
var room_id;
var address;
var websocket;
            
var messageText = document.getElementById("messageText");
var text;
            
function startChat(x)
    {
        document.getElementById("insert-roomName").textContent = x.lastElementChild.lastElementChild.firstElementChild.firstElementChild.textContent;
        room_id = x.id;
        
        $('#div21').hide();
        
           var xhttp = new XMLHttpRequest() || ActiveXObject();
       //Bat su kien thay doi trang thai cuar request
       xhttp.onreadystatechange = function() 
        {
            //Kiem tra neu nhu da gui request thanh cong
                 if (this.readyState == 4 && this.status == 200) {
                            
        document.getElementsByClassName("rounded-circle my-avatar")[1].src = this.responseText;
        document.getElementsByClassName("rounded-circle my-avatar")[2].src = this.responseText;
        
               } 
        }
        //cau hinh request
             xhttp.open('GET', 'GetRoomAvatar?room_id=' + room_id, true);
     //gui request
           xhttp.send();
    
            
        websocket = listSocketConnected.get(room_id);
        if(websocket == undefined)  
            {
                address = "ws://localhost:8080/WebChatProject/chatroomEndpoint/" + room_id;
                websocket = new WebSocket(address);
                listSocketConnected.set(room_id, websocket);
            }
        websocket.onmessage = function processMessage(message)
            {
                var jsonData = JSON.parse(message.data);
                if (jsonData.message !== null) 
                    {
                        document.getElementById("mess").innerHTML += jsonData.message;
                        
                    }
            };

           }
           
           
    function sendMessage()
    {
        text = document.getElementById("messageText").value;
        websocket.send(text);
        
        document.getElementById("mess").innerHTML += "<div class=\"d-flex justify-content-end mb-4\">"
                                                    + "<div class=\"msg_cotainer_send\">"
                                                        + text 
                                                    + "</div>"
                                                + "</div>";
        document.getElementById("messageText").value = "";
    }
