/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function show() {
            var addNewRoom = document.getElementById("add-new-room");
            addNewRoom.style.visibility = "visible";
            
            var xhttp = new XMLHttpRequest() || ActiveXObject();
    //Bat su kien thay doi trang thai cuar request
    xhttp.onreadystatechange = function () {
        //Kiem tra neu nhu da gui request thanh cong
        if (this.readyState == 4 && this.status == 200) {
            //In ra data nhan duoc
            document.getElementById("myPopup").innerHTML += (this.responseText)
        }
    }
    //cau hinh request
    xhttp.open('GET','GetUserFromDB?data=true',true);
    //gui request
    xhttp.send();
        }

        function hide()
        {
            var addNewRoom = document.getElementById("add-new-room");
            addNewRoom.style.visibility = "hidden";
            var popup = document.getElementById("myPopup");
    popup.style.visibility = 'hidden';
        }
        function myFunction() {
  var popup = document.getElementById("myPopup");
  popup.style.visibility = 'visible';
}

function myFunction2()
{
    var popup = document.getElementById("myPopup");
    popup.style.visibility = 'hidden';
}
function myFunction1() {
    var input, filter, ul, li, a, i, txtValue;
    input = document.getElementById("myInput");
    filter = input.value.toUpperCase();
    ul = document.getElementById("myPopup");
    li = ul.getElementsByTagName("li");
    for (i = 0; i < li.length; i++) {
        a = li[i].getElementsByTagName("a")[0];
        txtValue = a.textContent || a.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            li[i].style.display = "";
        } else {
            li[i].style.display = "none";
        }
    }
}

function showResult(x)
{
    document.getElementById("result").innerHTML += "<span class=\"addFriend\">" + x.textContent + "</span>";
    myFunction2();
}