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
        document.getElementById("insert-roomName1").textContent = x.lastElementChild.lastElementChild.firstElementChild.firstElementChild.textContent;
        document.getElementById("insert-roomName2").innerHTML = "<b>" + x.lastElementChild.lastElementChild.firstElementChild.firstElementChild.textContent + "</b>";
        room_id = x.id;
        
        $('#div21').hide();
        //use ajax to send room-id to servlet, get information from DB, then match mutual
           var xhttp = new XMLHttpRequest() || ActiveXObject();
       //listen event status changing of request
       xhttp.onreadystatechange = function() 
        {
            //check if request successfully
                 if (this.readyState == 4 && this.status == 200) {
                            
        document.getElementById("img-room-avatar-1").src = this.responseText;
        document.getElementById("img-room-avatar-2").src = this.responseText;
        loadMess();
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
                    // handle when user receive a kick out command
                if (jsonData.kickout != null || jsonData.kickout != undefined)
                {
                  alert("You are disowned from " + x.lastElementChild.lastElementChild.firstElementChild.firstElementChild.textContent);  
                removeSocket();
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
  
  function loadMess()
{
    $(document).ready(function(){
            $.ajax({
                type: 'POST',
                data: {command: 'chat'},
                url: 'getInformFromDB',
                success: function(result){
                    $('#mess').html("");
                    $('#mess').append(result);
                }
            });
          });
}

  function kickout(x){
      
        $(document).ready(function(){
            //$('#div21').hide();
            websocket.send("kick_out_member-"+x);
            
        });
    }