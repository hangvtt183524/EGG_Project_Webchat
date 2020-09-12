/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function addNewRoom() {
    var roomName = document.getElementById("addNewRoom-text").value;
    var xhttp = new XMLHttpRequest() || ActiveXObject();
    //Bat su kien thay doi trang thai cuar request
    xhttp.onreadystatechange = function () {
        //Kiem tra neu nhu da gui request thanh cong
        if (this.readyState == 4 && this.status == 200) {
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
                    
            $('#myModal').hide();
        }
    }
    //cau hinh request
    xhttp.open('GET','CreateNewRoom?roomName=' + roomName,true);
    //gui request
    xhttp.send();
}