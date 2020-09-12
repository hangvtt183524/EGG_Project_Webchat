/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

// handle when user make a new room
// use ajax to send room-name to servlet, receive room-id
function addNewRoom() {
    var roomName = document.getElementById("addNewRoom-text").value;
    var xhttp = new XMLHttpRequest() || ActiveXObject();
    
    xhttp.onreadystatechange = function () {
        
        if (this.readyState == 4 && this.status == 200) {
            // append new room to chat-list
            document.getElementsByClassName("chat-list")[0].innerHTML += 
                       "<li class=\"chat\">"
                            + "<a style=\"text-decoration: none; color: rgb(100, 100, 100)\" onclick=\"startChat(this)\" id=\"" + ("room-" + this.responseText) + "\">"
                            +     "<div style=\"display: flex;\">"
                            +         "<div style=\"height: 50px;\">"
                            +              "<img src=\"libs/image/default-avatar.png\" class=\"rounded-circle avatar\">"
                            +         "</div>"
                            +         "<div style=\"margin-left: 10px;\">"
                            +              "<div style=\"height: 30px;\">"
                            +                   "<span class=\"chat-name\">" + roomName + "</span>"
                            +              "</div>"
                            +              "<div style=\"display: flex;\">"
                            +                   "<div style=\"height: 20px;\">"
                            +                        "<span></span>"
                            +                   "</div>"
                            +                   "<div style=\"height: 20px;\">"
                            +                        "<span></span>"
                            +                   "</div>"
                            +              "</div>"
                            +         "</div>"
                            +     "</div>"
                            +  "</a>"
                            +"</li>";
                    
        }
    }
    
    xhttp.open('GET','CreateNewRoom?roomName=' + roomName,true);
    
    xhttp.send();
}